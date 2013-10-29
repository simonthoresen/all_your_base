package all.your.base.core.application;

import org.junit.Test;
import org.mockito.Mockito;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertSame;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class ApplicationStateTest {

    private static final Component SOURCE = Mockito.mock(Component.class);
    private static final long NOW = System.currentTimeMillis();

    static {
        Mockito.when(SOURCE.getLocationOnScreen()).thenReturn(new Point(0, 0));
    }

    @Test
    public void requireThatApplicationStateCanImplementApplicationListener() throws Exception {
        ApplicationListenerState state = new ApplicationListenerState();
        state.shutdown();
        new ApplicationBuilder().setInitialState(state).build().run();
        assertSame(Thread.currentThread(), state.appStartedThread);
        assertSame(Thread.currentThread(), state.appStoppedThread);
    }

    @Test
    public void requireThatApplicationStateCanImplementComponentListener() throws Exception {
        ComponentListenerState state = new ComponentListenerState();
        new ApplicationBuilder()
                .addApplicationListener(new EventDispatcher(
                        new ComponentEvent(SOURCE, ComponentEvent.COMPONENT_RESIZED),
                        new ComponentEvent(SOURCE, ComponentEvent.COMPONENT_MOVED),
                        new ComponentEvent(SOURCE, ComponentEvent.COMPONENT_SHOWN),
                        new ComponentEvent(SOURCE, ComponentEvent.COMPONENT_HIDDEN)
                ))
                .setInitialState(state)
                .build().run();
        assertSame(Thread.currentThread(), state.componentResizedThread);
        assertSame(Thread.currentThread(), state.componentMovedThread);
        assertSame(Thread.currentThread(), state.componentShownThread);
        assertSame(Thread.currentThread(), state.componentHiddenThread);
    }

    @Test
    public void requireThatApplicationStateCanImplementKeyListener() throws Exception {
        KeyListenerState state = new KeyListenerState();
        new ApplicationBuilder()
                .addApplicationListener(new EventDispatcher(
                        new KeyEvent(SOURCE, KeyEvent.KEY_TYPED, NOW, 0, KeyEvent.VK_UNDEFINED, '0'),
                        new KeyEvent(SOURCE, KeyEvent.KEY_PRESSED, NOW, 0, KeyEvent.VK_1, '1'),
                        new KeyEvent(SOURCE, KeyEvent.KEY_RELEASED, NOW, 0, KeyEvent.VK_2, '2')
                ))
                .setInitialState(state)
                .build().run();
        assertSame(Thread.currentThread(), state.keyTypedThread);
        assertSame(Thread.currentThread(), state.keyPressedThread);
        assertSame(Thread.currentThread(), state.keyReleasedThread);
    }

    @Test
    public void requireThatApplicationStateCanImplementMouseListener() throws Exception {
        MouseListenerState state = new MouseListenerState();
        new ApplicationBuilder()
                .addApplicationListener(new EventDispatcher(
                        new MouseEvent(SOURCE, MouseEvent.MOUSE_CLICKED, NOW, 0, 6, 9, 1, false),
                        new MouseEvent(SOURCE, MouseEvent.MOUSE_PRESSED, NOW, 0, 6, 9, 1, false),
                        new MouseEvent(SOURCE, MouseEvent.MOUSE_RELEASED, NOW, 0, 6, 9, 1, false),
                        new MouseEvent(SOURCE, MouseEvent.MOUSE_ENTERED, NOW, 0, 6, 9, 1, false),
                        new MouseEvent(SOURCE, MouseEvent.MOUSE_EXITED, NOW, 0, 6, 9, 1, false)
                ))
                .setInitialState(state)
                .build().run();
        assertSame(Thread.currentThread(), state.mouseClickedThread);
        assertSame(Thread.currentThread(), state.mousePressedThread);
        assertSame(Thread.currentThread(), state.mouseReleasedThread);
        assertSame(Thread.currentThread(), state.mouseEnteredThread);
        assertSame(Thread.currentThread(), state.mouseExitedThread);
    }

    private static class EventDispatcher implements ApplicationListener {

        final List<AWTEvent> events;

        EventDispatcher(AWTEvent... events) {
            this.events = Arrays.asList(events);
        }

        @Override
        public void applicationStarted(final Application app) {
            Executors.newSingleThreadExecutor().submit(new Runnable() {

                @Override
                public void run() {
                    for (AWTEvent event : events) {
                        event.setSource(app);
                        ((Component)app).dispatchEvent(event);
                    }
                }
            });
        }

        @Override
        public void applicationStopped(Application app) {

        }
    }

    private static class ApplicationListenerState extends SimpleApplicationState implements ApplicationListener {

        Thread appStartedThread;
        Thread appStoppedThread;

        @Override
        public void applicationStarted(Application app) {
            appStartedThread = Thread.currentThread();
        }

        @Override
        public void applicationStopped(Application app) {
            appStoppedThread = Thread.currentThread();
        }
    }

    private static class ComponentListenerState extends SimpleApplicationState implements ComponentListener {

        Thread componentResizedThread;
        Thread componentMovedThread;
        Thread componentShownThread;
        Thread componentHiddenThread;

        @Override
        public void componentResized(ComponentEvent e) {
            componentResizedThread = Thread.currentThread();
        }

        @Override
        public void componentMoved(ComponentEvent e) {
            componentMovedThread = Thread.currentThread();
        }

        @Override
        public void componentShown(ComponentEvent e) {
            componentShownThread = Thread.currentThread();
        }

        @Override
        public void componentHidden(ComponentEvent e) {
            componentHiddenThread = Thread.currentThread();
            shutdown();
        }
    }

    private static class KeyListenerState extends SimpleApplicationState implements KeyListener {

        Thread keyTypedThread;
        Thread keyPressedThread;
        Thread keyReleasedThread;

        @Override
        public void keyTyped(KeyEvent e) {
            keyTypedThread = Thread.currentThread();
        }

        @Override
        public void keyPressed(KeyEvent e) {
            keyPressedThread = Thread.currentThread();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            keyReleasedThread = Thread.currentThread();
            shutdown();
        }
    }

    private static class MouseListenerState extends SimpleApplicationState implements MouseListener {

        Thread mouseClickedThread;
        Thread mousePressedThread;
        Thread mouseReleasedThread;
        Thread mouseEnteredThread;
        Thread mouseExitedThread;

        @Override
        public void mouseClicked(MouseEvent e) {
            mouseClickedThread = Thread.currentThread();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mousePressedThread = Thread.currentThread();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mouseReleasedThread = Thread.currentThread();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            mouseEnteredThread = Thread.currentThread();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            mouseExitedThread = Thread.currentThread();
            shutdown();
        }
    }
}
