package all.your.base.application;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public interface ApplicationListener {

    public void applicationStarted(Application app);

    public void applicationStopped(Application app);
}
