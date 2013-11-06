package all.your.awt;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TileMapLayers {

    public static TileMapLayer newColorGrid(Color[][] tiles) {
        TileMapLayer map = new TileMapLayer(tiles[0].length, tiles.length);
        fillColorGrid(map, tiles);
        return map;
    }

    public static void fillColorGrid(TileMapLayer out, Color[][] tiles) {
        for (int y = 0; y < tiles.length; ++y) {
            for (int x = 0; x < tiles[y].length; ++x) {
                Color col = tiles[y][x];
                if (col == null) {
                    continue;
                }
                BufferedImage image = BufferedImages.newSquareGrid(1, 1, new Color[][] { { col } });
                out.putTile(x, y, new SimpleTile(new SimpleTexture(image)));
            }
        }
    }
}