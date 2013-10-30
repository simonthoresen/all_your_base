package all.your.awt;

import com.google.common.base.Preconditions;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class AtlasTexture implements Texture {

    private final BufferedImage image;
    private final int x, y, width, height;

    AtlasTexture(BufferedImage image, int x, int y, int width, int height) {
        Objects.requireNonNull(image, "image");
        Preconditions.checkArgument(x >= 0 && x + width <= image.getWidth() &&
                                    y >= 0 && y + height <= image.getHeight(),
                                    "region must be in image; [%s, %s, %s, %s]", x, y, width, height);
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void paint(Graphics2D g, int x, int y, int width, int height) {
        g.drawImage(image, x, y, x + width, y + height,
                    this.x, this.y, this.x + this.width, this.y + this.height, null);
    }
}
