package all.your.base.util;

import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertSame;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class LazyHashSetTest {

    @Test
    public void requireThatDelegateIsAHashSet() {
        LazyHashSet<Integer> set = new LazyHashSet<>();
        set.add(69);
        assertSame(HashSet.class, set.getDelegate().getClass());
    }
}
