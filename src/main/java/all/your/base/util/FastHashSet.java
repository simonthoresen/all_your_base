package all.your.base.util;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class FastHashSet<E> extends FastSet<E> {

    private final int initialCapacity;
    private final float loadFactor;

    public FastHashSet() {
        this(16);
    }

    public FastHashSet(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    public FastHashSet(int initialCapacity, float loadFactor) {
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
    }

    @Override
    protected Set<E> newDelegate() {
        return new HashSet<>(initialCapacity, loadFactor);
    }
}
