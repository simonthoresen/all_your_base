package all.your.core.application;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public interface ApplicationState {

    public void update(ApplicationManager appManager) throws Exception;

    public void render(Surface surface);
}
