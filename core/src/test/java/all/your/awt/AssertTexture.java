package all.your.awt;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static all.your.awt.AssertImage.assertPixels;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class AssertTexture {

    public static void assertPaint(Texture texture, Color[][] expectedPixels) {
        int width = expectedPixels[0].length;
        int height = expectedPixels.length;

        BufferedImage image = new BufferedImage(width * 2, height * 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        texture.paint(g, new Rectangle(width, height, width, height));
        g.dispose();

        Color[][] expectedImage = new Color[image.getHeight()][image.getWidth()];
        for (int y = 0; y < expectedImage.length; ++y) {
            for (int x = 0; x < expectedImage[y].length; ++x) {
                expectedImage[y][x] = (x < width || y < height) ? Color.BLACK : expectedPixels[y - height][x - width];
            }
        }
        assertPixels(image, expectedImage);
    }
}
