package all.your.base.application;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class NullApplicationListenerTest {

    @Test
    public void requireThatInstanceIsAnApplicationListener() {
        assertTrue(NullApplicationListener.INSTANCE instanceof ApplicationListener);
    }
}
