package all.your.base.tiled;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Collections;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class TileSets {

    public static TileSet newTileSet(int cols, int tileWidth, int tileHeight, Color... tiles) {
        int rows = (tiles.length + (cols - 1)) / cols;
        BufferedImage img = new BufferedImage(cols * tileWidth, rows * tileHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D)img.getGraphics();
        for (int i = 0; i < tiles.length; ++i) {
            g.setColor(tiles[i]);
            g.fillRect((i % cols) * tileWidth, (i / cols) * tileHeight, tileWidth, tileHeight);
        }
        g.dispose();
        return new TileSet(Collections.<Tile>emptyList(), img, tileWidth, tileHeight);
    }
}
