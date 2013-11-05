package all.your.swing;

import org.junit.Test;
import org.mockito.Mockito;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class NullComponentListenerTest {

    @Test
    public void requireThatInstanceIsAComponentListener() {
        assertTrue(NullComponentListener.INSTANCE instanceof ComponentListener);
    }

    @Test
    public void requireThatInstanceHasZeroInteractions() {
        ComponentEvent e = Mockito.mock(ComponentEvent.class);
        NullComponentListener.INSTANCE.componentResized(e);
        Mockito.verifyZeroInteractions(e);

        e = Mockito.mock(ComponentEvent.class);
        NullComponentListener.INSTANCE.componentMoved(e);
        Mockito.verifyZeroInteractions(e);

        e = Mockito.mock(ComponentEvent.class);
        NullComponentListener.INSTANCE.componentShown(e);
        Mockito.verifyZeroInteractions(e);

        e = Mockito.mock(ComponentEvent.class);
        NullComponentListener.INSTANCE.componentHidden(e);
        Mockito.verifyZeroInteractions(e);
    }
}