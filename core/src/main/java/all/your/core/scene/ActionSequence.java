package all.your.core.scene;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class ActionSequence implements Action {

    private final Action[] actions;

    public ActionSequence(Action... actions) {
        this.actions = actions;
    }
}
