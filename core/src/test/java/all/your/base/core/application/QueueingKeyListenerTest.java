package all.your.base.core.application;

import org.junit.Test;
import org.mockito.Mockito;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class QueueingKeyListenerTest {

    @Test
    public void requireThatEventsAreQueued() throws InterruptedException {
        SimpleBlockingQueue<AWTEvent> queue = new SimpleBlockingQueue<>();
        KeyListener listener = new QueueingKeyListener(queue);
        KeyEvent keyTyped = Mockito.mock(KeyEvent.class);
        listener.keyTyped(keyTyped);
        KeyEvent keyPressed = Mockito.mock(KeyEvent.class);
        listener.keyPressed(keyPressed);
        KeyEvent keyReleased = Mockito.mock(KeyEvent.class);
        listener.keyReleased(keyReleased);

        assertEquals(Arrays.<AWTEvent>asList(keyTyped, keyPressed, keyReleased),
                     queue.drain(Timeouts.EXPIRED));
    }
}
