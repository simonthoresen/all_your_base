package all.your.awt;

import org.junit.Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import static all.your.awt.AssertImage.assertPixels;
import static all.your.awt.Palette.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class BufferedImagesTest {

    @Test
    public void requireThatFilledImagesCanBeCreated() {
        BufferedImage image = BufferedImages.newFilled(new Dimension(2, 5), C0);
        assertNotNull(image);
        assertPixels(image, new Color[][] {
                { C0, C0 },
                { C0, C0 },
                { C0, C0 },
                { C0, C0 },
                { C0, C0 },
        });
    }

    @Test
    public void requireThatSquareGridsCanBeCreated() {
        Color[][] squares = new Color[][] {
                { C1, C2, C1, C2 },
                { C2, C3, C2, C3 },
                { C1, C2, C1, C2 },
                { C2, C3, C2, C3 },
        };
        BufferedImage image = BufferedImages.newSquareGrid(new Dimension(2, 5), squares);
        assertNotNull(image);
        assertPixels(image, new Color[][] {
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
