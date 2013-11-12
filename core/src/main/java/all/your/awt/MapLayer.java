package all.your.awt;

import all.your.util.Preconditions;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
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

    public void paint(Graphics2D g, Point viewportPos, Rectangle mapRegion, Dimension tileSize) {
        Rectangle viewportRegion = new Rectangle(tileSize);
        viewportRegion.y = viewportPos.y;
        for (int y = 0; y < mapRegion.height; ++y) {
            viewportRegion.x = viewportPos.x;
            for (int x = 0; x < mapRegion.width; ++x) {
                tiles.get((mapRegion.y + y) * bounds.width + (mapRegion.x + x)).getTexture().paint(g, viewportRegion);
                viewportRegion.x += tileSize.getWidth();
            }
            viewportRegion.y += tileSize.getHeight();
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }
}