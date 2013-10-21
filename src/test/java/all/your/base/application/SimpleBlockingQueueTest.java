package all.your.base.application;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class SimpleBlockingQueueTest {

    @Test
    public void requireThatQueueIsInitiallyEmpty() throws InterruptedException {
        SimpleBlockingQueue<Object> queue = new SimpleBlockingQueue<>();
        assertTrue(queue.drain(Timeouts.EXPIRED).isEmpty());
    }

    @Test
    public void requireThatQueueCanBeAddedTo() throws InterruptedException {
        SimpleBlockingQueue<Object> queue = new SimpleBlockingQueue<>();
        Object foo = new Object();
        queue.add(foo);
        Object bar = new Object();
        queue.add(bar);

        Collection<Object> list = queue.drain(Timeouts.EXPIRED);
        assertEquals(Arrays.asList(foo, bar), list);
    }

    @Test
    public void requireThatDrainEmptiesQueue() throws InterruptedException {
        SimpleBlockingQueue<Object> queue = new SimpleBlockingQueue<>();
        queue.add(new Object());
        queue.drain(Timeouts.EXPIRED);
        assertTrue(queue.drain(Timeouts.EXPIRED).isEmpty());
    }
}
