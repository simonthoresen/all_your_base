package all.your.swing;

import org.junit.Test;
import org.mockito.Mockito;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class NullMouseListenerTest {

    @Test
    public void requireThatInstanceIsAMouseListener() {
        assertTrue(NullMouseListener.INSTANCE instanceof MouseListener);
    }

    @Test
    public void requireThatInstanceHasZeroInteractions() {
        MouseEvent e = Mockito.mock(MouseEvent.class);
        NullMouseListener.INSTANCE.mouseClicked(e);
        Mockito.verifyZeroInteractions(e);

        e = Mockito.mock(MouseEvent.class);
        NullMouseListener.INSTANCE.mousePressed(e);
        Mockito.verifyZeroInteractions(e);

        e = Mockito.mock(MouseEvent.class);
        NullMouseListener.INSTANCE.mouseReleased(e);
        Mockito.verifyZeroInteractions(e);

        e = Mockito.mock(MouseEvent.class);
        NullMouseListener.INSTANCE.mouseEntered(e);
        Mockito.verifyZeroInteractions(e);

        e = Mockito.mock(MouseEvent.class);
        NullMouseListener.INSTANCE.mouseExited(e);
        Mockito.verifyZeroInteractions(e);
    }
}
