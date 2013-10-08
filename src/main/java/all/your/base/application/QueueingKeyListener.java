package all.your.base.application;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
class QueueingKeyListener implements KeyListener {

    private final SimpleBlockingQueue<AWTEvent> queue;

    public QueueingKeyListener(SimpleBlockingQueue<AWTEvent> queue) {
        this.queue = queue;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        queue.add(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        queue.add(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        queue.add(e);
    }
}
