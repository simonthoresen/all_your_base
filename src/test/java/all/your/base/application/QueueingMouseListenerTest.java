package all.your.base.application;

import org.junit.Test;
import org.mockito.Mockito;

import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class QueueingMouseListenerTest {

    @Test
    public void requireThatEventsAreQueued() throws InterruptedException {
        SimpleBlockingQueue<AWTEvent> queue = new SimpleBlockingQueue<>();
        MouseListener listener = new QueueingMouseListener(queue);
        MouseEvent mouseClicked = Mockito.mock(MouseEvent.class);
        listener.mouseClicked(mouseClicked);
        MouseEvent mousePressed = Mockito.mock(MouseEvent.class);
        listener.mousePressed(mousePressed);
        MouseEvent mouseReleased = Mockito.mock(MouseEvent.class);
        listener.mouseReleased(mouseReleased);
        MouseEvent mouseEntered = Mockito.mock(MouseEvent.class);
        listener.mouseEntered(mouseEntered);
        MouseEvent mouseExited = Mockito.mock(MouseEvent.class);
        listener.mouseExited(mouseExited);

        List<AWTEvent> events = new ArrayList<>();
        assertTrue(queue.drainTo(events, 100, TimeUnit.MILLISECONDS));
        assertEquals(Arrays.<AWTEvent>asList(mouseClicked, mousePressed, mouseReleased, mouseEntered, mouseExited),
                     events);
    }
}
