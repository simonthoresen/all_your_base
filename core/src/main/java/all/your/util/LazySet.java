package all.your.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public abstract class LazySet<E> implements Set<E> {

    private Set<E> delegate;

    @Override
    public final int size() {
        return delegate == null ? 0 : delegate.size();
    }

    @Override
    public final boolean isEmpty() {
        return delegate == null || delegate.isEmpty();
    }

    @Override
    public final boolean contains(Object o) {
        return delegate != null && delegate.contains(o);
    }

    @Override
    public final Iterator<E> iterator() {
        return delegate == null ? Collections.<E>emptyIterator() : delegate.iterator();
    }

    @Override
    public final Object[] toArray() {
        return delegate == null ? new Object[0] : delegate.toArray();
    }

    @SuppressWarnings("SuspiciousToArrayCall")
    @Override
    public final <T> T[] toArray(T[] a) {
        return delegate == null ? a : delegate.toArray(a);
    }

    @Override
    public final boolean add(E e) {
        if (delegate == null) {
            delegate = newDelegate();
        }
        return delegate.add(e);
    }

    @Override
    public final boolean remove(Object o) {
        return delegate != null && delegate.remove(o);
    }

    @Override
    public final boolean containsAll(Collection<?> c) {
        return delegate != null && delegate.containsAll(c);
    }

    @Override
    public final boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }
        if (delegate == null) {
            delegate = newDelegate();
        }
        return delegate.addAll(c);
    }

    @Override
    public final boolean retainAll(Collection<?> c) {
        return delegate != null && delegate.retainAll(c);
    }

    @Override
    public final boolean removeAll(Collection<?> c) {
        return delegate != null && delegate.removeAll(c);
    }

    @Override
    public final void clear() {
        if (delegate == null) {
            return;
        }
        delegate.clear();
    }

    @Override
    public final int hashCode() {
        return delegate == null ? Collections.emptySet().hashCode() : delegate.hashCode();
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (delegate == null) {
            return Collections.emptySet().equals(obj);
        }
        return delegate.equals(obj);
    }

    protected abstract Set<E> newDelegate();

    final Set<E> getDelegate() {
        return delegate;
    }
}
