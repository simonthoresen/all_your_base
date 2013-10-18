package all.your.base.application;

import org.junit.Test;

import static org.junit.Assert.assertSame;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class ApplicationListenerTest {

    @Test
    public void requireThatListenersAreCalledByMainThread() throws Exception {
        MyListener listener = new MyListener();
        SimpleApplicationState state = new SimpleApplicationState();
        state.shutdown();
        Application app = new ApplicationBuilder()
                .addApplicationListener(listener)
                .setInitialState(state)
                .build();
        app.run();
        assertSame(app, listener.startApp);
        assertSame(Thread.currentThread(), listener.startThread);
        assertSame(app, listener.stopApp);
        assertSame(Thread.currentThread(), listener.stopThread);
    }

    private static class MyListener implements ApplicationListener {

        Application startApp;
        Application stopApp;
        Thread startThread;
        Thread stopThread;

        @Override
        public void applicationStarted(Application app) {
            startApp = app;
            startThread = Thread.currentThread();
        }

        @Override
        public void applicationStopped(Application app) {
            stopApp = app;
            stopThread = Thread.currentThread();
        }
    }
}
