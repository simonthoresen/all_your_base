package all.your.awt;

import org.junit.Test;
import org.mockito.Mockito;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class NullTextureTest {

    @Test
    public void requireThatInstanceIsATexture() {
        assertTrue(Texture.class.isAssignableFrom(NullTexture.INSTANCE.getClass()));
    }

    @Test
    public void requireThatInstanceHasZeroInteractions() {
        Rectangle out = Mockito.mock(Rectangle.class);
        NullTexture.INSTANCE.getBounds(out);
        Mockito.verifyZeroInteractions(out);

        Graphics2D g = Mockito.mock(Graphics2D.class);
        Rectangle viewport = Mockito.mock(Rectangle.class);
        NullTexture.INSTANCE.paint(g, viewport);
        Mockito.verifyZeroInteractions(g);
        Mockito.verifyZeroInteractions(viewport);
    }
}
