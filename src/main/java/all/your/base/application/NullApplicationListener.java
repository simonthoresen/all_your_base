package all.your.base.application;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
enum NullApplicationListener implements ApplicationListener {

    INSTANCE;

    @Override
    public void applicationStarted(Application app) {

    }

    @Override
    public void applicationStopped(Application app) {

    }
}
