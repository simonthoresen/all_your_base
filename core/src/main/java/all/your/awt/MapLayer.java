package all.your.awt;

import java.awt.Graphics2D;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class MapLayer {

    private final Tile[][] tiles;

    public MapLayer(int width, int height) {
        tiles = new Tile[height][width];
    }

    public Tile getTile(int x, int y) {
        return tiles[y][x];
    }

    public void setTile(int x, int y, Tile tile) {
        tiles[y][x] = tile;
    }

    public void paint(Graphics2D viewport, int viewportX, int viewportY, int viewportWidth, int viewportHeight,
                      int mapRegionX, int mapRegionY, int mapRegionWidth, int mapRegionHeight) {
        int tileWidth = viewportWidth / mapRegionWidth;
        int tileHeight = viewportHeight / mapRegionHeight;
        double tileFractionX = (double)(viewportWidth - (tileWidth * mapRegionWidth)) / mapRegionWidth;
        double tileFractionY = (double)(viewportHeight - (tileHeight * mapRegionHeight)) / mapRegionHeight;

        // TODO: dont do this stretch, add a clipped row or column instead

        int renderY = viewportY;
        double renderFractionY = 0;
        for (int tileY = 0; tileY < mapRegionHeight; ++tileY) {
            int fullTileHeight = tileHeight;
            renderFractionY += tileFractionY;
            if (renderFractionY > 0.5) {
                ++fullTileHeight;
                renderFractionY -= 1;
            }
            int renderX = viewportX;
            double renderFractionX = 0;
            for (int tileX = 0; tileX < mapRegionWidth; ++tileX) {
                int fullTileWidth = tileWidth;
                renderFractionX += tileFractionX;
                if (renderFractionX > 0.5) {
                    ++fullTileWidth;
                    renderFractionX -= 1;
                }
                Tile tile = getTile(mapRegionX + tileX, mapRegionY + tileY);
                if (tile != null) {
                    tile.getTexture().paint(viewport, renderX, renderY, renderX + fullTileWidth, renderY + fullTileHeight);
                }
                renderX += fullTileWidth;
            }
            renderY += fullTileHeight;
        }
    }
}