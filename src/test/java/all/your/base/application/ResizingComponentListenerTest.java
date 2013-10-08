package all.your.base.application;

import org.junit.Test;
import org.mockito.Mockito;

import java.awt.Component;
import java.awt.event.ComponentEvent;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class ResizingComponentListenerTest {

    @Test
    public void requireThatBufferedSurfaceIsResized() {
        BufferedSurface surface = Mockito.mock(BufferedSurface.class);
        Component source = Mockito.mock(Component.class);
        Mockito.when(source.getWidth()).thenReturn(6);
        Mockito.when(source.getHeight()).thenReturn(9);
        ComponentEvent e = new ComponentEvent(source, ComponentEvent.COMPONENT_RESIZED);
        new ResizingComponentListener(surface).componentResized(e);

        Mockito.verify(surface, Mockito.times(1)).resize(6, 9);
    }
}
