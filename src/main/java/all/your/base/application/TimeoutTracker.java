package all.your.base.application;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
class TimeoutTracker {

    private final Timer timer;
    private final long timeoutAtMillis;

    public TimeoutTracker(Timer timer, long timeout, TimeUnit unit) {
        this.timer = timer;
        this.timeoutAtMillis = timer.currentTimeMillis() + unit.toMillis(timeout);
    }

    public long getTimeRemaining(TimeUnit unit) {
        long now = timer.currentTimeMillis();
        if (now > timeoutAtMillis) {
            return 0;
        }
        return unit.convert(timeoutAtMillis - now, TimeUnit.MILLISECONDS);
    }
}
