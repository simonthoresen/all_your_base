package all.your.awt;

import org.junit.Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static all.your.awt.AssertTexture.assertPaint;
import static all.your.awt.MoreColors.C0;
import static all.your.awt.MoreColors.C1;
import static all.your.awt.MoreColors.C2;
import static all.your.awt.MoreColors.C3;
import static all.your.awt.MoreColors.C4;
import static all.your.awt.MoreColors.C5;
import static all.your.awt.MoreColors.C6;
import static all.your.awt.MoreColors.C7;
import static all.your.awt.MoreColors.C8;
import static all.your.awt.MoreColors.C9;
import static all.your.awt.MoreColors.CA;
import static all.your.awt.MoreColors.CB;
import static all.your.awt.MoreColors.CC;
import static all.your.awt.MoreColors.CD;
import static all.your.awt.MoreColors.CE;
import static all.your.awt.MoreColors.CF;
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
            assertEquals("atlas", e.getMessage());
        }
    }

    @Test
    public void requireThatTextureIdMustBeUnique() {
        BufferedImage image = new BufferedImage(320, 240, BufferedImage.TYPE_INT_ARGB);
        FreeFormTextureAtlas.Builder builder = new FreeFormTextureAtlas.Builder(new Texture(image));
        builder.addTexture(6, new Rectangle(0, 0, 32, 24));
        builder.addTexture(9, new Rectangle(0, 0, 32, 24));
        try {
            builder.addTexture(6, new Rectangle(0, 0, 32, 24));
            fail();
        } catch (IllegalStateException e) {
            assertEquals("id '6' already in use", e.getMessage());
        }
    }

    @Test
    public void requireThatTextureRegionMustBeInImage() {
        BufferedImage image = new BufferedImage(320, 240, BufferedImage.TYPE_INT_ARGB);
        assertIllegalArgument(image, new Rectangle(-100, 0, 320, 240),
                              "region; java.awt.Rectangle[x=-100,y=0,width=320,height=240]");
        assertIllegalArgument(image, new Rectangle(0, -100, 320, 240),
                              "region; java.awt.Rectangle[x=0,y=-100,width=320,height=240]");
        assertIllegalArgument(image, new Rectangle(100, 0, 320, 240),
                              "region; java.awt.Rectangle[x=100,y=0,width=320,height=240]");
        assertIllegalArgument(image, new Rectangle(0, 100, 320, 240),
                              "region; java.awt.Rectangle[x=0,y=100,width=320,height=240]");
    }

    @Test
    public void requireThatTexturesCanBeRetrieved() {
        BufferedImage image = new BufferedImage(320, 240, BufferedImage.TYPE_INT_ARGB);
        FreeFormTextureAtlas atlas = new FreeFormTextureAtlas.Builder(new Texture(image))
                .addTexture(6, new Rectangle(0, 0, 32, 24))
                .addTexture(9, new Rectangle(0, 0, 32, 24))
                .build();
        assertSame(atlas.getTexture(6), atlas.getTexture(6));
        assertSame(atlas.getTexture(9), atlas.getTexture(9));
        assertNotSame(atlas.getTexture(6), atlas.getTexture(9));
    }

    @Test
    public void requireThatTextureCanBePainted() {
        Texture atlasTexture = Textures.newSquareGrid(new Dimension(1, 1), new Color[][] {
                { C0, C1, C2, C3 },
                { C4, C5, C6, C7 },
                { C8, C9, CA, CB },
                { CC, CD, CE, CF },
        });
        FreeFormTextureAtlas atlas = new FreeFormTextureAtlas.Builder(atlasTexture)
                .addTexture(0, new Rectangle(0, 0, 3, 1))
                .addTexture(1, new Rectangle(1, 1, 2, 3))
                .build();
        assertPaint(atlas.getTexture(0), new Color[][] {
                { C0, C1, C2 },
        });
        assertPaint(atlas.getTexture(1), new Color[][] {
                { C5, C6 },
                { C9, CA },
                { CD, CE },
        });
    }

    private static void assertIllegalArgument(BufferedImage image, Rectangle region, String expectedException) {
        FreeFormTextureAtlas.Builder builder = new FreeFormTextureAtlas.Builder(new Texture(image));
        try {
            builder.addTexture(69, region);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(expectedException, e.getMessage());
        }
    }
}
