package all.things.application;

import java.awt.Graphics2D;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
enum NullApplicationState implements ApplicationState {

    INSTANCE;

    @Override
    public void update(ApplicationManager appManager, long currentTimeNanos, long deltaTimeNanos) {

    }

    @Override
    public void render(Graphics2D g) {

    }
}
