package all.your.base.core.application;

import all.your.base.core.concurrent.Timeout;
import all.your.base.core.concurrent.Timer;
import org.mockito.Mockito;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class Timeouts {

    public final static Timeout EXPIRED = new Timeout(Mockito.mock(Timer.class), 0, TimeUnit.SECONDS);
}
