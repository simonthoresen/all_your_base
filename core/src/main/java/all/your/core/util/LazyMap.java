package all.your.core.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public abstract class LazyMap<K, V> implements Map<K, V> {

    private Map<K, V> delegate;

    @Override
    public final int size() {
        return delegate == null ? 0 : delegate.size();
    }

    @Override
    public final boolean isEmpty() {
        return delegate == null || delegate.isEmpty();
    }

    @Override
    public final boolean containsKey(Object key) {
        return delegate != null && delegate.containsKey(key);
    }

    @Override
    public final boolean containsValue(Object value) {
        return delegate != null && delegate.containsValue(value);
    }

    @Override
    public final V get(Object key) {
        return delegate == null ? null : delegate.get(key);
    }

    @Override
    public final V put(K key, V value) {
        if (delegate == null) {
            delegate = newDelegate();
        }
        return delegate.put(key, value);
    }

    @Override
    public final V remove(Object key) {
        return delegate == null ? null : delegate.remove(key);
    }

    @Override
    public final void putAll(Map<? extends K, ? extends V> m) {
        if (m.isEmpty()) {
            return;
        }
        if (delegate == null) {
            delegate = newDelegate();
        }
        delegate.putAll(m);
    }

    @Override
    public final void clear() {
        if (delegate == null) {
            return;
        }
        delegate.clear();
    }

    @Override
    public final Set<K> keySet() {
        return delegate == null ? Collections.<K>emptySet() : delegate.keySet();
    }

    @Override
    public final Collection<V> values() {
        return delegate == null ? Collections.<V>emptyList() : delegate.values();
    }

    @Override
    public final Set<Entry<K, V>> entrySet() {
        return delegate == null ? Collections.<Entry<K, V>>emptySet() : delegate.entrySet();
    }

    @Override
    public final int hashCode() {
        return delegate == null ? Collections.emptyMap().hashCode() : delegate.hashCode();
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (delegate == null) {
            return Collections.emptyMap().equals(obj);
        }
        return delegate.equals(obj);
    }

    protected abstract Map<K, V> newDelegate();

    final Map<K, V> getDelegate() {
        return delegate;
    }
}
