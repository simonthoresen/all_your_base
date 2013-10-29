package all.your.base.util;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertSame;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class LazyHashMapTest {

    @Test
    public void requireThatDelegateIsAHashMap() {
        LazyHashMap<String, Integer> map = new LazyHashMap<>();
        map.put("6", 9);
        assertSame(HashMap.class, map.getDelegate().getClass());
    }
}
