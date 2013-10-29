package all.your.core.application;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
enum NullApplicationState implements ApplicationState {

    INSTANCE;

    @Override
    public void update(ApplicationManager appManager) {

    }

    @Override
    public void render(Surface surface) {

    }
}
