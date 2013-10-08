package all.things.application;

import java.awt.Graphics2D;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public interface ApplicationState {

    public void update(ApplicationManager appManager, long currentTimeNanos, long deltaTimeNanos);

    public void render(Graphics2D g);
}
