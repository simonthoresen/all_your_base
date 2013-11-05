package all.your.swing;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class NullApplicationListenerTest {

    @Test
    public void requireThatInstanceIsAnApplicationListener() {
        assertTrue(NullApplicationListener.INSTANCE instanceof ApplicationListener);
    }

    @Test
    public void requireThatInstanceHasZeroInteractions() {
        Application app = Mockito.mock(Application.class);
        NullApplicationListener.INSTANCE.applicationStarted(app);
        Mockito.verifyZeroInteractions(app);

        app = Mockito.mock(Application.class);
        NullApplicationListener.INSTANCE.applicationStopped(app);
        Mockito.verifyZeroInteractions(app);
    }
}
