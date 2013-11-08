package all.your.awt;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Locale;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class MoreColors {

    public static final Color[] COLORS = new Color[] {
            new Color(0x000033),
            new Color(0x000077),
            new Color(0x0000BB),
            new Color(0x0000FF),
            new Color(0x003300),
            new Color(0x007700),
            new Color(0x00BB00),
            new Color(0x00FF00),
            new Color(0x330000),
            new Color(0x770000),
            new Color(0xBB0000),
            new Color(0xFF0000),
            new Color(0x333300),
            new Color(0x777700),
            new Color(0xBBBB00),
            new Color(0xFFFF00),
    };
    public static final Color C_ = null;
    public static final Color C0 = COLORS[0x00];
    public static final Color C1 = COLORS[0x01];
    public static final Color C2 = COLORS[0x02];
    public static final Color C3 = COLORS[0x03];
    public static final Color C4 = COLORS[0x04];
    public static final Color C5 = COLORS[0x05];
    public static final Color C6 = COLORS[0x06];
    public static final Color C7 = COLORS[0x07];
    public static final Color C8 = COLORS[0x08];
    public static final Color C9 = COLORS[0x09];
    public static final Color CA = COLORS[0x0A];
    public static final Color CB = COLORS[0x0B];
    public static final Color CC = COLORS[0x0C];
    public static final Color CD = COLORS[0x0D];
    public static final Color CE = COLORS[0x0E];
    public static final Color CF = COLORS[0x0F];

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
        for (int i = 0; i < COLORS.length; ++i) {
            if (rgb == COLORS[i].getRGB()) {
                return Character.forDigit(i, 16);
            }
        }
        return '?';
    }
}
