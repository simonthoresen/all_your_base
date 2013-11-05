package all.your.swing;

import org.junit.Test;
import org.mockito.Mockito;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class NullKeyListenerTest {

    @Test
    public void requireThatInstanceIsAKeyListener() {
        assertTrue(NullKeyListener.INSTANCE instanceof KeyListener);
    }

    @Test
    public void requireThatInstanceHasZeroInteractions() {
        KeyEvent e = Mockito.mock(KeyEvent.class);
        NullKeyListener.INSTANCE.keyTyped(e);
        Mockito.verifyZeroInteractions(e);

        e = Mockito.mock(KeyEvent.class);
        NullKeyListener.INSTANCE.keyPressed(e);
        Mockito.verifyZeroInteractions(e);

        e = Mockito.mock(KeyEvent.class);
        NullKeyListener.INSTANCE.keyReleased(e);
        Mockito.verifyZeroInteractions(e);
    }
}
