package all.your.awt;

import all.your.swing.AbstractApplicationState;
import all.your.swing.ApplicationBuilder;
import all.your.swing.Surface;
import org.junit.Test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class BufferedImagesTest {

    @Test
    public void requireThatSquareGridsCanBeCreated() {
        int squareWidth = 2;
        int squareHeight = 5;
        Color[][] squares = new Color[][] {
                { Color.RED, Color.YELLOW, Color.RED, Color.YELLOW },
                { Color.YELLOW, Color.BLUE, Color.YELLOW, Color.BLUE },
                { Color.RED, Color.YELLOW, Color.RED, Color.YELLOW },
                { Color.YELLOW, Color.BLUE, Color.YELLOW, Color.BLUE },
        };
        BufferedImage image = BufferedImages.newSquareGrid(squareWidth, squareHeight, squares);
        assertNotNull(image);
        for (int x = 0; x < squares[0].length * squareWidth; ++x) {
            for (int y = 0; y < squares.length * squareHeight; ++y) {
                assertEquals(squares[y / squareHeight][x / squareWidth], new Color(image.getRGB(x, y)));
            }
        }
    }

    @Test
    public void requireThatImageCanBeLoadedFromFile() throws IOException {
        BufferedImage image = BufferedImages.fromFile("/8x8.png");
        assertNotNull(image);
    }

    @Test
    public void requireImageCanNotLoadedFromFileNotFound() throws IOException {
        try {
            BufferedImages.fromFile("/fileNotFound.png");
            fail();
        } catch (FileNotFoundException e) {

        }
    }

    public static void displayImage(final BufferedImage image, final int scale) {
        try {
            new ApplicationBuilder()
                    .setWindowWidth(image.getWidth() * scale)
                    .setWindowHeight(image.getHeight() * scale)
                    .setInitialState(new AbstractApplicationState() {

                        @Override
                        public void render(Surface surface) {
                            Graphics2D g = surface.getGraphics();
                            g.drawImage(image, 0, 0, image.getWidth() * scale, image.getHeight() * scale, null);
                        }
                    }).build().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
