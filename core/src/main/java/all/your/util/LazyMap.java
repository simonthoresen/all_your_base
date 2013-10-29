package all.your.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public abstract class LazyMap<K, V> implements Map<K, V> {

    @SuppressWarnings("unchecked")
    private Map<K, V> delegate = Collections.EMPTY_MAP;

    @Override
    public final int size() {
        return delegate.size();
    }

    @Override
    public final boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public final boolean containsKey(Object key) {
        return delegate.containsKey(key);
    }

    @Override
    public final boolean containsValue(Object value) {
        return delegate.containsValue(value);
    }

    @Override
    public final V get(Object key) {
        return delegate.get(key);
    }

    @Override
    public final V put(K key, V value) {
        if (delegate == Collections.EMPTY_MAP) {
            delegate = newDelegate();
        }
        return delegate.put(key, value);
    }

    @Override
    public final V remove(Object key) {
        return delegate.remove(key);
    }

    @Override
    public final void putAll(Map<? extends K, ? extends V> m) {
        if (m.isEmpty()) {
            return;
        }
        if (delegate == Collections.EMPTY_MAP) {
            delegate = newDelegate();
        }
        delegate.putAll(m);
    }

    @Override
    public final void clear() {
        delegate.clear();
    }

    @Override
    public final Set<K> keySet() {
        return delegate.keySet();
    }

    @Override
    public final Collection<V> values() {
        return delegate.values();
    }

    @Override
    public final Set<Entry<K, V>> entrySet() {
        return delegate.entrySet();
    }

    @Override
    public final int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        return obj == this || (obj instanceof Map && delegate.equals(obj));
    }

    protected abstract Map<K, V> newDelegate();

    final Map<K, V> getDelegate() {
        return delegate;
    }
}
