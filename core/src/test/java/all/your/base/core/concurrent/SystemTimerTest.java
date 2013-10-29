package all.your.base.core.concurrent;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class SystemTimerTest {

    @Test
    public void requireThatSystemTimerIsATimer() {
        assertTrue(Timer.class.isAssignableFrom(SystemTimer.class));
    }

    @Test
    public void requireThatSystemTimerReturnsSystemTime() {
        assertEquals(System.currentTimeMillis(), SystemTimer.INSTANCE.currentTimeMillis(), 100);
    }
}
