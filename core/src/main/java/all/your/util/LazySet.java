package all.your.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public abstract class LazySet<E> implements Set<E> {

    private Set<E> delegate = Collections.emptySet();

    @Override
    public final int size() {
        return delegate.size();
    }

    @Override
    public final boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public final boolean contains(Object o) {
        return delegate.contains(o);
    }

    @Override
    public final Iterator<E> iterator() {
        return delegate.iterator();
    }

    @Override
    public final Object[] toArray() {
        return delegate.toArray();
    }

    @Override
    public final <T> T[] toArray(T[] a) {
        // noinspection SuspiciousToArrayCall
        return delegate.toArray(a);
    }

    @Override
    public final boolean add(E e) {
        if (delegate == Collections.EMPTY_SET) {
            delegate = newDelegate();
        }
        return delegate.add(e);
    }

    @Override
    public final boolean remove(Object o) {
        return delegate.remove(o);
    }

    @Override
    public final boolean containsAll(Collection<?> c) {
        return delegate.containsAll(c);
    }

    @Override
    public final boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }
        if (delegate == Collections.EMPTY_SET) {
            delegate = newDelegate();
        }
        return delegate.addAll(c);
    }

    @Override
    public final boolean retainAll(Collection<?> c) {
        return delegate.retainAll(c);
    }

    @Override
    public final boolean removeAll(Collection<?> c) {
        return delegate.removeAll(c);
    }

    @Override
    public final void clear() {
        delegate.clear();
    }

    @Override
    public final int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        return obj == this || (obj instanceof Set && delegate.equals(obj));
    }

    protected abstract Set<E> newDelegate();

    final Set<E> getDelegate() {
        return delegate;
    }
}
