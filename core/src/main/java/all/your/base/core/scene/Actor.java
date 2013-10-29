package all.your.base.core.scene;

import all.your.base.core.util.LazyHashMap;
import all.your.base.core.util.LazyHashSet;

import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class Actor {

    private final Map<String, Action> actions = new LazyHashMap<>();
    private final Set<Actor> children = new LazyHashSet<>();
}
