package all.your.awt;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class AssertImage {

    public static void assertPixels(BufferedImage actualImage, Color[][] expectedPixels) {
        assertEquals(expectedPixels.length, actualImage.getHeight());
        assertEquals(expectedPixels[0].length, actualImage.getWidth());
        for (Point p = new Point(0, 0); p.y < expectedPixels.length; ++p.y) {
            for (p.x = 0; p.x < expectedPixels[p.y].length; ++p.x) {
                int expected = expectedPixels[p.y][p.x].getRGB();
                int actual = actualImage.getRGB(p.x, p.y);
                if (expected != actual) {
                    assertEquals(Palette.render(expectedPixels), Palette.render(actualImage));
                    assertEquals(String.format("0x%08X", expected),
                                 String.format("0x%08X", actual));
                }
            }
        }
    }
}
