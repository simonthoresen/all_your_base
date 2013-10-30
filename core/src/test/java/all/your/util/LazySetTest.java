package all.your.util;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
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

    @SuppressWarnings("unchecked")
    @Test
    public void requireThatSetDelegates() {
        Set<String> delegate = Mockito.mock(Set.class);
        Set<String> set = new SimpleLazySet<>(delegate);
        set.add("foo"); // trigger the assignment of the delegate
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
        CountingLazySet<String> set = new CountingLazySet<>();

        assertEquals(0, set.size());
        assertEquals(0, set.newDelegateCallCnt);

        assertTrue(set.isEmpty());
        assertEquals(0, set.newDelegateCallCnt);

        assertFalse(set.contains("foo"));
        assertEquals(0, set.newDelegateCallCnt);

        assertFalse(set.iterator().hasNext());
        assertEquals(0, set.newDelegateCallCnt);

        assertEquals(0, set.toArray().length);
        assertEquals(0, set.newDelegateCallCnt);

        String[] arg = new String[69];
        assertSame(arg, set.toArray(arg));
        assertEquals(0, set.newDelegateCallCnt);

        assertFalse(set.remove("foo"));
        assertEquals(0, set.newDelegateCallCnt);

        assertFalse(set.containsAll(Collections.singletonList("foo")));
        assertEquals(0, set.newDelegateCallCnt);

        assertFalse(set.retainAll(Collections.singletonList("foo")));
        assertEquals(0, set.newDelegateCallCnt);

        assertFalse(set.removeAll(Collections.singletonList("foo")));
        assertEquals(0, set.newDelegateCallCnt);

        set.clear();
        assertEquals(0, set.newDelegateCallCnt);

        assertTrue(set.add("foo"));
        assertEquals(1, set.newDelegateCallCnt);

        assertTrue(set.addAll(Arrays.asList("bar", "baz")));
        assertEquals(1, set.newDelegateCallCnt);
    }

    @Test
    public void requireThatHashCodeIsImplemented() {
        assertEquals(new SimpleLazySet<>(null).hashCode(),
                     new SimpleLazySet<>(null).hashCode());
    }

    @Test
    public void requireThatEqualsIsImplemented() {
        Set<Object> lhs = new SimpleLazySet<>(new HashSet<>());
        Set<Object> rhs = new SimpleLazySet<>(new HashSet<>());
        assertEquals(lhs, lhs);
        assertEquals(lhs, rhs);

        Object obj = new Object();
        lhs.add(obj);
        assertEquals(lhs, lhs);
        assertFalse(lhs.equals(rhs));
        rhs.add(obj);
        assertEquals(lhs, rhs);
    }

    @Test
    public void requireThatHashSetFactoryDelegatesToAHashSet() {
        LazySet<Integer> set = LazySet.newHashSet();
        set.add(69);
        assertSame(HashSet.class, set.getDelegate().getClass());
    }

    private static class SimpleLazySet<E> extends LazySet<E> {

        final Set<E> delegate;

        SimpleLazySet(Set<E> delegate) {
            this.delegate = delegate;
        }

        @Override
        protected Set<E> newDelegate() {
            return delegate;
        }
    }

    private static class CountingLazySet<E> extends LazySet<E> {

        int newDelegateCallCnt = 0;

        @Override
        protected Set<E> newDelegate() {
            ++newDelegateCallCnt;
            return new HashSet<>();
        }
    }
}
