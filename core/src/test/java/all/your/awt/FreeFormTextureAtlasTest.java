package all.your.awt;

import org.junit.Test;

import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class FreeFormTextureAtlasTest {

    @Test
    public void requireThatNullImageThrows() {
        try {
            new FreeFormTextureAtlas.Builder(null);
            fail();
        } catch (NullPointerException e) {
            assertEquals("image", e.getMessage());
        }
    }

    @Test
    public void requireThatTextureIdMustBeUnique() {
        BufferedImage image = new BufferedImage(320, 240, BufferedImage.TYPE_INT_ARGB);
        FreeFormTextureAtlas.Builder builder = new FreeFormTextureAtlas.Builder(image);
        builder.addTexture(6, 0, 0, 32, 24);
        builder.addTexture(9, 0, 0, 32, 24);
        try {
            builder.addTexture(6, 0, 0, 32, 24);
            fail();
        } catch (IllegalStateException e) {
            assertEquals("", e.getMessage());
        }
    }

    @Test
    public void requireThatTextureRegionMustBeInImage() {
        BufferedImage image = new BufferedImage(320, 240, BufferedImage.TYPE_INT_ARGB);
        assertIllegalArgument(image, -100, 0, 320, 240, "region must be in image; [-100, 0, 320, 240]");
        assertIllegalArgument(image, 0, -100, 320, 240, "region must be in image; [0, -100, 320, 240]");
        assertNotNull(new AtlasTexture(image, 0, 0, 320, 240));
        assertIllegalArgument(image, 100, 0, 320, 240, "region must be in image; [100, 0, 320, 240]");
        assertIllegalArgument(image, 0, 100, 320, 240, "region must be in image; [0, 100, 320, 240]");
    }


    private static void assertIllegalArgument(BufferedImage image, int x, int y, int width, int height,
                                              String expectedException) {
        FreeFormTextureAtlas.Builder builder = new FreeFormTextureAtlas.Builder(image)
        try {
            new AtlasTexture(image, x, y, width, height);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(expectedException, e.getMessage());
        }
    }
}
