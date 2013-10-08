package all.things.application;

import org.junit.Test;
import org.mockito.Mockito;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

        List<AWTEvent> events = new ArrayList<>();
        assertTrue(queue.drainTo(events, 100, TimeUnit.MILLISECONDS));
        assertEquals(Arrays.<AWTEvent>asList(keyTyped, keyPressed, keyReleased), events);
    }
}
