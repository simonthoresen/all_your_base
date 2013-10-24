package all.your.base.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class LazyHashMapTest {

    @Test
    public void requireThatMapCanBeInstantiated() {
        LazyHashMap<String, Integer> map = new LazyHashMap<>();
        assertFalse(map.hasDelegate());

        map.put("6", 9);
        assertTrue(map.hasDelegate());
        assertEquals(Integer.valueOf(9), map.get("6"));
    }
}
