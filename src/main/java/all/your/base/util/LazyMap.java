package all.your.base.util;

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
    public int size() {
        return delegate == null ? 0 : delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate == null || delegate.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return delegate != null && delegate.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return delegate != null && delegate.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return delegate == null ? null : delegate.get(key);
    }

    @Override
    public V put(K key, V value) {
        if (delegate == null) {
            delegate = newDelegate();
        }
        return delegate.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return delegate == null ? null : delegate.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        if (m.isEmpty()) {
            return;
        }
        if (delegate == null) {
            delegate = newDelegate();
        }
        delegate.putAll(m);
    }

    @Override
    public void clear() {
        if (delegate == null) {
            return;
        }
        delegate.clear();
    }

    @Override
    public Set<K> keySet() {
        return delegate == null ? Collections.<K>emptySet() : delegate.keySet();
    }

    @Override
    public Collection<V> values() {
        return delegate == null ? Collections.<V>emptyList() : delegate.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return delegate == null ? Collections.<Entry<K, V>>emptySet() : delegate.entrySet();
    }

    @Override
    public int hashCode() {
        return delegate == null ? Collections.emptyMap().hashCode() : delegate.hashCode();
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (delegate == null) {
            return Collections.emptyMap().equals(obj);
        }
        return delegate.equals(obj);
    }

    protected abstract Map<K, V> newDelegate();

    boolean hasDelegate() {
        return delegate != null;
    }
}
