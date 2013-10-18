package all.your.base.application;

import all.your.base.graphics.Surface;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
class SwingApplicationManager implements ApplicationManager {

    private final List<ApplicationListener> appListeners;
    private final SimpleBlockingQueue<AWTEvent> eventQueue = new SimpleBlockingQueue<>();
    private ApplicationListener stateAppListener = NullApplicationListener.INSTANCE;
    private ApplicationState state = NullApplicationState.INSTANCE;
    private ComponentListener stateComponentListener = NullComponentListener.INSTANCE;
    private KeyListener stateKeyListener = NullKeyListener.INSTANCE;
    private MouseListener stateMouseListener = NullMouseListener.INSTANCE;
    private volatile boolean shutdown = false;

    public SwingApplicationManager(Component c, List<ApplicationListener> appListeners) {
        this.appListeners = new ArrayList<>(appListeners);

        c.addComponentListener(new QueueingComponentListener(eventQueue));
        c.addKeyListener(new QueueingKeyListener(eventQueue));
        c.addMouseListener(new QueueingMouseListener(eventQueue));
    }

    @Override
    public boolean processEventQueue(long duration, TimeUnit unit) throws InterruptedException {
        doProcessEventQueue(0, TimeUnit.NANOSECONDS);
        long timeoutNanos = System.nanoTime() + unit.toNanos(duration);
        while (true) {
            long nanoTime = System.nanoTime();
            if (nanoTime >= timeoutNanos) {
                break;
            }
            doProcessEventQueue(timeoutNanos - nanoTime, TimeUnit.NANOSECONDS);
        }
        return !shutdown;
    }

    private void doProcessEventQueue(long timeout, TimeUnit unit) throws InterruptedException {
        Collection<AWTEvent> events = eventQueue.drain(timeout, unit);
        for (Iterator<AWTEvent> it = events.iterator(); it.hasNext() && !shutdown; ) {
            dispatchEvent(it.next());
        }
    }

    private void dispatchEvent(AWTEvent e) {
        switch (e.getID()) {
        case ComponentEvent.COMPONENT_RESIZED:
            stateComponentListener.componentResized((ComponentEvent)e);
            break;
        case ComponentEvent.COMPONENT_MOVED:
            stateComponentListener.componentMoved((ComponentEvent)e);
            break;
        case ComponentEvent.COMPONENT_SHOWN:
            stateComponentListener.componentShown((ComponentEvent)e);
            break;
        case ComponentEvent.COMPONENT_HIDDEN:
            stateComponentListener.componentHidden((ComponentEvent)e);
            break;
        case KeyEvent.KEY_TYPED:
            stateKeyListener.keyTyped((KeyEvent)e);
            break;
        case KeyEvent.KEY_PRESSED:
            stateKeyListener.keyPressed((KeyEvent)e);
            break;
        case KeyEvent.KEY_RELEASED:
            stateKeyListener.keyReleased((KeyEvent)e);
            break;
        case MouseEvent.MOUSE_CLICKED:
            stateMouseListener.mouseClicked((MouseEvent)e);
            break;
        case MouseEvent.MOUSE_PRESSED:
            stateMouseListener.mousePressed((MouseEvent)e);
            break;
        case MouseEvent.MOUSE_RELEASED:
            stateMouseListener.mouseReleased((MouseEvent)e);
            break;
        case MouseEvent.MOUSE_ENTERED:
            stateMouseListener.mouseEntered((MouseEvent)e);
            break;
        case MouseEvent.MOUSE_EXITED:
            stateMouseListener.mouseExited((MouseEvent)e);
            break;
        }
    }

    @Override
    public void setState(ApplicationState state) {
        this.state = state;
        this.stateAppListener = castTo(state, ApplicationListener.class, NullApplicationListener.INSTANCE);
        this.stateComponentListener = castTo(state, ComponentListener.class, NullComponentListener.INSTANCE);
        this.stateKeyListener = castTo(state, KeyListener.class, NullKeyListener.INSTANCE);
        this.stateMouseListener = castTo(state, MouseListener.class, NullMouseListener.INSTANCE);
    }

    @Override
    public void shutdown() {
        shutdown = true;
    }

    public boolean update() throws Exception {
        state.update(this);
        return !shutdown;
    }

    public void render(Surface surface) {
        state.render(surface);
    }

    public void applicationStarted(Application app) {
        for (ApplicationListener listener : appListeners) {
            listener.applicationStarted(app);
        }
        stateAppListener.applicationStarted(app);
    }

    public void applicationStopped(Application app) {
        for (ApplicationListener listener : appListeners) {
            listener.applicationStopped(app);
        }
        stateAppListener.applicationStopped(app);
    }

    private static <T> T castTo(Object candidate, Class<T> targetClass, T defaultValue) {
        if (!targetClass.isInstance(candidate)) {
            return defaultValue;
        }
        return targetClass.cast(candidate);
    }
}