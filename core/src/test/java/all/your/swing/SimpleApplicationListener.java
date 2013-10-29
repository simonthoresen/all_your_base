package all.your.swing;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class SimpleApplicationListener implements ApplicationListener {

    private final CountDownLatch hasStarted = new CountDownLatch(1);
    private final CountDownLatch hasStopped = new CountDownLatch(1);

    @Override
    public void applicationStarted(Application app) {
        hasStarted.countDown();
    }

    @Override
    public void applicationStopped(Application app) {
        hasStopped.countDown();
    }

    public boolean awaitStart(long timeout, TimeUnit unit) throws InterruptedException {
        return hasStarted.await(timeout, unit);
    }

    public boolean awaitStop(long timeout, TimeUnit unit) throws InterruptedException {
        return hasStopped.await(timeout, unit);
    }
}
