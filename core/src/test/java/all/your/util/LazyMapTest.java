package all.your.util;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class LazyMapTest {

    @Test
    public void requireThatMapDelegates() {
        @SuppressWarnings("unchecked") final Map<String, String> delegate = Mockito.mock(Map.class);
        Map<String, String> map = new LazyMap<String, String>() {

            @Override
            protected Map<String, String> newDelegate() {
                return delegate;
            }
        };
        map.put("foo", "bar");
        Mockito.verify(delegate).put("foo", "bar");

        Map<String, String> arg = Collections.singletonMap("baz", "cox");
        map.putAll(arg);
        Mockito.verify(delegate).putAll(arg);

        assertEquals(0, map.size());
        Mockito.verify(delegate).size();

        assertFalse(map.isEmpty());
        Mockito.verify(delegate).isEmpty();

        assertFalse(map.containsKey("foo"));
        Mockito.verify(delegate).containsKey("foo");

        assertFalse(map.containsValue("bar"));
        Mockito.verify(delegate).containsValue("bar");

        assertNull(map.get("foo"));
        Mockito.verify(delegate).get("foo");

        assertNull(map.remove("foo"));
        Mockito.verify(delegate).remove("foo");

        map.clear();
        Mockito.verify(delegate).clear();

        assertTrue(map.keySet().isEmpty());
        Mockito.verify(delegate).keySet();

        assertTrue(map.values().isEmpty());
        Mockito.verify(delegate).values();

        assertTrue(map.entrySet().isEmpty());
        Mockito.verify(delegate).entrySet();
    }

    @Test
    public void requireThatMapIsLazy() {
        LazyMap<String, String> map = new LazyHashMap<>();
        assertEquals(0, map.size());
        assertNull(map.getDelegate());

        assertTrue(map.isEmpty());
        assertNull(map.getDelegate());

        assertFalse(map.containsKey("foo"));
        assertNull(map.getDelegate());

        assertFalse(map.containsValue("bar"));
        assertNull(map.getDelegate());

        assertNull(map.get("foo"));
        assertNull(map.getDelegate());

        assertNull(map.remove("foo"));
        assertNull(map.getDelegate());

        map.clear();
        assertNull(map.getDelegate());

        assertTrue(map.keySet().isEmpty());
        assertNull(map.getDelegate());

        assertTrue(map.values().isEmpty());
        assertNull(map.getDelegate());

        assertTrue(map.entrySet().isEmpty());
        assertNull(map.getDelegate());
    }

    @Test
    public void requireThatHashCodeIsImplemented() {
        assertEquals(new LazyHashMap<>(), new LazyHashMap<>());
    }

    @Test
    public void requireThatEqualsIsImplemented() {
        Map<Object, Object> lhs = new LazyHashMap<>();
        Map<Object, Object> rhs = new LazyHashMap<>();
        assertEquals(lhs, lhs);
        assertEquals(lhs, rhs);

        Object key = new Object();
        Object val = new Object();
        lhs.put(key, val);
        assertEquals(lhs, lhs);
        assertFalse(lhs.equals(rhs));
        rhs.put(key, val);
        assertEquals(lhs, rhs);
    }
}
