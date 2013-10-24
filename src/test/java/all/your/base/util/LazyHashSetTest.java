package all.your.base.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class LazyHashSetTest {

    @Test
    public void requireThatSetIsLazy() {
        LazyHashSet<Integer> set = new LazyHashSet<>();
        assertFalse(set.hasDelegate());

        set.add(69);
        assertTrue(set.hasDelegate());
        assertTrue(set.contains(69));
    }
}
