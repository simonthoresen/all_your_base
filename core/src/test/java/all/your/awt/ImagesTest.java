package all.your.awt;

import org.junit.Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import static all.your.awt.AssertImage.assertPixels;
import static all.your.awt.MoreColors.C1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class ImagesTest {

    @Test
    public void requireThatSizeIsMandatory() {
        try {
            Images.newInstance(null);
            fail();
        } catch (NullPointerException e) {
            assertEquals("size", e.getMessage());
        }
        try {
            Images.newInstance(null, C1);
            fail();
        } catch (NullPointerException e) {
            assertEquals("size", e.getMessage());
        }
    }

    @Test
    public void requireThatNullColorThrows() {
        try {
            Images.newInstance(new Dimension(1, 1), null);
            fail();
        } catch (NullPointerException e) {
            assertEquals("fill", e.getMessage());
        }
    }

    @Test
    public void requireThatImageCanBeCreated() {
        BufferedImage image = Images.newInstance(new Dimension(1, 2));
        assertNotNull(image);
        assertEquals(1, image.getWidth());
        assertEquals(2, image.getHeight());
    }

    @Test
    public void requireThatImageCanBeFilled() {
        assertPixels(Images.newInstance(new Dimension(6, 9), C1), new Color[][] {
                { C1, C1, C1, C1, C1, C1 },
                { C1, C1, C1, C1, C1, C1 },
                { C1, C1, C1, C1, C1, C1 },
                { C1, C1, C1, C1, C1, C1 },
                { C1, C1, C1, C1, C1, C1 },
                { C1, C1, C1, C1, C1, C1 },
                { C1, C1, C1, C1, C1, C1 },
                { C1, C1, C1, C1, C1, C1 },
                { C1, C1, C1, C1, C1, C1 },
        });
    }
}
