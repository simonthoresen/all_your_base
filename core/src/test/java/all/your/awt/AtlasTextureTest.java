package all.your.awt;

import org.junit.Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static all.your.awt.MoreColors.*;
import static org.junit.Assert.*;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class AtlasTextureTest {

    @Test
    public void requireThatNullImageThrows() {
        try {
            new AtlasTexture(null, new Rectangle(0, 0, 1, 1));
            fail();
        } catch (NullPointerException e) {
            assertEquals("image", e.getMessage());
        }
    }

    @Test
    public void requireThatRegionMustBeIsInImage() {
        BufferedImage image = new BufferedImage(320, 240, BufferedImage.TYPE_INT_ARGB);
        assertIllegalArgument(image, new Rectangle(-100, 0, 320, 240),
                              "region; java.awt.Rectangle[x=-100,y=0,width=320,height=240]");
        assertIllegalArgument(image, new Rectangle(0, -100, 320, 240),
                              "region; java.awt.Rectangle[x=0,y=-100,width=320,height=240]");
        assertNotNull(new AtlasTexture(image, new Rectangle(0, 0, 320, 240)));
        assertIllegalArgument(image, new Rectangle(100, 0, 320, 240),
                              "region; java.awt.Rectangle[x=100,y=0,width=320,height=240]");
        assertIllegalArgument(image, new Rectangle(0, 100, 320, 240),
                              "region; java.awt.Rectangle[x=0,y=100,width=320,height=240]");
    }

    @Test
    public void requireThatOnlySpecifiedRegionOfAtlasIsPainted() {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(C0);
        g.fillRect(0, 0, 100, 100);
        new AtlasTexture(BufferedImages.newSquareGrid(new Dimension(2, 2), new Color[][] {
                { C1, C2, C3, C4 },
                { C2, C3, C4, C1 },
                { C3, C4, C1, C2 },
                { C4, C1, C2, C3 }
        }), new Rectangle(2, 2, 4, 4)).paint(g, new Rectangle(25, 25, 50, 50));
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

    private static void assertIllegalArgument(BufferedImage image, Rectangle region, String expectedException) {
        try {
            new AtlasTexture(image, region);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(expectedException, e.getMessage());
        }
    }
}
