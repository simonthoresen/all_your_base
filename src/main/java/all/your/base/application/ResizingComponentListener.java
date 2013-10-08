package all.your.base.application;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
class ResizingComponentListener implements ComponentListener {

    private final BufferedSurface surface;

    public ResizingComponentListener(BufferedSurface surface) {
        this.surface = surface;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        Component c = e.getComponent();
        surface.resize(c.getWidth(), c.getHeight());
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
}
