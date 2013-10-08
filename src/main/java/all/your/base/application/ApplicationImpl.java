package all.your.base.application;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
class ApplicationImpl extends JPanel implements Application {

    private final ApplicationManagerImpl manager;
    private final BufferedSurface surface;
    private final Renderer renderer = new Renderer();
    private final SimpleBlockingQueue<AWTEvent> eventQueue = new SimpleBlockingQueue<>();
    private final List<AWTEvent> eventList = new ArrayList<>();
    private final String windowTitle;
    private final long nanosPerFrame;
    private final int windowWidth;
    private final int windowHeight;

    public ApplicationImpl(ApplicationBuilder builder) {
        this.manager = new ApplicationManagerImpl(builder.getApplicationListeners());
        this.manager.setState(builder.getInitialState());
        this.windowTitle = builder.getWindowTitle();
        this.windowWidth = builder.getWindowWidth();
        this.windowHeight = builder.getWindowHeight();
        this.nanosPerFrame = TimeUnit.SECONDS.toNanos(1) / builder.getFramesPerSecond();
        this.surface = BufferedSurface.newTripleBuffered(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void run() throws InterruptedException {
        openWindow();
        mainLoop();
    }

    private void openWindow() {
        setFocusable(true);
        setPreferredSize(new Dimension(windowWidth, windowHeight));
        addComponentListener(new QueueingComponentListener(eventQueue));
        addComponentListener(new ResizingComponentListener(surface));
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
        BufferedSurface.Buffer buf = surface.retainBuffer();
        try {
            manager.render(buf.getGraphics());
        } finally {
            surface.releaseBuffer(buf);
        }
        SwingUtilities.invokeLater(renderer);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderer.render(g);
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

    private class Renderer implements Runnable {

        @Override
        public void run() {
            Graphics g = getGraphics();
            if (g != null) {
                render(g);
            }
        }

        void render(Graphics g) {
            // called by EDT
            surface.render(g);
        }
    }
}
