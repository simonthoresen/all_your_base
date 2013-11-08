package all.your.awt;

import org.junit.Test;
import org.mockito.Mockito;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class NullTextureTest {

    @Test
    public void requireThatInstanceIsATexture() {
        assertTrue(NullTexture.INSTANCE instanceof Texture);
    }

    @Test
    public void requireThatInstanceHasZeroInteractions() {
        Dimension out = Mockito.mock(Dimension.class);
        NullTexture.INSTANCE.getSize(out);
        Mockito.verifyZeroInteractions(out);

        Graphics2D g = Mockito.mock(Graphics2D.class);
        Rectangle viewport = Mockito.mock(Rectangle.class);
        NullTexture.INSTANCE.paint(g, viewport);
        Mockito.verifyZeroInteractions(g);
        Mockito.verifyZeroInteractions(viewport);

        g = Mockito.mock(Graphics2D.class);
        viewport = Mockito.mock(Rectangle.class);
        Rectangle textureRegion = Mockito.mock(Rectangle.class);
        NullTexture.INSTANCE.paint(g, viewport, textureRegion);
        Mockito.verifyZeroInteractions(g);
        Mockito.verifyZeroInteractions(viewport);
        Mockito.verifyZeroInteractions(textureRegion);
    }
}
