package all.your.awt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class Images {

    public static BufferedImage newInstance(Dimension size) {
        Objects.requireNonNull(size, "size");
        return new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
    }

    public static BufferedImage newInstance(Dimension size, Color fill) {
        Objects.requireNonNull(size, "size");
        Objects.requireNonNull(fill, "fill");
        BufferedImage image = newInstance(size);
        Graphics2D g = image.createGraphics();
        g.setColor(fill);
        g.fillRect(0, 0, size.width, size.height);
        g.dispose();
        return image;
    }
}
