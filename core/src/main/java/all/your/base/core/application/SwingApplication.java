package all.your.base.core.application;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
class SwingApplication extends JPanel implements Application {

    private final SwingApplicationManager manager;
    private final String windowTitle;
    private final SurfaceBuffer surfaceBuffer;
    private final SwingPaintEvent paintEvent = new SwingPaintEvent();
    private Surface writeSurface;

    public SwingApplication(ApplicationBuilder builder) {
        this.manager = new SwingApplicationManager(builder.getApplicationListeners(), builder.getTimer());
        this.manager.setState(builder.getInitialState());
        this.manager.registerListeners(this);
        this.windowTitle = builder.getWindowTitle();
        this.surfaceBuffer = SurfaceBuffers.newDoubleBuffer(builder.getWindowWidth(), builder.getWindowHeight());
        this.writeSurface = surfaceBuffer.commit();
    }

    @Override
    public void run() throws Exception {
        openWindow();
        try {
            manager.applicationStarted(this);
            while (manager.update()) {
                // noinspection SynchronizeOnNonFinalField
                synchronized (writeSurface) {
                    manager.render(writeSurface);
                }
                writeSurface = surfaceBuffer.commit();
                paintEvent.invokeLater();
            }
        } finally {
            manager.applicationStopped(this);
        }
    }

    private void openWindow() {
        setFocusable(true);
        setPreferredSize(new Dimension(surfaceBuffer.peek().getWidth(),
                                       surfaceBuffer.peek().getHeight()));

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

        requestFocus();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintEvent.paint(g);
    }

    private class SwingPaintEvent implements Runnable {

        final AtomicBoolean idle = new AtomicBoolean(true);

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
                g.drawImage(surface.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
            idle.set(true);
        }

        void invokeLater() {
            if (idle.compareAndSet(true, false)) {
                SwingUtilities.invokeLater(this);
            }
        }
    }
}
