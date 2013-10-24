package all.your.base.scene;

import all.your.base.util.LazyHashMap;
import all.your.base.util.LazyHashSet;

import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class Actor {

    private final Map<String, Action> actions = new LazyHashMap<>();
    private final Set<Actor> children = new LazyHashSet<>();
}