package all.your.util.concurrent;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TimeoutTest {

    @Test
    public void requireThatTimeRemainingIsReturnedInRequestedUnit() {
        StaticTimer timer = new StaticTimer();
        Timeout timeout = new Timeout(timer, 69, TimeUnit.HOURS);
        for (TimeUnit unit : TimeUnit.values()) {
            assertEquals(unit.convert(69, TimeUnit.HOURS), timeout.getTimeRemaining(unit));
        }
    }

    @Test
    public void requireThatTimeRemainingDecreasesWhenTimeIncreases() {
        StaticTimer timer = new StaticTimer();
        Timeout timeout = new Timeout(timer, 69, TimeUnit.MILLISECONDS);
        for (long millis = 0; millis < 100; ++millis) {
            assertEquals(69 - millis, timeout.getTimeRemaining(TimeUnit.MILLISECONDS));
            ++timer.millis;
        }
    }

    @Test
    public void requireThatTimeoutExpiresWhenZeroTimeRemains() {
        StaticTimer timer = new StaticTimer();
        Timeout timeout = new Timeout(timer, 2, TimeUnit.MILLISECONDS);
        assertFalse(timeout.isExpired());
        ++timer.millis;
        assertFalse(timeout.isExpired());
        ++timer.millis;
        assertTrue(timeout.isExpired());
        ++timer.millis;
        assertTrue(timeout.isExpired());
    }

    private static class StaticTimer implements Timer {

        long millis;

        @Override
        public long currentTimeMillis() {
            return millis;
        }
    }
}
