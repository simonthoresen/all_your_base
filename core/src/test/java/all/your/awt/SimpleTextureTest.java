package all.your.awt;

import org.junit.Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static all.your.awt.AssertImage.assertPixels;
import static all.your.awt.MoreColors.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class SimpleTextureTest {

    @Test
    public void requireThatNullImageThrows() {
        try {
            new SimpleTexture(null);
            fail();
        } catch (NullPointerException e) {
            assertEquals("image", e.getMessage());
        }
    }

    @Test
    public void requireThatTextureIsDrawnToGivenRectangle() {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(C0);
        g.fillRect(0, 0, 100, 100);
        new SimpleTexture(BufferedImages.newSquareGrid(new Dimension(2, 2), new Color[][] {
                { C1, C2 },
                { C3, C4 }
        })).paint(g, new Rectangle(25, 25, 50, 50));
        g.dispose();

        Color[][] expected = new Color[100][100];
        for (int y = 0; y < 100; ++y) {
            for (int x = 0; x < 100; ++x) {
                expected[y][x] = C0;
                if (y >= 25 && y < 50) {
                    if (x >= 25 && x < 50) {
                        expected[y][x] = C1;
                    } else if (x >= 50 && x < 75) {
                        expected[y][x] = C2;
                    }
                } else if (y >= 50 && y < 75) {
                    if (x >= 25 && x < 50) {
                        expected[y][x] = C3;
                    } else if (x >= 50 && x < 75) {
                        expected[y][x] = C4;
                    }
                }
            }
        }
        assertPixels(image, expected);
    }
}
