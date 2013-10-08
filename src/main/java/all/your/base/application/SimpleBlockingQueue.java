package all.your.base.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
class SimpleBlockingQueue<T> {

    private final List<T> delegate = new ArrayList<>();

    public void add(T t) {
        synchronized (delegate) {
            delegate.add(t);
            delegate.notifyAll();
        }
    }

    public boolean drainTo(Collection<T> out, long timeout, TimeUnit unit) throws InterruptedException {
        synchronized (delegate) {
            if (delegate.isEmpty() && !awaitNonEmpty(timeout, unit)) {
                return false;
            }
            out.addAll(delegate);
            delegate.clear();
        }
        return true;
    }

    private boolean awaitNonEmpty(long timeout, TimeUnit unit) throws InterruptedException {
        long timeoutNanos = System.nanoTime() + unit.toNanos(timeout);
        while (delegate.isEmpty()) {
            long nanoTime = System.nanoTime();
            if (nanoTime >= timeoutNanos) {
                return false;
            }
            long remainingNanos = timeoutNanos - nanoTime;
            long remainingMillis = TimeUnit.NANOSECONDS.toMillis(remainingNanos);
            delegate.wait(remainingMillis, (int)(remainingNanos - TimeUnit.MILLISECONDS.toNanos(remainingMillis)));
        }
        return true;
    }
}
