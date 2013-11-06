package all.your.awt;

import org.junit.Test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static all.your.awt.AssertImage.assertPixels;
import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;
import static java.awt.Color.ORANGE;
import static java.awt.Color.RED;
import static java.awt.Color.YELLOW;
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
        TileMapLayer foo = map.newLayer("foo");
        assertNotNull(foo);
        TileMapLayer bar = map.newLayer("bar");
        assertNotNull(bar);
        assertSame(foo, map.getLayer("foo"));
        assertSame(bar, map.getLayer("bar"));
    }

    @Test
    public void requireThatLayerCanBeRemoved() {
        TileMap map = new TileMap(1, 1);
        TileMapLayer foo = map.newLayer("foo");
        assertSame(foo, map.removeLayer("foo"));
        assertNull(map.getLayer("foo"));
        assertNull(map.removeLayer("foo"));
    }

    @Test
    public void requireThatMapCanBePainted() {
        TileMap map = new TileMap(3, 3);
        TileMapLayers.fillColorGrid(map.newLayer("foo"), new Color[][] {
                { RED, ORANGE, YELLOW },
                { ORANGE, null, null },
                { null, null, null },
        });
        TileMapLayers.fillColorGrid(map.newLayer("bar"), new Color[][] {
                { BLUE, null, null },
                { null, null, RED },
                { YELLOW, RED, ORANGE },
        });
        BufferedImage image = new BufferedImage(9, 9, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(GREEN);
        g.fillRect(0, 0, 9, 9);
        map.paint(g, new Rectangle(3, 3, 6, 6), new Rectangle(0, 0, 3, 3));
        g.dispose();
        assertPixels(image, new Color[][] {
                { GREEN, GREEN, GREEN, GREEN, GREEN, GREEN, GREEN, GREEN, GREEN },
                { GREEN, GREEN, GREEN, GREEN, GREEN, GREEN, GREEN, GREEN, GREEN },
                { GREEN, GREEN, GREEN, GREEN, GREEN, GREEN, GREEN, GREEN, GREEN },
                { GREEN, GREEN, GREEN, BLUE, BLUE, ORANGE, ORANGE, YELLOW, YELLOW },
                { GREEN, GREEN, GREEN, BLUE, BLUE, ORANGE, ORANGE, YELLOW, YELLOW },
                { GREEN, GREEN, GREEN, ORANGE, ORANGE, GREEN, GREEN, RED, RED },
                { GREEN, GREEN, GREEN, ORANGE, ORANGE, GREEN, GREEN, RED, RED },
                { GREEN, GREEN, GREEN, YELLOW, YELLOW, RED, RED, ORANGE, ORANGE },
                { GREEN, GREEN, GREEN, YELLOW, YELLOW, RED, RED, ORANGE, ORANGE },
        });
    }
}
