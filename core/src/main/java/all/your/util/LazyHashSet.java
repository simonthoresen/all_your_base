package all.your.util;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public final class LazyHashSet<E> extends LazySet<E> {

    private final int initialCapacity;
    private final float loadFactor;

    public LazyHashSet() {
        this(16);
    }

    public LazyHashSet(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    public LazyHashSet(int initialCapacity, float loadFactor) {
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
    }

    @Override
    protected Set<E> newDelegate() {
        return new HashSet<>(initialCapacity, loadFactor);
    }
}
