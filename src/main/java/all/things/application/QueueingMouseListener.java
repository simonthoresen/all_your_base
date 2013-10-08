package all.things.application;

import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
class QueueingMouseListener implements MouseListener {

    private final SimpleBlockingQueue<AWTEvent> queue;

    public QueueingMouseListener(SimpleBlockingQueue<AWTEvent> queue) {
        this.queue = queue;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        queue.add(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        queue.add(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        queue.add(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        queue.add(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        queue.add(e);
    }
}
