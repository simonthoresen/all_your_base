package all.your.base.tiled;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class MazeBuilderTest {

    @Test
    public void requireThatMazeCanBeBuilt() {
        assertNotNull(new MazeBuilder().build());
    }

    @Test
    public void requireThatMazeIsBuiltWithSpecifiedDimensions() {
        TileSheet maze = new MazeBuilder().setWidth(32).setHeight(24).build();
        assertEquals(32, maze.getWidth());
        assertEquals(24, maze.getHeight());
    }
}
