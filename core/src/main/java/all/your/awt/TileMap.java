package all.your.awt;

import all.your.util.Preconditions;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.LinkedHashMap;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TileMap {

    private final LinkedHashMap<String, MapLayer> layers = new LinkedHashMap<>();
    private final Rectangle bounds;

    public TileMap(Dimension size) {
        Preconditions.checkArgument(size.width > 0 && size.height > 0, "size; %s", size);
        this.bounds = new Rectangle(size);
    }

    public MapLayer getLayer(String id) {
        return layers.get(id);
    }

    public TileMap addLayer(String id, MapLayer layer) {
        layers.put(id, layer);
        return this;
    }

    public MapLayer newLayer(String id) {
        Preconditions.checkState(!layers.containsKey(id), "id '" + id + "' already in use");
        MapLayer layer = new MapLayer(bounds.getSize());
        layers.put(id, layer);
        return layer;
    }

    public MapLayer removeLayer(String id) {
        return layers.remove(id);
    }

    public void paint(Graphics2D g, Rectangle viewport, Rectangle2D mapRegion) {
        // in case the map region is outside of the map, there is no work to do
        if (!mapRegion.intersects(bounds)) {
            return;
        }

        // because the map layers paint from origin, we prepare a local graphics object that clips to the given viewport
        g = (Graphics2D)g.create(viewport.x, viewport.y, viewport.width, viewport.height);

        // restrict the size of the requested map region to the number of pixels in the viewport. this ensures that
        // neither tile width nor height will ever be less than 1
        mapRegion = new Rectangle2D.Double(mapRegion.getX(), mapRegion.getY(),
                                           Math.min(mapRegion.getWidth(), viewport.width),
                                           Math.min(mapRegion.getHeight(), viewport.height));

        // divide the available number of viewport pixels over the requested map region to find the number of pixels to
        // use for each tile. due to the above restriction, neither dimension will ever be less than 1
        Dimension tileSize = new Dimension((int)(viewport.width / mapRegion.getWidth()),
                                           (int)(viewport.height / mapRegion.getHeight()));

        // to avoid conditions in the inner paint loop, we cap the requested map region to hold only valid indexes.
        Rectangle2D validRegion = bounds.createIntersection(mapRegion);

        // because the valid region might differ from the requested region, we must set up a translation on the local
        // graphics object so that each layer can still paint from origin
        g.translate((int)((validRegion.getX() - mapRegion.getX()) * tileSize.width),
                    (int)((validRegion.getY() - mapRegion.getY()) * tileSize.height));

        // because we only wish to paint full tiles, we round the region location down to include any fractional tile
        // that we might start off in
        Rectangle paintRegion = new Rectangle((int)validRegion.getX(), (int)validRegion.getY(), 0, 0);

        // determine where to start painting the tiles so that whatever fraction was requested acually ends up being
        // rendered appropriately at origin. this is simply a translation from the requested to painted region
        Point viewportPos = new Point((int)((paintRegion.x - validRegion.getX()) * tileSize.width),
                                      (int)((paintRegion.y - validRegion.getY()) * tileSize.height));

        paintRegion.width = Math.min((int)Math.ceil((viewport.width - viewportPos.x) / (double)tileSize.width),
                                     bounds.width - paintRegion.x);
        paintRegion.height = Math.min((int)Math.ceil((viewport.height - viewportPos.y) / (double)tileSize.height),
                                     bounds.height - paintRegion.y);

        // invoke paint() on all layers in order with the pre-calculated viewport and map region
        for (MapLayer layer : layers.values()) {
            layer.paint(g, viewportPos, paintRegion, tileSize);
        }

        // all done, discard of the temporary graphics object
        g.dispose();
    }
}
