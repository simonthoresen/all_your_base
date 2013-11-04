package all.your.awt;

import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;

import static all.your.awt.AssertTexture.assertPaint;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
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
            assertEquals("id '6' already in use", e.getMessage());
        }
    }

    @Test
    public void requireThatTextureRegionMustBeInImage() {
        BufferedImage image = new BufferedImage(320, 240, BufferedImage.TYPE_INT_ARGB);
        assertIllegalArgument(image, -100, 0, 320, 240, "region [-100, 0, 320, 240] not in image [0, 0, 320, 240]");
        assertIllegalArgument(image, 0, -100, 320, 240, "region [0, -100, 320, 240] not in image [0, 0, 320, 240]");
        assertIllegalArgument(image, 100, 0, 320, 240, "region [100, 0, 320, 240] not in image [0, 0, 320, 240]");
        assertIllegalArgument(image, 0, 100, 320, 240, "region [0, 100, 320, 240] not in image [0, 0, 320, 240]");

    }

    @Test
    public void requireThatTexturesCanBeRetrieved() {
        BufferedImage image = new BufferedImage(320, 240, BufferedImage.TYPE_INT_ARGB);
        FreeFormTextureAtlas atlas = new FreeFormTextureAtlas.Builder(image)
                .addTexture(6, 0, 0, 32, 24)
                .addTexture(9, 0, 0, 32, 24)
                .build();
        assertSame(atlas.getTexture(6), atlas.getTexture(6));
        assertSame(atlas.getTexture(9), atlas.getTexture(9));
        assertNotSame(atlas.getTexture(6), atlas.getTexture(9));
    }

    @Test
    public void requireThatTextureCanBePainted() {
        BufferedImage atlasImage = BufferedImages.newSquareGrid(1, 1, new Color[][] {
                { Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY },
                { Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA },
                { Color.ORANGE, Color.PINK, Color.RED, Color.WHITE },
                { Color.YELLOW, Color.BLACK, Color.CYAN, Color.DARK_GRAY },
        });
        FreeFormTextureAtlas atlas = new FreeFormTextureAtlas.Builder(atlasImage)
                .addTexture(0, 0, 0, 3, 1)
                .addTexture(1, 1, 1, 2, 3)
                .build();
        assertPaint(atlas.getTexture(0), new Color[][] {
                { Color.BLACK, Color.BLUE, Color.CYAN },
        });
        assertPaint(atlas.getTexture(1), new Color[][] {
                { Color.GREEN, Color.LIGHT_GRAY },
                { Color.PINK, Color.RED },
                { Color.BLACK, Color.CYAN },
        });
    }

    private static void assertIllegalArgument(BufferedImage image, int x, int y, int width, int height,
                                              String expectedException) {
        FreeFormTextureAtlas.Builder builder = new FreeFormTextureAtlas.Builder(image);
        try {
            builder.addTexture(69, x, y, width, height);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(expectedException, e.getMessage());
        }
    }
}
