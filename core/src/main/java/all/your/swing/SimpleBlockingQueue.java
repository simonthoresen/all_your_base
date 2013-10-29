package all.your.swing;

import all.your.util.concurrent.Timeout;

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

    public Collection<T> drain(Timeout timeout) throws InterruptedException {
        synchronized (lock) {
            while (delegate.isEmpty()) {
                long millis = timeout.getTimeRemaining(TimeUnit.MILLISECONDS);
                if (millis <= 0) {
                    return Collections.emptyList();
                }
                lock.wait(millis);
            }
            List<T> out = delegate;
            delegate = new ArrayList<>();
            return out;
        }
    }
}
