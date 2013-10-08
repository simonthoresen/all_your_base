package all.your.base.application;

import java.awt.Graphics2D;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class SimpleApplicationState implements ApplicationState {

    private volatile boolean shutdown = false;

    @Override
    public void update(ApplicationManager appManager, long currentTimeNanos, long deltaTimeNanos) {
        if (shutdown) {
            appManager.shutdown();
        }
    }

    @Override
    public void render(Graphics2D g) {

    }

    public void shutdown() {
        shutdown = true;
    }
}
