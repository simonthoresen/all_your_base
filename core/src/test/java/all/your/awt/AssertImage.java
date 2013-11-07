package all.your.awt;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class AssertImage {

    public static void assertPixels(BufferedImage actualImage, Color[][] expectedPixels) {
        Palette palette = newPalette(actualImage, expectedPixels);
        StringBuilder expected = new StringBuilder();
        for (Point p = new Point(0, 0); p.y < expectedPixels.length; ++p.y) {
            for (p.x = 0; p.x < expectedPixels[p.y].length; ++p.x) {
                expected.append(palette.get(expectedPixels[p.y][p.x]));
            }
            expected.append('\n');
        }
        StringBuilder actual = new StringBuilder();
        for (Point p = new Point(0, 0); p.y < actualImage.getHeight(); ++p.y) {
            for (p.x = 0; p.x < actualImage.getWidth(); ++p.x) {
                actual.append(palette.get(new Color(actualImage.getRGB(p.x, p.y))));
            }
            actual.append('\n');
        }
        assertEquals(expected.toString(), actual.toString());
    }

    public static Palette newPalette(BufferedImage image, Color[][] pixels) {
        Palette palette = new Palette();
        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                palette.add(new Color(image.getRGB(x, y)));
            }
        }
        for (Color[] colors : pixels) {
            for (Color color : colors) {
                palette.add(color);
            }
        }
        return palette;
    }

    public static class Palette {

        private final static String SYMBOLS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        private final Map<Color, Character> colors = new HashMap<>();

        public void add(Color color) {
            if (colors.containsKey(color)) {
                return;
            }
            colors.put(color, SYMBOLS.charAt(colors.size()));
        }

        public Character get(Color color) {
            return colors.get(color);
        }
    }
}
