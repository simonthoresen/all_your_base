package all.your.awt;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class MoreColors {

    public static final Color C_ = null;
    public static final Color C0 = new Color(0x000033);
    public static final Color C1 = new Color(0x000077);
    public static final Color C2 = new Color(0x0000BB);
    public static final Color C3 = new Color(0x0000FF);
    public static final Color C4 = new Color(0x003300);
    public static final Color C5 = new Color(0x007700);
    public static final Color C6 = new Color(0x00BB00);
    public static final Color C7 = new Color(0x00FF00);
    public static final Color C8 = new Color(0x330000);
    public static final Color C9 = new Color(0x770000);
    public static final Color CA = new Color(0xBB0000);
    public static final Color CB = new Color(0xFF0000);
    public static final Color CC = new Color(0x333300);
    public static final Color CD = new Color(0x777700);
    public static final Color CE = new Color(0xBBBB00);
    public static final Color CF = new Color(0xFFFF00);

    public static final List<Color> COLORS = Arrays.asList(C0, C1, C2, C3, C4, C5, C6, C7,
                                                           C8, C9, CA, CB, CC, CD, CE, CF);
    public static final Map<Integer, Character> SYMBOLS = newSymbolMap(COLORS);

    public static String render(Color[][] pixels) {
        StringBuilder out = new StringBuilder();
        for (Point p = new Point(0, 0); p.y < pixels.length; ++p.y) {
            for (p.x = 0; p.x < pixels[p.y].length; ++p.x) {
                out.append(resolveSymbol(pixels[p.y][p.x].getRGB()));
            }
            out.append('\n');
        }
        return out.toString().toUpperCase(Locale.US);
    }

    public static String render(BufferedImage image) {
        StringBuilder out = new StringBuilder();
        for (Point p = new Point(0, 0); p.y < image.getHeight(); ++p.y) {
            for (p.x = 0; p.x < image.getWidth(); ++p.x) {
                out.append(resolveSymbol(image.getRGB(p.x, p.y)));
            }
            out.append('\n');
        }
        return out.toString().toUpperCase(Locale.US);
    }

    private static char resolveSymbol(int rgb) {
        Character symbol = SYMBOLS.get(rgb);
        return symbol != null ? symbol : '?';
    }

    private static Map<Integer, Character> newSymbolMap(List<Color> colors) {
        Map<Integer, Character> out = new HashMap<>();
        for (int i = 0, len = colors.size(); i < len; ++i) {
            out.put(colors.get(i).getRGB(), String.format("%X", i).charAt(0));
        }
        return out;
    }
}
