package all.your.awt;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class SimpleTexture implements Texture {

    private final BufferedImage image;

    public SimpleTexture(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void paint(Graphics2D g, int x, int y, int width, int height) {
        g.drawImage(image, x, y, width, height, null);
    }

    public static SimpleTexture fromFile(String fileName) throws IOException {
        return new SimpleTexture(BufferedImages.fromFile(fileName));
    }
}
