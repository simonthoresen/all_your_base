package all.your.base.core.application;

import org.junit.Test;
import org.mockito.Mockito;

import java.awt.AWTEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class QueuingComponentListenerTest {

    @Test
    public void requireThatEventsAreQueued() throws InterruptedException {
        SimpleBlockingQueue<AWTEvent> queue = new SimpleBlockingQueue<>();
        ComponentListener listener = new QueueingComponentListener(queue);
        ComponentEvent componentHidden = Mockito.mock(ComponentEvent.class);
        listener.componentHidden(componentHidden);
        ComponentEvent componentMoved = Mockito.mock(ComponentEvent.class);
        listener.componentMoved(componentMoved);
        ComponentEvent componentResized = Mockito.mock(ComponentEvent.class);
        listener.componentResized(componentResized);
        ComponentEvent componentShown = Mockito.mock(ComponentEvent.class);
        listener.componentShown(componentShown);

        assertEquals(Arrays.<AWTEvent>asList(componentHidden, componentMoved, componentResized, componentShown),
                     queue.drain(Timeouts.EXPIRED));
    }
}
