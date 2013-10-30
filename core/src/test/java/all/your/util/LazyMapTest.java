package all.your.util;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class LazyMapTest {

    @Test
    public void requireThatInitialDelegateIsEmpty() {
        LazyMap<String, String> map = newLazyMap(new HashMap<String, String>());
        assertEquals(LazyMap.EmptyMap.class, map.getDelegate().getClass());
    }

    @Test
    public void requireThatSingleEntryDelegateIsSingleton() {
        LazyMap<String, String> map = newLazyMap(new HashMap<String, String>());
        map.put("foo", "bar");
        assertEquals(LazyMap.SingletonMap.class, map.getDelegate().getClass());

        map = LazyMap.newHashMap();
        map.putAll(Collections.singletonMap("foo", "bar"));
        assertEquals(LazyMap.SingletonMap.class, map.getDelegate().getClass());
    }

    @Test
    public void requireThatRemovingEntryFromSingletonRevertsToEmpty() {
        LazyMap<String, String> map = newLazyMap(new HashMap<String, String>());
        map.put("foo", "bar");
        assertEquals(LazyMap.SingletonMap.class, map.getDelegate().getClass());
        map.remove("foo");
        assertEquals(LazyMap.EmptyMap.class, map.getDelegate().getClass());
    }

    @Test
    public void requireThatNewDelegateIsInvokedWhenNumEntriesExceedOne() {
        Map<String, String> delegate = new HashMap<>();
        LazyMap<String, String> map = newLazyMap(delegate);
        map.put("foo", "bar");
        map.put("baz", "cox");
        assertSame(delegate, map.getDelegate());

        map = newLazyMap(delegate);
        map.putAll(new HashMap<String, String>() {{
            put("foo", "bar");
            put("baz", "cox");
        }});
        assertSame(delegate, map.getDelegate());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void requireThatMapDelegates() {
        Map<String, String> delegate = Mockito.mock(Map.class);
        Map<String, String> map = newLazyMap(delegate);
        map.put("foo", "bar");
        map.put("baz", "cox"); // trigger the assignment of the delegate
        Mockito.verify(delegate).put("foo", "bar");
        Mockito.verify(delegate).put("baz", "cox");

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
    public void requireThatHashCodeIsImplemented() {
        assertEquals(newLazyMap(null).hashCode(),
                     newLazyMap(null).hashCode());
    }

    @Test
    public void requireThatEqualsIsImplemented() {
        Map<Object, Object> lhs = newLazyMap(new HashMap<>());
        Map<Object, Object> rhs = newLazyMap(new HashMap<>());
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

    @Test
    public void requireThatHashMapFactoryDelegatesToAHashMap() {
        LazyMap<String, String> map = LazyMap.newHashMap();
        map.put("foo", "bar");
        map.put("baz", "cox");
        assertEquals(HashMap.class, map.getDelegate().getClass());
    }

    private static <K, V> LazyMap<K, V> newLazyMap(final Map<K, V> delegate) {
        return new LazyMap<K, V>() {

            @Override
            protected Map<K, V> newDelegate() {
                return delegate;
            }
        };
    }
}
