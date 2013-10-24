package all.your.base.util;

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
    public int size() {
        return delegate == null ? 0 : delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate == null || delegate.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return delegate != null && delegate.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return delegate == null ? Collections.<E>emptyIterator() : delegate.iterator();
    }

    @Override
    public Object[] toArray() {
        return delegate == null ? new Object[0] : delegate.toArray();
    }

    @SuppressWarnings("SuspiciousToArrayCall")
    @Override
    public <T> T[] toArray(T[] a) {
        return delegate == null ? a : delegate.toArray(a);
    }

    @Override
    public boolean add(E e) {
        if (delegate == null) {
            delegate = newDelegate();
        }
        return delegate.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return delegate != null && delegate.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return delegate != null && delegate.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }
        if (delegate == null) {
            delegate = newDelegate();
        }
        return delegate.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return delegate != null && delegate.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return delegate != null && delegate.removeAll(c);
    }

    @Override
    public void clear() {
        if (delegate == null) {
            return;
        }
        delegate.clear();
    }

    protected abstract Set<E> newDelegate();

    boolean hasDelegate() {
        return delegate != null;
    }
}
