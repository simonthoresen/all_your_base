package all.your.awt;

import org.junit.Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static all.your.awt.AssertImage.assertPixels;
import static all.your.awt.MoreColors.C0;
import static all.your.awt.MoreColors.C1;
import static all.your.awt.MoreColors.C2;
import static all.your.awt.MoreColors.C3;
import static all.your.awt.MoreColors.C4;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TextureTest {

    @Test
    public void requireThatNullImageThrows() {
        try {
            new Texture(null);
            fail();
        } catch (NullPointerException e) {
            assertEquals("image", e.getMessage());
        }
        try {
            new Texture(null, new Rectangle(0, 0, 1, 1));
            fail();
        } catch (NullPointerException e) {
            assertEquals("texture", e.getMessage());
        }
    }

    @Test
    public void requireThatRegionMustBeIsInImage() {
        BufferedImage image = new BufferedImage(320, 240, BufferedImage.TYPE_INT_ARGB);
        assertIllegalArgument(image, new Rectangle(-100, 0, 320, 240),
                              "region; java.awt.Rectangle[x=-100,y=0,width=320,height=240]");
        assertIllegalArgument(image, new Rectangle(0, -100, 320, 240),
                              "region; java.awt.Rectangle[x=0,y=-100,width=320,height=240]");
        assertNotNull(new Texture(new Texture(image), new Rectangle(0, 0, 320, 240)));
        assertIllegalArgument(image, new Rectangle(100, 0, 320, 240),
                              "region; java.awt.Rectangle[x=100,y=0,width=320,height=240]");
        assertIllegalArgument(image, new Rectangle(0, 100, 320, 240),
                              "region; java.awt.Rectangle[x=0,y=100,width=320,height=240]");
    }

    @Test
    public void requireThatOnlySpecifiedRegionOfAtlasIsPainted() {
        BufferedImage image = Images.newInstance(new Dimension(100, 100), C0);
        Graphics2D g = image.createGraphics();
        new Texture(Textures.newSquareGrid(new Color[][] {
                { C1, C2, C3, C4 },
                { C2, C3, C4, C1 },
                { C3, C4, C1, C2 },
                { C4, C1, C2, C3 }
        }), new Rectangle(1, 1, 2, 2)).paint(g, new Rectangle(25, 25, 50, 50));
        g.dispose();

        for (int y = 0; y < 100; ++y) {
            for (int x = 0; x < 100; ++x) {
                Color actual = new Color(image.getRGB(x, y));
                Color expected = C0;
                if (y >= 25 && y < 50) {
                    if (x >= 25 && x < 50) {
                        expected = C3;
                    } else if (x >= 50 && x < 75) {
                        expected = C4;
                    }
                } else if (y >= 50 && y < 75) {
                    if (x >= 25 && x < 50) {
                        expected = C4;
                    } else if (x >= 50 && x < 75) {
                        expected = C1;
                    }
                }
                assertEquals("(" + x + "," + y + ")", expected, actual);
            }
        }
    }

    @Test
    public void requireThatTextureIsDrawnToGivenRectangle() {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(C0);
        g.fillRect(0, 0, 100, 100);
        Textures.newSquareGrid(new Color[][] {
                { C1, C2 },
                { C3, C4 }
        }).paint(g, new Rectangle(25, 25, 50, 50));
        g.dispose();

        Color[][] expected = new Color[100][100];
        for (int y = 0; y < 100; ++y) {
            for (int x = 0; x < 100; ++x) {
                expected[y][x] = C0;
                if (y >= 25 && y < 50) {
                    if (x >= 25 && x < 50) {
                        expected[y][x] = C1;
                    } else if (x >= 50 && x < 75) {
                        expected[y][x] = C2;
                    }
                } else if (y >= 50 && y < 75) {
                    if (x >= 25 && x < 50) {
                        expected[y][x] = C3;
                    } else if (x >= 50 && x < 75) {
                        expected[y][x] = C4;
                    }
                }
            }
        }
        assertPixels(image, expected);
    }

    private static void assertIllegalArgument(BufferedImage image, Rectangle region, String expectedException) {
        try {
            new Texture(new Texture(image), region);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(expectedException, e.getMessage());
        }
    }
}
