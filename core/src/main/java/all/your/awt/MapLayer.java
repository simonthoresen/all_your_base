package all.your.awt;

import all.your.util.Preconditions;

import java.awt.Graphics2D;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class MapLayer {

    private final Tile[][] tiles;

    public MapLayer(int width, int height) {
        Preconditions.checkArgument(width > 0, "width");
        Preconditions.checkArgument(height > 0, "height");
        tiles = new Tile[width][height];
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= tiles.length ||
            y < 0 || y >= tiles[0].length) {
            return null;
        }
        return tiles[x][y];
    }

    public Tile putTile(int x, int y, Tile tile) {
        Preconditions.checkIndex(x, tiles.length);
        Preconditions.checkIndex(y, tiles[0].length);
        Tile oldTile = tiles[x][y];
        tiles[x][y] = tile;
        return oldTile;
    }

    public void paint(Graphics2D viewport, int viewportX, int viewportY, int viewportWidth, int viewportHeight,
                      int mapRegionX, int mapRegionY, int mapRegionWidth, int mapRegionHeight) {
        int tileWidth = viewportWidth / mapRegionWidth;
        int tileHeight = viewportHeight / mapRegionHeight;

        int maxTileX = mapRegionX + viewportWidth / tileWidth + (viewportWidth % tileWidth != 0 ? 1 : 0);
        int maxTileY = mapRegionY + viewportHeight / tileHeight + (viewportHeight % tileHeight != 0 ? 1 : 0);
        for (int tileX = mapRegionX; tileX < maxTileX; ++tileX) {
            for (int tileY = mapRegionY; tileY < maxTileY; ++tileY) {
                Tile tile = getTile(tileX, tileY);
                if (tile != null) {
                    tile.getTexture().paint(viewport, viewportX, viewportY, tileWidth, tileHeight);
                }
            }
        }
   }
}