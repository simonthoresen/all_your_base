package all.your.awt;

import all.your.swing.AbstractApplicationState;
import all.your.swing.ApplicationBuilder;
import all.your.swing.Surface;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class ImageViewer {

    public static void show(final BufferedImage image, final int scale) {
        try {
            new ApplicationBuilder()
                    .setWindowWidth(image.getWidth() * scale)
                    .setWindowHeight(image.getHeight() * scale)
                    .setInitialState(new AbstractApplicationState() {

                        @Override
                        public void render(Surface surface) {
                            Graphics2D g = surface.getGraphics();
                            g.drawImage(image, 0, 0, image.getWidth() * scale, image.getHeight() * scale, null);
                        }
                    }).build().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
