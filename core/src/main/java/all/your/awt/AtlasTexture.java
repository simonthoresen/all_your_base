package all.your.awt;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class AtlasTexture implements Texture {

    private final BufferedImage atlas;
    private final int x, y, width, height;

    AtlasTexture(BufferedImage atlas, int x, int y, int width, int height) {
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
