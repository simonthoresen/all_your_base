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
        for (Point p = new Point(0, 0); p.x < actualImage.getWidth(); ++p.x) {
            for (p.y = 0; p.y < actualImage.getHeight(); ++p.y) {
                assertEquals(String.valueOf(p), expectedPixels[p.y][p.x], new Color(actualImage.getRGB(p.x, p.y)));
            }
        }
    }
}
