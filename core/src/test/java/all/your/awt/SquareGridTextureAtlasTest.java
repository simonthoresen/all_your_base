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
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class SquareGridTextureAtlasTest {

    @Test
    public void requireThatNullImageThrows() {
        try {
            new SquareGridTextureAtlas(null, 32, 32);
            fail();
        } catch (NullPointerException e) {
            assertEquals("image", e.getMessage());
        }
    }

    @Test
    public void requireThatSquareWidthMustBePositive() {
        BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        assertIllegalArgument(image, 0, 32, "squareWidth must be positive; 0");
        assertIllegalArgument(image, 32, 0, "squareHeight must be positive; 0");
    }

    @Test
    public void requireThatTextureIdIsEncodedRowCol() {
        BufferedImage atlasImage = BufferedImages.newSquareGrid(2, 2, new Color[][] {
                { Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY },
                { Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA },
                { Color.ORANGE, Color.PINK, Color.RED, Color.WHITE },
                { Color.YELLOW, Color.BLACK, Color.CYAN, Color.DARK_GRAY },
        });
        SquareGridTextureAtlas atlas = new SquareGridTextureAtlas(atlasImage, 4, 4);
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
        BufferedImage atlasImage = BufferedImages.newSquareGrid(2, 2, new Color[][] {
                { Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY },
                { Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA },
                { Color.ORANGE, Color.PINK, Color.RED, Color.WHITE },
                { Color.YELLOW, Color.BLACK, Color.CYAN, Color.DARK_GRAY },
        });
        SquareGridTextureAtlas atlas = new SquareGridTextureAtlas(atlasImage, 4, 4);
        assertPaint(atlas.getTexture(0, 0), new Color[][] {
                { Color.BLACK, Color.BLACK, Color.BLUE, Color.BLUE, },
                { Color.BLACK, Color.BLACK, Color.BLUE, Color.BLUE, },
                { Color.GRAY, Color.GRAY, Color.GREEN, Color.GREEN, },
                { Color.GRAY, Color.GRAY, Color.GREEN, Color.GREEN, },
        });
        assertPaint(atlas.getTexture(0, 1), new Color[][] {
                { Color.CYAN, Color.CYAN, Color.DARK_GRAY, Color.DARK_GRAY, },
                { Color.CYAN, Color.CYAN, Color.DARK_GRAY, Color.DARK_GRAY, },
                { Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.MAGENTA, },
                { Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.MAGENTA, },
        });
        assertPaint(atlas.getTexture(1, 0), new Color[][] {
                { Color.ORANGE, Color.ORANGE, Color.PINK, Color.PINK, },
                { Color.ORANGE, Color.ORANGE, Color.PINK, Color.PINK, },
                { Color.YELLOW, Color.YELLOW, Color.BLACK, Color.BLACK, },
                { Color.YELLOW, Color.YELLOW, Color.BLACK, Color.BLACK, },
        });
        assertPaint(atlas.getTexture(1, 1), new Color[][] {
                { Color.RED, Color.RED, Color.WHITE, Color.WHITE, },
                { Color.RED, Color.RED, Color.WHITE, Color.WHITE, },
                { Color.CYAN, Color.CYAN, Color.DARK_GRAY, Color.DARK_GRAY, },
                { Color.CYAN, Color.CYAN, Color.DARK_GRAY, Color.DARK_GRAY, },
        });
    }

    private static void assertIllegalArgument(BufferedImage image, int squareWidth, int squareHeight,
                                              String expectedException) {
        try {
            new SquareGridTextureAtlas(image, squareWidth, squareHeight);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(expectedException, e.getMessage());
        }
    }
}
