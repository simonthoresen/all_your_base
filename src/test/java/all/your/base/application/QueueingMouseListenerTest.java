package all.your.base.application;

import org.junit.Test;
import org.mockito.Mockito;

import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

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

        assertEquals(Arrays.<AWTEvent>asList(mouseClicked, mousePressed, mouseReleased, mouseEntered, mouseExited),
                     queue.drain(Timeouts.EXPIRED));
    }
}
