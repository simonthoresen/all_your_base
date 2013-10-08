package all.things.application;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

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
        builder.setFramesPerSecond(3);

        assertSame(state, builder.getInitialState());
        assertEquals("windowTitle", builder.getWindowTitle());
        assertEquals(1, builder.getWindowWidth());
        assertEquals(2, builder.getWindowHeight());
        assertEquals(3, builder.getFramesPerSecond());
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
