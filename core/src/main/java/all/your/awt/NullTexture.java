package all.your.awt;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
class NullTexture extends Texture {

    public static final NullTexture INSTANCE = new NullTexture();

    private NullTexture() {
        super(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
    }

    @Override
    public void paint(Graphics2D g, Rectangle viewport) {

    }

    @Override
    public Rectangle getBounds(Rectangle out) {
        return out;
    }
}
