package all.your.base.application;

import all.your.base.graphics.Surface;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class AbstractApplicationState implements ApplicationListener, ApplicationState, ComponentListener, KeyListener,
                                                 MouseListener {

    private final long nanosPerFrame;
    private long lastUpdateNanoTime;
    private int componentWidth;
    private int componentHeight;

    public AbstractApplicationState() {
        this(60);
    }

    public AbstractApplicationState(int framesPerSecond) {
        nanosPerFrame = TimeUnit.SECONDS.toNanos(1) / framesPerSecond;
    }

    @Override
    public void applicationStarted(Application app) {
        lastUpdateNanoTime = System.nanoTime();
    }

    @Override
    public void applicationStopped(Application app) {

    }

    @Override
    public void update(ApplicationManager appManager) throws Exception {
        long nanoTime = System.nanoTime();
        long nextUpdateNanoTime = lastUpdateNanoTime + nanosPerFrame;
        appManager.processEventQueue(nextUpdateNanoTime - nanoTime, TimeUnit.NANOSECONDS);

        nanoTime = System.nanoTime();
        update(appManager, nanoTime, nanoTime - lastUpdateNanoTime);
        lastUpdateNanoTime = nanoTime;
    }

    @SuppressWarnings("UnusedParameters")
    public void update(ApplicationManager appManager, long currentTimeNanos, long deltaTimeNanos) throws Exception {

    }

    @Override
    public final void render(Surface surface) {
        surface.resize(componentWidth, componentHeight);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        Component c = e.getComponent();
        componentWidth = c.getWidth();
        componentHeight = c.getHeight();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
