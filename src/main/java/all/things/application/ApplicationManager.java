package all.things.application;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public interface ApplicationManager {

    public void setState(ApplicationState state);

    public void shutdown();
}
