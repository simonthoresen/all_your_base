package all.your.base.core.application;

import all.your.base.core.concurrent.SystemTimer;
import all.your.base.core.concurrent.Timer;

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

    private final Timer timer;
    private final long millisPerFrame;
    private long lastUpdateMillis;

    public AbstractApplicationState() {
        this(SystemTimer.INSTANCE, 60);
    }

    public AbstractApplicationState(Timer timer, int framesPerSecond) {
        this.timer = timer;
        this.millisPerFrame = TimeUnit.SECONDS.toMillis(1) / framesPerSecond;
        this.lastUpdateMillis = timer.currentTimeMillis();
    }

    @Override
    public void applicationStarted(Application app) {

    }

    @Override
    public void applicationStopped(Application app) {

    }

    @Override
    public final void update(ApplicationManager appManager) throws Exception {
        long now = timer.currentTimeMillis();
        long nextUpdateMillis = lastUpdateMillis + millisPerFrame;
        appManager.processEventQueue(nextUpdateMillis - now, TimeUnit.MILLISECONDS);

        now = timer.currentTimeMillis();
        update(appManager, now, now - lastUpdateMillis);
        lastUpdateMillis = now;
    }

    @SuppressWarnings("UnusedParameters")
    public void update(ApplicationManager appManager, long currentTimeMillis, long deltaTimeMillis) throws Exception {

    }

    @Override
    public void render(Surface surface) {

    }

    @Override
    public void componentResized(ComponentEvent e) {

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
