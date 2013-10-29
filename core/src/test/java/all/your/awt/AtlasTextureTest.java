package all.your.awt;

import org.junit.Test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class AtlasTextureTest {

    @Test
    public void requireThatOnlySpecifiedRegionOfAtlasIsPainted() {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 100, 100);
        new AtlasTexture(BufferedImages.newSquareGrid(2, 2, new Color[][] {
                { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN },
                { Color.ORANGE, Color.YELLOW, Color.GREEN, Color.RED },
                { Color.YELLOW, Color.GREEN, Color.RED, Color.ORANGE },
                { Color.GREEN, Color.RED, Color.ORANGE, Color.YELLOW }
        }), 2, 2, 4, 4).paint(g, 25, 25, 50, 50);
        g.dispose();

        for (int y = 0; y < 100; ++y) {
            for (int x = 0; x < 100; ++x) {
                Color actual = new Color(image.getRGB(x, y));
                Color expected = Color.BLACK;
                if (y >= 25 && y < 50) {
                    if (x >= 25 && x < 50) {
                        expected = Color.YELLOW;
                    } else if (x >= 50 && x < 75) {
                        expected = Color.GREEN;
                    }
                } else if (y >= 50 && y < 75) {
                    if (x >= 25 && x < 50) {
                        expected = Color.GREEN;
                    } else if (x >= 50 && x < 75) {
                        expected = Color.RED;
                    }
                }
                assertEquals("(" + x + "," + y + ")", expected, actual);
            }
        }
    }
}
