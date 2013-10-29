package all.your.awt;

import com.google.common.base.Preconditions;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class AtlasTexture implements Texture {

    private final BufferedImage atlas;
    private final int x, y, width, height;

    AtlasTexture(BufferedImage atlas, int x, int y, int width, int height) {
        Objects.requireNonNull(atlas, "atlas");
        Preconditions.checkArgument(x > 0, "x <= 0");
        Preconditions.checkArgument(y > 0, "y <= 0");
        Preconditions.checkArgument(width > 0, "width <= 0");
        Preconditions.checkArgument(height > 0, "height <= 0");
        this.atlas = atlas;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void paint(Graphics2D g, int x, int y, int width, int height) {
        g.drawImage(atlas, x, y, x + width, y + height,
                    this.x, this.y, this.x + this.width, this.y + this.height, null);
    }
}
