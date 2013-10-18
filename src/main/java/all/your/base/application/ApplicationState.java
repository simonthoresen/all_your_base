package all.your.base.application;

import all.your.base.graphics.Surface;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public interface ApplicationState {

    public void update(ApplicationManager appManager) throws Exception;

    public void render(Surface surface);
}
