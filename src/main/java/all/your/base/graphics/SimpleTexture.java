package all.your.base.graphics;

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
    public void paint(Surface surface, int x, int y, int width, int height) {
        surface.getGraphics().drawImage(image, x, y, width, height, null);
    }

    public static SimpleTexture fromFile(String fileName) throws IOException {
        return new SimpleTexture(BufferedImages.fromFile(fileName));
    }
}
