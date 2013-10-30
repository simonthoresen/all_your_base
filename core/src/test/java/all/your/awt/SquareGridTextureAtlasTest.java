package all.your.awt;

import org.junit.Test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;
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
    public void requireThatAtlasTexturesCanBeRendered() {
        BufferedImage atlasImage = BufferedImages.newSquareGrid(2, 2, new Color[][] {
                { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN },
                { Color.ORANGE, Color.YELLOW, Color.GREEN, Color.RED },
                { Color.YELLOW, Color.GREEN, Color.RED, Color.ORANGE },
                { Color.GREEN, Color.RED, Color.ORANGE, Color.YELLOW },
        });
        SquareGridTextureAtlas atlas = new SquareGridTextureAtlas(atlasImage, 4, 4);
        assertPaint(atlas.getTexture(0, 0), new Color[][] {
                { Color.RED, Color.RED, Color.ORANGE, Color.ORANGE, },
                { Color.RED, Color.RED, Color.ORANGE, Color.ORANGE, },
                { Color.ORANGE, Color.ORANGE, Color.YELLOW, Color.YELLOW, },
                { Color.ORANGE, Color.ORANGE, Color.YELLOW, Color.YELLOW, },
        });
        assertPaint(atlas.getTexture(1, 0), new Color[][] {
                { Color.YELLOW, Color.YELLOW, Color.GREEN, Color.GREEN, },
                { Color.YELLOW, Color.YELLOW, Color.GREEN, Color.GREEN, },
                { Color.GREEN, Color.GREEN, Color.RED, Color.RED, },
                { Color.GREEN, Color.GREEN, Color.RED, Color.RED, },
        });
        assertPaint(atlas.getTexture(0, 1), new Color[][] {
                { Color.YELLOW, Color.YELLOW, Color.GREEN, Color.GREEN, },
                { Color.YELLOW, Color.YELLOW, Color.GREEN, Color.GREEN, },
                { Color.GREEN, Color.GREEN, Color.RED, Color.RED, },
                { Color.GREEN, Color.GREEN, Color.RED, Color.RED, },
        });
        assertPaint(atlas.getTexture(1, 1), new Color[][] {
                { Color.RED, Color.RED, Color.ORANGE, Color.ORANGE, },
                { Color.RED, Color.RED, Color.ORANGE, Color.ORANGE, },
                { Color.ORANGE, Color.ORANGE, Color.YELLOW, Color.YELLOW, },
                { Color.ORANGE, Color.ORANGE, Color.YELLOW, Color.YELLOW, },
        });
    }

    private static void assertPaint(Texture texture, Color[][] expectedPixels) {
        int width = expectedPixels[0].length;
        int height = expectedPixels.length;

        BufferedImage image = new BufferedImage(width * 2, height * 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        texture.paint(g, width, height, width, height);
        ImageViewer.show(image, 10);
        for (int x = 0; x < image.getWidth(); ++x) {
            for (int y = 0; y < image.getHeight(); ++y) {
                Color actual = new Color(image.getRGB(x, y));
                if (x < width || y < height) {
                    assertEquals(Color.BLACK, actual);
                } else {
                    assertEquals(expectedPixels[y - height][x - width], actual);
                }
            }
        }
        g.dispose();
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
