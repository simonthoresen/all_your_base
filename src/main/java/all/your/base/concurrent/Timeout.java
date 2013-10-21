package all.your.base.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class Timeout {

    private final Timer timer;
    private final long timeoutAtMillis;

    public Timeout(Timer timer, long timeout, TimeUnit unit) {
        this.timer = timer;
        this.timeoutAtMillis = timer.currentTimeMillis() + unit.toMillis(timeout);
    }

    public long getTimeRemaining(TimeUnit unit) {
        return unit.convert(timeoutAtMillis - timer.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    public boolean isExpired() {
        return timeoutAtMillis <= timer.currentTimeMillis();
    }
}
