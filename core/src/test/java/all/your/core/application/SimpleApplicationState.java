package all.your.core.application;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class SimpleApplicationState implements ApplicationState {

    private volatile boolean shutdown = false;

    @Override
    public void update(ApplicationManager appManager) throws InterruptedException {
        appManager.processEventQueue(100, TimeUnit.MILLISECONDS);
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
