package all.your.base.tiled;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class BufferedImagesTest {

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
