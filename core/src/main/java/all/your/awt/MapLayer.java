package all.your.awt;

import all.your.util.Preconditions;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class MapLayer {

    private final Tile[][] tiles;

    public MapLayer(int width, int height) {
        Preconditions.checkArgument(width > 0, "width");
        Preconditions.checkArgument(height > 0, "height");
        tiles = new Tile[height][width];
    }

    public Tile getTile(int x, int y) {
        if (y < 0 || y >= tiles.length ||
            x < 0 || x >= tiles[y].length) {
            return null;
        }
        return tiles[y][x];
    }

    public Tile putTile(int x, int y, Tile tile) {
        Preconditions.checkIndex(y, tiles.length);
        Preconditions.checkIndex(x, tiles[y].length);
        Tile oldTile = tiles[y][x];
        tiles[y][x] = tile;
        return oldTile;
    }

    public void paint(Graphics2D g, Rectangle viewport, Rectangle mapRegion) {
        int tileWidth = viewport.width / mapRegion.width;
        int tileHeight = viewport.height / mapRegion.height;

        int maxTileX = mapRegion.x + viewport.width / tileWidth + (viewport.width % tileWidth != 0 ? 1 : 0);
        int maxTileY = mapRegion.y + viewport.height / tileHeight + (viewport.height % tileHeight != 0 ? 1 : 0);

        int viewportY = viewport.y;
        for (int tileY = mapRegion.y; tileY < maxTileY; ++tileY) {
            int viewportX = viewport.x;
            for (int tileX = mapRegion.x; tileX < maxTileX; ++tileX) {
                Tile tile = getTile(tileX, tileY);
                if (tile != null) {
                    tile.getTexture().paint(g, viewportX, viewportY, tileWidth, tileHeight);
                }
                viewportX += tileWidth;
            }
            viewportY += tileHeight;
        }
   }
}