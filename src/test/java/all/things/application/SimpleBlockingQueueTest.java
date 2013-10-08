package all.things.application;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class SimpleBlockingQueueTest {

    @Test
    public void requireThatQueueIsInitiallyEmpty() throws InterruptedException {
        SimpleBlockingQueue<Object> queue = new SimpleBlockingQueue<>();
        List<Object> list = new ArrayList<>();
        assertFalse(queue.drainTo(list, 100, TimeUnit.MILLISECONDS));
        assertTrue(list.isEmpty());
    }

    @Test
    public void requireThatQueueCanBeAddedTo() throws InterruptedException {
        SimpleBlockingQueue<Object> queue = new SimpleBlockingQueue<>();
        Object foo = new Object();
        queue.add(foo);
        Object bar = new Object();
        queue.add(bar);

        List<Object> list = new ArrayList<>();
        assertTrue(queue.drainTo(list, 0, TimeUnit.MILLISECONDS));
        assertEquals(Arrays.asList(foo, bar), list);
    }

    @Test
    public void requireThatDrainToEmptiesQueue() throws InterruptedException {
        SimpleBlockingQueue<Object> queue = new SimpleBlockingQueue<>();
        queue.add(new Object());

        List<Object> list = new ArrayList<>();
        assertTrue(queue.drainTo(list, 0, TimeUnit.MILLISECONDS));
        assertFalse(queue.drainTo(list, 100, TimeUnit.MILLISECONDS));
    }
}
