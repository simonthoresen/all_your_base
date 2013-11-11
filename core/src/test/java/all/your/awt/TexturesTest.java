package all.your.awt;

import org.junit.Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import static all.your.awt.AssertImage.assertPixels;
import static all.your.awt.MoreColors.C0;
import static all.your.awt.MoreColors.C1;
import static all.your.awt.MoreColors.C2;
import static all.your.awt.MoreColors.C3;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TexturesTest {

    @Test
    public void requireThatFilledTexturesCanBeCreated() {
        assertPaint(
                Textures.newFilled(new Dimension(2, 5), C1),
                C0,
                new Rectangle(0, 0, 2, 5),
                new Color[][] {
                        { C1, C1, C0 },
                        { C1, C1, C0 },
                        { C1, C1, C0 },
                        { C1, C1, C0 },
                        { C1, C1, C0 },
                        { C0, C0, C0 },
                });
    }

    @Test
    public void requireThatSquareGridsCanBeCreated() {
        assertPaint(
                Textures.newSquareGrid(new Color[][] {
                        { C1, C2, C1, C2 },
                        { C2, C3, C2, C3 },
                        { C1, C2, C1, C2 },
                        { C2, C3, C2, C3 },
                }),
                C0,
                new Rectangle(0, 0, 8, 20),
                new Color[][] {
                        { C1, C1, C2, C2, C1, C1, C2, C2 },
                        { C1, C1, C2, C2, C1, C1, C2, C2 },
                        { C1, C1, C2, C2, C1, C1, C2, C2 },
                        { C1, C1, C2, C2, C1, C1, C2, C2 },
                        { C1, C1, C2, C2, C1, C1, C2, C2 },
                        { C2, C2, C3, C3, C2, C2, C3, C3 },
                        { C2, C2, C3, C3, C2, C2, C3, C3 },
                        { C2, C2, C3, C3, C2, C2, C3, C3 },
                        { C2, C2, C3, C3, C2, C2, C3, C3 },
                        { C2, C2, C3, C3, C2, C2, C3, C3 },
                        { C1, C1, C2, C2, C1, C1, C2, C2 },
                        { C1, C1, C2, C2, C1, C1, C2, C2 },
                        { C1, C1, C2, C2, C1, C1, C2, C2 },
                        { C1, C1, C2, C2, C1, C1, C2, C2 },
                        { C1, C1, C2, C2, C1, C1, C2, C2 },
                        { C2, C2, C3, C3, C2, C2, C3, C3 },
                        { C2, C2, C3, C3, C2, C2, C3, C3 },
                        { C2, C2, C3, C3, C2, C2, C3, C3 },
                        { C2, C2, C3, C3, C2, C2, C3, C3 },
                        { C2, C2, C3, C3, C2, C2, C3, C3 },
                });
    }

    @Test
    public void requireThatTextureCanBeLoadedFromFile() throws IOException {
        Texture texture = Textures.fromFile("/8x8.png");
        assertNotNull(texture);
    }

    @Test
    public void requireImageCanNotLoadedFromFileNotFound() throws IOException {
        try {
            Textures.fromFile("/fileNotFound.png");
            fail();
        } catch (FileNotFoundException e) {

        }
    }

    private static void assertPaint(Texture texture, Color background, Rectangle viewport, Color[][] expectedPixels) {
        BufferedImage image = Images.newInstance(new Dimension(expectedPixels[0].length, expectedPixels.length),
                                                 background);
        Graphics2D g = image.createGraphics();
        texture.paint(g, viewport);
        g.dispose();
        assertPixels(image, expectedPixels);
    }
}
