package all.your.awt;

import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import static all.your.awt.AssertImage.assertPixels;
import static java.awt.Color.BLUE;
import static java.awt.Color.RED;
import static java.awt.Color.YELLOW;
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
                { RED, YELLOW, RED, YELLOW },
                { YELLOW, BLUE, YELLOW, BLUE },
                { RED, YELLOW, RED, YELLOW },
                { YELLOW, BLUE, YELLOW, BLUE },
        };
        BufferedImage image = BufferedImages.newSquareGrid(squareWidth, squareHeight, squares);
        assertNotNull(image);
        assertPixels(image, new Color[][] {
                { RED, RED, YELLOW, YELLOW, RED, RED, YELLOW, YELLOW },
                { RED, RED, YELLOW, YELLOW, RED, RED, YELLOW, YELLOW },
                { RED, RED, YELLOW, YELLOW, RED, RED, YELLOW, YELLOW },
                { RED, RED, YELLOW, YELLOW, RED, RED, YELLOW, YELLOW },
                { RED, RED, YELLOW, YELLOW, RED, RED, YELLOW, YELLOW },
                { YELLOW, YELLOW, BLUE, BLUE, YELLOW, YELLOW, BLUE, BLUE },
                { YELLOW, YELLOW, BLUE, BLUE, YELLOW, YELLOW, BLUE, BLUE },
                { YELLOW, YELLOW, BLUE, BLUE, YELLOW, YELLOW, BLUE, BLUE },
                { YELLOW, YELLOW, BLUE, BLUE, YELLOW, YELLOW, BLUE, BLUE },
                { YELLOW, YELLOW, BLUE, BLUE, YELLOW, YELLOW, BLUE, BLUE },
                { RED, RED, YELLOW, YELLOW, RED, RED, YELLOW, YELLOW },
                { RED, RED, YELLOW, YELLOW, RED, RED, YELLOW, YELLOW },
                { RED, RED, YELLOW, YELLOW, RED, RED, YELLOW, YELLOW },
                { RED, RED, YELLOW, YELLOW, RED, RED, YELLOW, YELLOW },
                { RED, RED, YELLOW, YELLOW, RED, RED, YELLOW, YELLOW },
                { YELLOW, YELLOW, BLUE, BLUE, YELLOW, YELLOW, BLUE, BLUE },
                { YELLOW, YELLOW, BLUE, BLUE, YELLOW, YELLOW, BLUE, BLUE },
                { YELLOW, YELLOW, BLUE, BLUE, YELLOW, YELLOW, BLUE, BLUE },
                { YELLOW, YELLOW, BLUE, BLUE, YELLOW, YELLOW, BLUE, BLUE },
                { YELLOW, YELLOW, BLUE, BLUE, YELLOW, YELLOW, BLUE, BLUE },
        });
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
}
