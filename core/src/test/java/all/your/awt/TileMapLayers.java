package all.your.awt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TileMapLayers {

    public static MapLayer newInstance(Tile[][] tiles) {
        MapLayer map = new MapLayer(new Dimension(tiles[0].length, tiles.length));
        for (Point p = new Point(0, 0); p.y < tiles.length; ++p.y) {
            for (p.x = 0; p.x < tiles[p.y].length; ++p.x) {
                map.putTile(p, tiles[p.y][p.x]);
            }
        }
        return map;
    }

    public static MapLayer newColorGrid(Color[][] tiles) {
        MapLayer map = new MapLayer(new Dimension(tiles[0].length, tiles.length));
        fillColorGrid(map, tiles);
        return map;
    }

    public static void fillColorGrid(MapLayer out, Color[][] tiles) {
        for (Point p = new Point(0, 0); p.y < tiles.length; ++p.y) {
            for (p.x = 0; p.x < tiles[p.y].length; ++p.x) {
                Color col = tiles[p.y][p.x];
                if (col == null) {
                    continue;
                }
                BufferedImage image = BufferedImages.newSquareGrid(new Dimension(1, 1), new Color[][] { { col } });
                out.putTile(p, new SimpleTile(new Texture(image)));
            }
        }
    }
}