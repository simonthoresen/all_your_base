package all.your.awt;

import java.awt.Color;
import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class AssertImage {

    public static void assertPixels(BufferedImage actualImage, Color[][] expectedPixels) {
        for (int x = 0; x < actualImage.getWidth(); ++x) {
            for (int y = 0; y < actualImage.getHeight(); ++y) {
                assertEquals(expectedPixels[y][x], new Color(actualImage.getRGB(x, y)));
            }
        }
    }
}
