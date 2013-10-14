package all.your.base.application;

import all.your.base.graphics.Surface;
import all.your.base.graphics.SurfaceBuffer;
import all.your.base.graphics.SurfaceBuffers;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
class ApplicationImpl extends JPanel implements Application {

    private final ApplicationManagerImpl manager;
    private final List<AWTEvent> eventList = new ArrayList<>();
    private final SimpleBlockingQueue<AWTEvent> eventQueue = new SimpleBlockingQueue<>();
    private final String windowTitle;
    private final SurfaceBuffer surfaceBuffer;
    private final SwingPaintEvent paintEvent = new SwingPaintEvent();
    private final long nanosPerFrame;
    private Surface writeSurface;

    public ApplicationImpl(ApplicationBuilder builder) {
        this.manager = new ApplicationManagerImpl(builder.getApplicationListeners());
        this.manager.setState(builder.getInitialState());
        this.windowTitle = builder.getWindowTitle();
        this.nanosPerFrame = TimeUnit.SECONDS.toNanos(1) / builder.getFramesPerSecond();
        this.surfaceBuffer = SurfaceBuffers.newDoubleBuffer(builder.getWindowWidth(), builder.getWindowHeight());
        this.writeSurface = surfaceBuffer.commit();
    }

    @Override
    public void run() throws InterruptedException {
        openWindow();
        mainLoop();
    }

    private void openWindow() {
        setFocusable(true);
        setPreferredSize(new Dimension(surfaceBuffer.peek().getImage().getWidth(),
                                       surfaceBuffer.peek().getImage().getHeight()));
        addComponentListener(new QueueingComponentListener(eventQueue));
        addKeyListener(new QueueingKeyListener(eventQueue));
        addMouseListener(new QueueingMouseListener(eventQueue));
        requestFocus();

        JFrame frame = new JFrame(windowTitle);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                manager.shutdown();
            }
        });
        frame.setContentPane(this);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        frame.pack();
        frame.setVisible(true);
    }

    private void mainLoop() throws InterruptedException {
        manager.applicationStarted(this);
        processEventQueue(0, TimeUnit.SECONDS);

        long prevTime = System.nanoTime();
        while (!manager.isShutdown()) {
            long time = System.nanoTime();
            if (!updateState(time, time - prevTime)) {
                break;
            }
            render();
            waitForNextFrame(time + nanosPerFrame);
            prevTime = time;
        }

        manager.applicationStopped(this);
    }

    private boolean updateState(long currentTime, long deltaTime) {
        manager.update(currentTime, deltaTime);
        return !manager.isShutdown();
    }

    private void render() {
        // noinspection SynchronizeOnNonFinalField
        synchronized (writeSurface) {
            manager.render(writeSurface.getGraphics());
        }
        writeSurface = surfaceBuffer.commit();
        SwingUtilities.invokeLater(paintEvent);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintEvent.paint(g);
    }

    private void waitForNextFrame(long timeoutNanos) throws InterruptedException {
        processEventQueue(0, TimeUnit.NANOSECONDS);
        while (!manager.isShutdown()) {
            long nanoTime = System.nanoTime();
            if (nanoTime >= timeoutNanos) {
                break;
            }
            processEventQueue(timeoutNanos - nanoTime, TimeUnit.NANOSECONDS);
        }
    }

    private void processEventQueue(long timeout, TimeUnit unit) throws InterruptedException {
        if (!eventQueue.drainTo(eventList, timeout, unit)) {
            return; // no event was drained
        }
        for (AWTEvent event : eventList) {
            manager.dispatchEvent(event);
        }
        eventList.clear();
    }

    private class SwingPaintEvent implements Runnable {

        @Override
        public void run() {
            Graphics g = getGraphics();
            if (g != null) {
                paint(g);
            }
        }

        void paint(Graphics g) {
            Surface surface = surfaceBuffer.peek();
            // noinspection SynchronizationOnLocalVariableOrMethodParameter
            synchronized (surface) {
                surface.paint(g, 0, 0, getWidth(), getHeight());
            }
        }
    }
}
