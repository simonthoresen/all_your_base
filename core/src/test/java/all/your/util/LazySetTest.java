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

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class LazySetTest {

    @Test
    public void requireThatInitialDelegateIsEmpty() {
        LazySet<String> set = newLazySet(new HashSet<String>());
        assertEquals(LazySet.EmptySet.class, set.getDelegate().getClass());
    }

    @Test
    public void requireThatSingleEntryDelegateIsSingleton() {
        LazySet<String> set = newLazySet(new HashSet<String>());
        set.add("foo");
        assertEquals(LazySet.SingletonSet.class, set.getDelegate().getClass());

        set = LazySet.newHashSet();
        set.addAll(Collections.singleton("foo"));
        assertEquals(LazySet.SingletonSet.class, set.getDelegate().getClass());
    }

    @Test
    public void requireThatRemovingEntryFromSingletonRevertsToEmpty() {
        LazySet<String> set = newLazySet(new HashSet<String>());
        set.add("foo");
        assertEquals(LazySet.SingletonSet.class, set.getDelegate().getClass());
        set.remove("foo");
        assertEquals(LazySet.EmptySet.class, set.getDelegate().getClass());
    }

    @Test
    public void requireThatNewDelegateIsInvokedWhenNumEntriesExceedOne() {
        Set<String> delegate = new HashSet<>();
        LazySet<String> set = newLazySet(delegate);
        set.add("foo");
        set.add("bar");
        assertSame(delegate, set.getDelegate());

        set = newLazySet(delegate);
        set.addAll(Arrays.asList("foo", "bar"));
        assertSame(delegate, set.getDelegate());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void requireThatSetDelegates() {
        Set<String> delegate = Mockito.mock(Set.class);
        Set<String> set = newLazySet(delegate);
        set.add("foo");
        set.add("bar"); // trigger the assignment of the delegate
        Mockito.verify(delegate).add("foo");
        Mockito.verify(delegate).add("bar");

        Set<String> addAllArg = Collections.singleton("foo");
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
    public void requireThatHashCodeIsImplemented() {
        assertEquals(newLazySet(null).hashCode(),
                     newLazySet(null).hashCode());
    }

    @Test
    public void requireThatEqualsIsImplemented() {
        Set<Object> lhs = newLazySet(new HashSet<>());
        Set<Object> rhs = newLazySet(new HashSet<>());
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
        set.add(6);
        set.add(9);
        assertEquals(HashSet.class, set.getDelegate().getClass());
    }

    private static <E> LazySet<E> newLazySet(final Set<E> delegate) {
        return new LazySet<E>() {

            @Override
            protected Set<E> newDelegate() {
                return delegate;
            }
        };
    }
}
