package all.your.base.application;

import all.your.base.graphics.Surface;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class SimpleApplicationState implements ApplicationState {

    private volatile boolean shutdown = false;

    @Override
    public void update(ApplicationManager appManager) {
        if (shutdown) {
            appManager.shutdown();
        }
    }

    @Override
    public void render(Surface surface) {

    }

    public void shutdown() {
        shutdown = true;
    }
}
