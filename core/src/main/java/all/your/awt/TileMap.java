package all.your.awt;

import all.your.util.Preconditions;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.LinkedHashMap;

import static java.lang.Math.ceil;
import static java.lang.Math.max;
import static java.lang.Math.min;

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
        if (!mapRegion.intersects(bounds)) {
            return;
        }
        Dimension tile = new Dimension((int)max(1, viewport.width / mapRegion.getWidth()),
                                       (int)max(1, viewport.height / mapRegion.getHeight()));
        g = (Graphics2D)g.create(viewport.x, viewport.y, viewport.width, viewport.height);
        double mapX = mapRegion.getX();
        if (mapX < 0) {
            g.translate(-mapX * tile.width, 0);
            mapX = 0;
        }
        double mapY = mapRegion.getY();
        if (mapY < 0) {
            g.translate(0, -mapY * tile.height);
            mapY = 0;
        }
        Point cursor = new Point((int)(((int)mapX - mapX) * tile.width),
                                 (int)(((int)mapY - mapY) * tile.height));
        Rectangle region = new Rectangle(
                (int)mapX, (int)mapY,
                min((int)ceil((viewport.width - cursor.x) / (double)tile.width), bounds.width - (int)mapX),
                min((int)ceil((viewport.height - cursor.y) / (double)tile.height), bounds.height - (int)mapY));
        for (MapLayer layer : layers.values()) {
            layer.paint(g, cursor, region, tile);
        }
        g.dispose();
    }
}
