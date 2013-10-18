package all.your.base.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
class SimpleBlockingQueue<T> {

    private final Object lock = new Object();
    private List<T> delegate = new ArrayList<>();

    public void add(T t) {
        synchronized (lock) {
            delegate.add(t);
            lock.notifyAll();
        }
    }

    public Collection<T> drain(TimeLimit limit) throws InterruptedException {
        synchronized (lock) {
            while (delegate.isEmpty()) {
                long timeout = limit.getTimeRemaining(TimeUnit.MILLISECONDS);
                if (timeout <= 0) {
                    return Collections.emptyList();
                }
                lock.wait(timeout);
            }
            List<T> out = delegate;
            delegate = new ArrayList<>();
            return out;
        }
    }
}
