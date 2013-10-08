package all.your.base.application;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class ApplicationTest {

    @Test
    public void requireThatApplicationCanBeRun() throws InterruptedException, ExecutionException, TimeoutException {
        ApplicationBuilder builder = new ApplicationBuilder();
        SimpleApplicationListener listener = new SimpleApplicationListener();
        builder.addApplicationListener(listener);
        SimpleApplicationState state = new SimpleApplicationState();
        builder.setInitialState(state);
        Application app = builder.build();
        assertNotNull(app);
        Future<Object> future = ApplicationExecutor.run(app);
        assertTrue(listener.awaitStart(60, TimeUnit.SECONDS));
        state.shutdown();
        assertTrue(listener.awaitStop(60, TimeUnit.SECONDS));
        assertNull(future.get(60, TimeUnit.SECONDS));
    }
}
