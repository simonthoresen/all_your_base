package all.your.awt;

import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class NullTileTest {

    @Test
    public void requireThatInstanceIsATile() {
        assertTrue(NullTile.INSTANCE instanceof Tile);
    }

    @Test
    public void requireThatInstancReturnsNullTexture() {
        assertSame(NullTexture.INSTANCE, NullTile.INSTANCE.getTexture());
    }
}
