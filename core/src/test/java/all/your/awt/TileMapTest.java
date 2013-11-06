package all.your.awt;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TileMapTest {

    @Test
    public void requireThatConstructorChecksDimensions() {
        try {
            new TileMap(0, 1);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("width", e.getMessage());
        }
        try {
            new TileMap(1, 0);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("height", e.getMessage());
        }
    }

    @Test
    public void requireThatLayerIdMustBeUnique() {
        TileMap map = new TileMap(1, 1);
        map.newLayer("foo");
        try {
            map.newLayer("foo");
            fail();
        } catch (IllegalStateException e) {
            assertEquals("id 'foo' already in use", e.getMessage());
        }
    }

    @Test
    public void requireThatLayerCanBeRetrieved() {
        TileMap map = new TileMap(1, 1);
        MapLayer foo = map.newLayer("foo");
        assertNotNull(foo);
        MapLayer bar = map.newLayer("bar");
        assertNotNull(bar);
        assertSame(foo, map.getLayer("foo"));
        assertSame(bar, map.getLayer("bar"));
    }

    @Test
    public void requireThatLayerCanBeRemoved() {
        TileMap map = new TileMap(1, 1);
        MapLayer foo = map.newLayer("foo");
        assertSame(foo, map.removeLayer("foo"));
        assertNull(map.getLayer("foo"));
        assertNull(map.removeLayer("foo"));
    }

    @Test
    public void requireThatMapCanBePainted() {

    }
}
