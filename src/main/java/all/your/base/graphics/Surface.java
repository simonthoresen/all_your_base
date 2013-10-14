package all.your.base.graphics;

import java.awt.Graphics;
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

    public void paint(Graphics g, int x, int y, int width, int height) {
        if (image.getWidth() != width || image.getHeight() != height) {
            BufferedImage old = image;
            graphics.dispose();

            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            graphics = image.createGraphics();
            graphics.drawImage(old, 0, 0, width, height, null);
        }
        g.drawImage(image, x, y, null);
    }

    public BufferedImage getImage() {
        return image;
    }

    public Graphics2D getGraphics() {
        return graphics;
    }
}
