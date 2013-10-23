package all.your.base.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class FastHashMap<K, V> extends FastMap<K, V> {

    private final int initialCapacity;
    private final float loadFactor;

    public FastHashMap() {
        this(16);
    }

    public FastHashMap(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    public FastHashMap(int initialCapacity, float loadFactor) {
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
    }

    @Override
    protected Map<K, V> newDelegate() {
        return new HashMap<>(initialCapacity, loadFactor);
    }
}