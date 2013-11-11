package all.your.awt;

import org.junit.Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

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
public class TextureAtlasTest {

    @Test
    public void requireThatNullImageThrows() {
        TextureAtlas.Builder builder = new TextureAtlas.Builder();
        try {
            builder.setAtlasTexture(null);
            fail();
        } catch (NullPointerException e) {
            assertEquals("atlasTexture", e.getMessage());
        }
    }

    @Test
    public void requireThatNonPositiveSquareSizeThrows() {
        TextureAtlas.Builder builder = new TextureAtlas.Builder();
        try {
            builder.setSquareSize(new Dimension(0, 32));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("squareSize; java.awt.Dimension[width=0,height=32]", e.getMessage());
        }
        try {
            builder.setSquareSize(new Dimension(32, 0));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("squareSize; java.awt.Dimension[width=32,height=0]", e.getMessage());
        }
    }

    @Test
    public void requireThatTextureIdMustBeUnique() {
        TextureAtlas.Builder builder = new TextureAtlas.Builder();
        builder.setAtlasTexture(Textures.newFilled(new Dimension(32, 32), C0));
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
        TextureAtlas.Builder builder = new TextureAtlas.Builder();
        builder.setAtlasTexture(Textures.newFilled(new Dimension(320, 240), C0));
        try {
            builder.addTexture(69, new Rectangle(-100, 0, 320, 240));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("region; java.awt.Rectangle[x=-100,y=0,width=320,height=240]", e.getMessage());
        }
    }

    @Test
    public void requireThatTexturesCanBeRetrieved() {
        TextureAtlas atlas = new TextureAtlas.Builder()
                .setAtlasTexture(Textures.newFilled(new Dimension(320, 240), C0))
                .addTexture(6, new Rectangle(0, 0, 32, 24))
                .addTexture(9, new Rectangle(0, 0, 32, 24))
                .build();
        assertSame(atlas.getTexture(6), atlas.getTexture(6));
        assertSame(atlas.getTexture(9), atlas.getTexture(9));
        assertNotSame(atlas.getTexture(6), atlas.getTexture(9));
    }

    @Test
    public void requireThatGridTexturesCanBeRendered() {
        TextureAtlas atlas = new TextureAtlas.Builder()
                .setAtlasTexture(Textures.newSquareGrid(new Dimension(2, 2), new Color[][] {
                        { C0, C1, C2, C3 },
                        { C4, C5, C6, C7 },
                        { C8, C9, CA, CB },
                        { CC, C0, C2, C3 },
                }))
                .setSquareSize(new Dimension(4, 4))
                .build();
        assertPaint(atlas.getTexture(new Point(0, 0)), new Color[][] {
                { C0, C0, C1, C1, },
                { C0, C0, C1, C1, },
                { C4, C4, C5, C5, },
                { C4, C4, C5, C5, },
        });
        assertPaint(atlas.getTexture(new Point(1, 0)), new Color[][] {
                { C2, C2, C3, C3, },
                { C2, C2, C3, C3, },
                { C6, C6, C7, C7, },
                { C6, C6, C7, C7, },
        });
        assertPaint(atlas.getTexture(new Point(0, 1)), new Color[][] {
                { C8, C8, C9, C9, },
                { C8, C8, C9, C9, },
                { CC, CC, C0, C0, },
                { CC, CC, C0, C0, },
        });
        assertPaint(atlas.getTexture(new Point(1, 1)), new Color[][] {
                { CA, CA, CB, CB, },
                { CA, CA, CB, CB, },
                { C2, C2, C3, C3, },
                { C2, C2, C3, C3, },
        });
    }

    @Test
    public void requireThatFreeFormTextureCanBePainted() {
        TextureAtlas atlas = new TextureAtlas.Builder()
                .setAtlasTexture(Textures.newSquareGrid(new Dimension(1, 1), new Color[][] {
                        { C0, C1, C2, C3 },
                        { C4, C5, C6, C7 },
                        { C8, C9, CA, CB },
                        { CC, CD, CE, CF },
                }))
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
}
