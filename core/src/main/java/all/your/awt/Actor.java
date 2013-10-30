package all.your.awt;

import all.your.util.LazyMap;
import all.your.util.LazySet;

import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class Actor {

    private final Map<String, Action> actions = LazyMap.newHashMap();
    private final Set<Actor> children = LazySet.newHashSet();
}
