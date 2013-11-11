package all.your.awt;

import all.your.util.Preconditions;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class MapLayer {

    private final List<Tile> tiles;
    private final Rectangle bounds;

    public MapLayer(Dimension size) {
        Preconditions.checkArgument(size.width > 0 && size.height > 0, "size; %s", size);
        this.bounds = new Rectangle(size);
        this.tiles = Arrays.asList(new Tile[size.height * size.width]);
        Collections.fill(this.tiles, NullTile.INSTANCE);
    }

    public Tile getTile(Point p) {
        if (!bounds.contains(p)) {
            return null;
        }
        Tile tile = tiles.get(p.y * bounds.width + p.x);
        if (tile == NullTile.INSTANCE) {
            tile = null;
        }
        return tile;
    }

    public Tile putTile(Point p, Tile tile) {
        Preconditions.checkIndex(p.x, bounds.width);
        Preconditions.checkIndex(p.y, bounds.height);
        if (tile == null) {
            tile = NullTile.INSTANCE;
        }
        tile = tiles.set(p.y * bounds.width + p.x, tile);
        if (tile == NullTile.INSTANCE) {
            tile = null;
        }
        return tile;
    }

    public void paint(Graphics2D g, Rectangle viewport, Rectangle2D mapRegion) {
        // if the requested map region does not intersect with the bounds of this map layer, then nothing will be
        // painted. return immediately
        if (!mapRegion.intersects(bounds)) {
            return;
        }

        // calculate size of each tile so that the request region covers the viewport
        int tileWidth = Math.max(1, (int)(viewport.width / mapRegion.getWidth()));
        int tileHeight = Math.max(1, (int)(viewport.height / mapRegion.getHeight()));

        // clip the requested map region to the bounds of this map layer, and translate the viewport accordingly
        Rectangle2D intersection = mapRegion.createIntersection(bounds);
        viewport = new Rectangle(viewport.x + (int)((intersection.getX() - mapRegion.getX()) * tileWidth),
                                 viewport.y + (int)((intersection.getY() - mapRegion.getY()) * tileHeight),
                                 viewport.width + (int)((intersection.getWidth() - mapRegion.getWidth()) * tileWidth),
                                 viewport.height + (int)((intersection.getHeight() - mapRegion.getHeight()) * tileHeight));
        mapRegion = intersection;

        // create a local graphics object that translates to and clips to the given viewport
        g = (Graphics2D)g.create(viewport.x, viewport.y, viewport.width, viewport.height);


        int xMin = (int)Math.floor(mapRegion.getX()); // 0.5 -> 0
        int yMin = (int)Math.floor(mapRegion.getY());
        int xMax = (int)Math.ceil(mapRegion.getX() + mapRegion.getWidth()); // 0.5 + 2 -> 3
        int yMax = (int)Math.ceil(mapRegion.getY() + mapRegion.getHeight());

        Rectangle viewportRegion = new Rectangle(tileWidth, tileHeight);
        viewportRegion.y = (int)((yMin - mapRegion.getY()) * tileHeight);
        for (int y = yMin; y < yMax; ++y) {
            viewportRegion.x = (int)((xMin - mapRegion.getX()) * tileWidth);
            for (int x = xMin; x < xMax; ++x) {
                tiles.get(y * bounds.width + x).getTexture().paint(g, viewportRegion);
                viewportRegion.x += tileWidth;
            }
            viewportRegion.y += tileHeight;
        }
        g.dispose();
   }
}