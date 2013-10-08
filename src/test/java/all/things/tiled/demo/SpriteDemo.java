package all.things.tiled.demo;

import all.things.application.ApplicationManager;
import all.things.application.ApplicationState;
import all.things.application.Applications;

import java.awt.Graphics2D;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class SpriteDemo implements ApplicationState {

    @Override
    public void update(ApplicationManager appManager, long currentTimeNanos, long deltaTimeNanos) {

    }

    @Override
    public void render(Graphics2D g) {

    }

    public static void main(String[] args) throws Exception {
        Applications.fromState(new SpriteDemo()).run();
    }
}
