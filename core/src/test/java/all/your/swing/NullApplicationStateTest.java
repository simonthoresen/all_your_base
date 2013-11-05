package all.your.swing;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class NullApplicationStateTest {

    @Test
    public void requireThatInstanceIsAnApplicationState() {
        assertTrue(NullApplicationState.INSTANCE instanceof ApplicationState);
    }

    @Test
    public void requireThatInstanceHasZeroInteractions() {
        Surface surface = Mockito.mock(Surface.class);
        NullApplicationState.INSTANCE.render(surface);
        Mockito.verifyZeroInteractions(surface);

        ApplicationManager manager = Mockito.mock(ApplicationManager.class);
        NullApplicationState.INSTANCE.update(manager);
        Mockito.verifyZeroInteractions(manager);
    }
}
