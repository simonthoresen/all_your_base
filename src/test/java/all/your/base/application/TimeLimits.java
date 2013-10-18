package all.your.base.application;

import org.mockito.Mockito;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TimeLimits {

    public final static TimeLimit EXPIRED = new TimeLimit(Mockito.mock(Timer.class), 0, TimeUnit.SECONDS);
}
