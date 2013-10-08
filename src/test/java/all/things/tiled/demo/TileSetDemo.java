package all.things.tiled.demo;

import all.things.application.ApplicationManager;
import all.things.application.ApplicationState;
import all.things.application.Applications;
import all.things.tiled.TileSet;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TileSetDemo implements ApplicationState {

    private final TileSet tileSet;
    private long currentTimeNanos;

    public TileSetDemo() throws IOException {
        tileSet = TileSet.loadFromImageFile("/32x32.png", 32, 32);
    }

    @Override
    public void update(ApplicationManager appManager, long currentTimeNanos, long deltaTimeNanos) {
        this.currentTimeNanos = currentTimeNanos;
    }

    @Override
    public void render(Graphics2D g) {
        g.clearRect(0, 0, 640, 480);
        for (int i = 0; i < (640 / 32) * (480 / 32); ++i) {
            tileSet.renderTile((int)(TimeUnit.NANOSECONDS.toSeconds(currentTimeNanos) % tileSet.numTiles() + i), g,
                               (i * 32) % 640,
                               32 * ((i * 32) / 640));
        }
    }

    public static void main(String[] args) throws Exception {
        Applications.fromState(new TileSetDemo()).run();
    }
}
