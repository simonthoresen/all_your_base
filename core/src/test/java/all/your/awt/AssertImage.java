package all.your.awt;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class AssertImage {

    public static void assertPixels(BufferedImage actualImage, Color[][] expectedPixels) {
        Map<Color, Integer> palette = newPalette(expectedPixels);
        StringBuilder expected = new StringBuilder();
        for (Point p = new Point(0, 0); p.y < expectedPixels.length; ++p.y) {
            for (p.x = 0; p.x < expectedPixels[p.y].length; ++p.x) {
                expected.append(Integer.toHexString(palette.get(expectedPixels[p.y][p.x])));
            }
            expected.append('\n');
        }
        StringBuilder actual = new StringBuilder();
        for (Point p = new Point(0, 0); p.y < actualImage.getHeight(); ++p.y) {
            for (p.x = 0; p.x < actualImage.getWidth(); ++p.x) {
                Color color = new Color(actualImage.getRGB(p.x, p.y));
                Integer idx = palette.get(color);
                assertNotNull("unknown " + color.toString() + " at " + p, idx);
                actual.append(Integer.toHexString(idx));
            }
            actual.append('\n');
        }
        assertEquals(expected.toString(), actual.toString());
    }

    public static Map<Color, Integer> newPalette(Color[][] pixels) {
        Map<Color, Integer> palette = new HashMap<>();
        for (Color[] colors : pixels) {
            for (Color color : colors) {
                if (!palette.containsKey(color)) {
                    palette.put(color, palette.size());
                }
            }
        }
        assertTrue(palette.size() < 16);
        return palette;
    }
}
