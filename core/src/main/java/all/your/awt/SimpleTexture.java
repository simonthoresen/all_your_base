package all.your.awt;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class SimpleTexture implements Texture {

    private final BufferedImage image;

    public SimpleTexture(BufferedImage image) {
        Objects.requireNonNull(image, "image");
        this.image = image;
    }

    @Override
    public void paint(Graphics2D g, Rectangle viewport) {
        paint(g, viewport, 0, 0, image.getWidth(), image.getHeight());
    }

    @Override
    public void paint(Graphics2D g, Rectangle viewport, Rectangle textureRegion) {
        paint(g, viewport, textureRegion.x, textureRegion.y, textureRegion.width, textureRegion.height);
    }

    private void paint(Graphics2D g, Rectangle viewport, int x, int y, int width, int height) {
        g.drawImage(image, viewport.x, viewport.y, viewport.x + viewport.width, viewport.y + viewport.height,
                    x, y, x + width, y + height, null);
    }

    @Override
    public Dimension getSize(Dimension out) {
        out.width = image.getWidth();
        out.height = image.getHeight();
        return out;
    }
}
