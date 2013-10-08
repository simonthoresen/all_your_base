package all.your.base.application;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class Applications {

    public static Application fromImage(final BufferedImage image) {
        return fromImage(image, 1);
    }

    public static Application fromImage(final BufferedImage image, final int scale) {
        return new ApplicationBuilder()
                .setWindowWidth(image.getWidth() * scale)
                .setWindowHeight(image.getHeight() * scale)
                .setInitialState(new ApplicationState() {

                    @Override
                    public void update(ApplicationManager appManager, long currentTimeNanos, long deltaTimeNanos) {

                    }

                    @Override
                    public void render(Graphics2D g) {
                        g.drawImage(image, 0, 0, image.getWidth() * scale, image.getHeight() * scale, null);
                    }
                }).build();
    }

    public static Application fromState(ApplicationState initialState) {
        return new ApplicationBuilder()
                .setInitialState(initialState)
                .build();
    }
}
