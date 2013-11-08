package all.your.awt;

import org.junit.Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import static all.your.awt.AssertTexture.assertPaint;
import static all.your.awt.MoreColors.*;
import static org.junit.Assert.*;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class SquareGridTextureAtlasTest {

    @Test
    public void requireThatNullImageThrows() {
        try {
            new SquareGridTextureAtlas(null, new Dimension(32, 32));
            fail();
        } catch (NullPointerException e) {
            assertEquals("image", e.getMessage());
        }
    }

    @Test
    public void requireThatSquareWidthMustBePositive() {
        BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        assertIllegalArgument(image, new Dimension(0, 32), "squareSize; java.awt.Dimension[width=0,height=32]");
        assertIllegalArgument(image, new Dimension(32, 0), "squareSize; java.awt.Dimension[width=32,height=0]");
    }

    @Test
    public void requireThatTextureIdIsEncodedRowCol() {
        BufferedImage atlasImage = BufferedImages.newSquareGrid(new Dimension(2, 2), new Color[][] {
                { C0, C1, C2, C3 },
                { C4, C5, C6, C7 },
                { C8, C9, CA, CB },
                { CC, CD, CE, CF },
        });
        SquareGridTextureAtlas atlas = new SquareGridTextureAtlas(atlasImage, new Dimension(4, 4));
        for (int lhsRow = 0; lhsRow < 2; ++lhsRow) {
            for (int lhsCol = 0; lhsCol < 2; ++lhsCol) {
                Texture lhs = atlas.getTexture(lhsRow, lhsCol);
                for (int rhsRow = 0; rhsRow < 2; ++rhsRow) {
                    for (int rhsCol = 0; rhsCol < 2; ++rhsCol) {
                        Texture rhs = atlas.getTexture((rhsRow << 16) | (rhsCol & 0xFFFF));
                        if (lhsRow == rhsRow && lhsCol == rhsCol) {
                            assertSame(lhs, rhs);
                        } else {
                            assertNotSame(lhs, rhs);
                        }
                    }
                }
            }
        }
    }

    @Test
    public void requireThatAtlasTexturesCanBeRendered() {
        BufferedImage atlasImage = BufferedImages.newSquareGrid(new Dimension(2, 2), new Color[][] {
                { C0, C1, C2, C3 },
                { C4, C5, C6, C7 },
                { C8, C9, CA, CB },
                { CC, C0, C2, C3 },
        });
        SquareGridTextureAtlas atlas = new SquareGridTextureAtlas(atlasImage, new Dimension(4, 4));
        assertPaint(atlas.getTexture(0, 0), new Color[][] {
                { C0, C0, C1, C1, },
                { C0, C0, C1, C1, },
                { C4, C4, C5, C5, },
                { C4, C4, C5, C5, },
        });
        assertPaint(atlas.getTexture(0, 1), new Color[][] {
                { C2, C2, C3, C3, },
                { C2, C2, C3, C3, },
                { C6, C6, C7, C7, },
                { C6, C6, C7, C7, },
        });
        assertPaint(atlas.getTexture(1, 0), new Color[][] {
                { C8, C8, C9, C9, },
                { C8, C8, C9, C9, },
                { CC, CC, C0, C0, },
                { CC, CC, C0, C0, },
        });
        assertPaint(atlas.getTexture(1, 1), new Color[][] {
                { CA, CA, CB, CB, },
                { CA, CA, CB, CB, },
                { C2, C2, C3, C3, },
                { C2, C2, C3, C3, },
        });
    }

    private static void assertIllegalArgument(BufferedImage image, Dimension squareSize, String expectedException) {
        try {
            new SquareGridTextureAtlas(image, squareSize);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(expectedException, e.getMessage());
        }
    }
}
