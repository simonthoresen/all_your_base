package all.your.base.application;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class SimpleBlockingQueueTest {

    @Test
    public void requireThatQueueIsInitiallyEmpty() throws InterruptedException {
        SimpleBlockingQueue<Object> queue = new SimpleBlockingQueue<>();
        assertTrue(queue.drain(100, TimeUnit.MILLISECONDS).isEmpty());
    }

    @Test
    public void requireThatQueueCanBeAddedTo() throws InterruptedException {
        SimpleBlockingQueue<Object> queue = new SimpleBlockingQueue<>();
        Object foo = new Object();
        queue.add(foo);
        Object bar = new Object();
        queue.add(bar);

        Collection<Object> list = queue.drain(0, TimeUnit.MILLISECONDS);
        assertEquals(Arrays.asList(foo, bar), list);
    }

    @Test
    public void requireThatDrainEmptiesQueue() throws InterruptedException {
        SimpleBlockingQueue<Object> queue = new SimpleBlockingQueue<>();
        queue.add(new Object());
        queue.drain(0, TimeUnit.MILLISECONDS);
        assertTrue(queue.drain(100, TimeUnit.MILLISECONDS).isEmpty());
    }
}
