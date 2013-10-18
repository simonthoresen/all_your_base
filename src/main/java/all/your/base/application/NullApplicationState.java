package all.your.base.application;

import all.your.base.graphics.Surface;

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
