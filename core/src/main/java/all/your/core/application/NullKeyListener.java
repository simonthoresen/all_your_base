package all.your.core.application;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
enum NullKeyListener implements KeyListener {

    INSTANCE;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
