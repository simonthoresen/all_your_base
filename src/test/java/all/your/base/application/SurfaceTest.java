package all.your.base.application;

import org.junit.Test;

import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class SurfaceTest {

    @Test
    public void requireThatAccessorsWork() {
        Surface surface = new Surface(6, 9);
        assertEquals(6, surface.getWidth());
        assertEquals(9, surface.getHeight());
        assertNotNull(surface.getGraphics());
        assertNotNull(surface.getImage());
    }

    @Test
    public void requireThatImageDimensionsMatchesSurface() {
        Surface surface = new Surface(6, 9);
        assertEquals(6, surface.getImage().getWidth());
        assertEquals(9, surface.getImage().getHeight());
    }

    @Test
    public void requireThatSurfaceCanBeResized() {
        Surface surface = new Surface(6, 9);
        surface.resize(9, 6);
        assertEquals(9, surface.getWidth());
        assertEquals(9, surface.getImage().getWidth());
        assertEquals(6, surface.getHeight());
        assertEquals(6, surface.getImage().getHeight());
    }

    @Test
    public void requireThatImageIsOnlyResizedWhenNewDimensionsAreDifferentFromCurrent() {
        Surface surface = new Surface(6, 9);
        BufferedImage image = surface.getImage();
        surface.resize(6, 9);
        assertSame(image, surface.getImage());

        surface.resize(9, 6);
        assertNotSame(image, surface.getImage());
    }
}
