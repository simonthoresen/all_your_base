package all.your.util;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class LazyMapTest {

    @SuppressWarnings("unchecked")
    @Test
    public void requireThatMapDelegates() {
        Map<String, String> delegate = Mockito.mock(Map.class);
        Map<String, String> map = new SimpleLazyMap<>(delegate);
        map.put("foo", "bar"); // trigger the assignment of the delegate
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
        CountingLazyMap<String, String> map = new CountingLazyMap<>();

        assertEquals(0, map.size());
        assertEquals(0, map.newDelegateCallCnt);

        assertTrue(map.isEmpty());
        assertEquals(0, map.newDelegateCallCnt);

        assertFalse(map.containsKey("foo"));
        assertEquals(0, map.newDelegateCallCnt);

        assertFalse(map.containsValue("bar"));
        assertEquals(0, map.newDelegateCallCnt);

        assertNull(map.get("foo"));
        assertEquals(0, map.newDelegateCallCnt);

        assertNull(map.remove("foo"));
        assertEquals(0, map.newDelegateCallCnt);

        map.clear();
        assertEquals(0, map.newDelegateCallCnt);

        assertTrue(map.keySet().isEmpty());
        assertEquals(0, map.newDelegateCallCnt);

        assertTrue(map.values().isEmpty());
        assertEquals(0, map.newDelegateCallCnt);

        assertTrue(map.entrySet().isEmpty());
        assertEquals(0, map.newDelegateCallCnt);

        assertNull(map.put("foo", "bar"));
        assertEquals(1, map.newDelegateCallCnt);

        map.putAll(Collections.singletonMap("baz", "cox"));
        assertEquals(1, map.newDelegateCallCnt);
    }

    @Test
    public void requireThatHashCodeIsImplemented() {
        assertEquals(new SimpleLazyMap<>(null).hashCode(),
                     new SimpleLazyMap<>(null).hashCode());
    }

    @Test
    public void requireThatEqualsIsImplemented() {
        Map<Object, Object> lhs = new SimpleLazyMap<>(new HashMap<>());
        Map<Object, Object> rhs = new SimpleLazyMap<>(new HashMap<>());
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

    private static class SimpleLazyMap<K, V> extends LazyMap<K, V> {

        final Map<K, V> delegate;

        SimpleLazyMap(Map<K, V> delegate) {
            this.delegate = delegate;
        }

        @Override
        protected Map<K, V> newDelegate() {
            return delegate;
        }
    }

    private static class CountingLazyMap<K, V> extends LazyMap<K, V> {

        int newDelegateCallCnt = 0;

        @Override
        protected Map<K, V> newDelegate() {
            ++newDelegateCallCnt;
            return new HashMap<>();
        }
    }
}
