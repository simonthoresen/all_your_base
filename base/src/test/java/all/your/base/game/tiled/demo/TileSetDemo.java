package all.your.base.game.tiled.demo;

import all.your.base.core.application.ApplicationManager;
import all.your.base.core.application.ApplicationState;
import all.your.base.core.application.Surface;
import all.your.base.game.tiled.TileSet;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TileSetDemo implements ApplicationState {

    private final TileSet tileSet;
    private int frame;

    public TileSetDemo() throws IOException {
        tileSet = TileSet.loadFromImageFile("/32x32.png", 32, 32);
    }

    @Override
    public void update(ApplicationManager appManager) throws InterruptedException {
        if (++frame > 1) {
            appManager.processEventQueue(1, TimeUnit.SECONDS);
        }
    }

    @Override
    public void render(Surface surface) {
        Graphics2D g = surface.getGraphics();
        g.clearRect(0, 0, 640, 480);
        for (int i = 0; i < (640 / 32) * (480 / 32); ++i) {
            tileSet.renderTile(frame % tileSet.numTiles() + i, g,
                               (i * 32) % 640,
                               32 * ((i * 32) / 640));
        }
    }

    public static void main(String[] args) throws Exception {
        Applications.fromState(new TileSetDemo()).run();
    }
}
