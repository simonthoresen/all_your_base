package all.your.swing;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public interface ApplicationManager {

    public boolean processEventQueue(long duration, TimeUnit unit) throws InterruptedException;

    public void setState(ApplicationState state);

    public void shutdown();
}
