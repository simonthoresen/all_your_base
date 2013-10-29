package all.your.base.util;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class LazySetTest {

    @Test
    public void requireThatSetDelegates() {
        @SuppressWarnings("unchecked") final Set<String> delegate = Mockito.mock(Set.class);
        Set<String> set = new LazySet<String>() {

            @Override
            protected Set<String> newDelegate() {
                return delegate;
            }
        };
        set.add("foo");
        Mockito.verify(delegate).add("foo");

        Set<String> addAllArg = Collections.singleton("bar");
        set.addAll(addAllArg);
        Mockito.verify(delegate).addAll(addAllArg);

        assertEquals(0, set.size());
        Mockito.verify(delegate).size();

        assertFalse(set.isEmpty());
        Mockito.verify(delegate).isEmpty();

        assertFalse(set.contains("foo"));
        Mockito.verify(delegate).contains("foo");

        assertNull(set.iterator());
        Mockito.verify(delegate).iterator();

        assertNull(set.toArray());
        Mockito.verify(delegate).toArray();

        String[] toArrayArg = new String[69];
        assertNull(set.toArray(toArrayArg));
        Mockito.verify(delegate).toArray(toArrayArg);

        assertFalse(set.remove("foo"));
        Mockito.verify(delegate).remove("foo");

        Collection<String> containsAllArg = Collections.singletonList("foo");
        assertFalse(set.containsAll(containsAllArg));
        Mockito.verify(delegate).containsAll(containsAllArg);

        Collection<String> retainAllArg = Collections.singletonList("foo");
        assertFalse(set.retainAll(retainAllArg));
        Mockito.verify(delegate).retainAll(retainAllArg);

        Collection<String> removeAllArg = Collections.singletonList("foo");
        assertFalse(set.removeAll(removeAllArg));
        Mockito.verify(delegate).removeAll(removeAllArg);

        set.clear();
        Mockito.verify(delegate).clear();
    }

    @Test
    public void requireThatSetIsLazy() {
        LazySet<String> set = new LazyHashSet<>();
        assertEquals(0, set.size());
        assertNull(set.getDelegate());

        assertTrue(set.isEmpty());
        assertNull(set.getDelegate());

        assertFalse(set.contains("foo"));
        assertNull(set.getDelegate());

        assertFalse(set.iterator().hasNext());
        assertNull(set.getDelegate());

        assertEquals(0, set.toArray().length);
        assertNull(set.getDelegate());

        String[] arg = new String[69];
        assertSame(arg, set.toArray(arg));
        assertNull(set.getDelegate());

        assertFalse(set.remove("foo"));
        assertNull(set.getDelegate());

        assertFalse(set.containsAll(Collections.singletonList("foo")));
        assertNull(set.getDelegate());

        assertFalse(set.retainAll(Collections.singletonList("foo")));
        assertNull(set.getDelegate());

        assertFalse(set.removeAll(Collections.singletonList("foo")));
        assertNull(set.getDelegate());

        set.clear();
        assertNull(set.getDelegate());
    }

    @Test
    public void requireThatHashCodeIsImplemented() {
        assertEquals(new LazyHashSet<>(), new LazyHashSet<>());
    }

    @Test
    public void requireThatEqualsIsImplemented() {
        Set<Object> lhs = new LazyHashSet<>();
        Set<Object> rhs = new LazyHashSet<>();
        assertEquals(lhs, lhs);
        assertEquals(lhs, rhs);

        Object obj = new Object();
        lhs.add(obj);
        assertEquals(lhs, lhs);
        assertFalse(lhs.equals(rhs));
        rhs.add(obj);
        assertEquals(lhs, rhs);
    }
}
