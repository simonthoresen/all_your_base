package all.your.demo.daylight;

import all.your.swing.ApplicationBuilder;
import all.your.swing.ApplicationManager;
import all.your.swing.ApplicationState;
import all.your.swing.Surface;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class DaylightDemo implements ApplicationState {

    public static void main(String[] args) throws Exception {
        new ApplicationBuilder().setInitialState(new DaylightDemo()).build().run();
    }

    @Override
    public void update(ApplicationManager appManager) throws Exception {

    }

    @Override
    public void render(Surface surface) {

    }
}
