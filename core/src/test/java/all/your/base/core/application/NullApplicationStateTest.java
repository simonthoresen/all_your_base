package all.your.base.core.application;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class NullApplicationStateTest {

    @Test
    public void requireThatInstanceIsAnApplicationState() {
        assertTrue(NullApplicationState.INSTANCE instanceof ApplicationState);
    }
}
