package all.your.base.tiled.demo;

import all.your.base.application.ApplicationManager;
import all.your.base.application.ApplicationState;
import all.your.base.application.Applications;
import all.your.base.graphics.Surface;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class SpriteDemo implements ApplicationState {

    @Override
    public void update(ApplicationManager appManager) {

    }

    @Override
    public void render(Surface surface) {

    }

    public static void main(String[] args) throws Exception {
        Applications.fromState(new SpriteDemo()).run();
    }
}
