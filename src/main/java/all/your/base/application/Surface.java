package all.your.base.application;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class Surface {

    private BufferedImage image;
    private Graphics2D graphics;

    public Surface(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();
    }

    public void resize(int width, int height) {
        if (image.getWidth() == width && image.getHeight() == height) {
            return;
        }
        BufferedImage old = image;
        graphics.dispose();

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();
        graphics.drawImage(old, 0, 0, width, height, null);
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public Graphics2D getGraphics() {
        return graphics;
    }

    BufferedImage getImage() {
        return image;
    }
}
