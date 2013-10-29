package all.your.swing;

import java.awt.AWTEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
class QueueingComponentListener implements ComponentListener {

    private final SimpleBlockingQueue<AWTEvent> queue;

    public QueueingComponentListener(SimpleBlockingQueue<AWTEvent> queue) {
        this.queue = queue;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        queue.add(e);
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        queue.add(e);
    }

    @Override
    public void componentShown(ComponentEvent e) {
        queue.add(e);
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        queue.add(e);
    }
}
