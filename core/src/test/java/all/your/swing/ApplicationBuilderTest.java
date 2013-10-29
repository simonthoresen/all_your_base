package all.your.swing;

import all.your.util.concurrent.Timer;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class ApplicationBuilderTest {

    @Test
    public void requireThatAccessorsWork() {
        ApplicationBuilder builder = new ApplicationBuilder();
        ApplicationState state = Mockito.mock(ApplicationState.class);
        builder.setInitialState(state);
        builder.setWindowTitle("windowTitle");
        builder.setWindowWidth(1);
        builder.setWindowHeight(2);
        Timer timer = Mockito.mock(Timer.class);
        builder.setTimer(timer);

        assertSame(state, builder.getInitialState());
        assertEquals("windowTitle", builder.getWindowTitle());
        assertEquals(1, builder.getWindowWidth());
        assertEquals(2, builder.getWindowHeight());
        assertSame(timer, builder.getTimer());
    }

    @Test
    public void requireThatApplicationCanBeBuilt() {
        ApplicationState state = Mockito.mock(ApplicationState.class);
        ApplicationBuilder builder = new ApplicationBuilder();
        builder.setInitialState(state);
        builder.setWindowTitle("my_app");
        builder.setWindowWidth(640);
        builder.setWindowHeight(480);

        Application app = builder.build();
        assertNotNull(app);
    }

    @Test
    public void requireThatBuilderHasReasonableDefaults() {
        ApplicationBuilder builder = new ApplicationBuilder();
        Application app = builder.build();
        assertNotNull(app);
    }
}
