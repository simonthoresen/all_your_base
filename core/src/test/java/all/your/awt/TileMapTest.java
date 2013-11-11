package all.your.awt;

import org.junit.Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static all.your.awt.AssertImage.assertPixels;
import static all.your.awt.MoreColors.C0;
import static all.your.awt.MoreColors.C1;
import static all.your.awt.MoreColors.C2;
import static all.your.awt.MoreColors.C3;
import static all.your.awt.MoreColors.C4;
import static all.your.awt.MoreColors.C_;
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
            new TileMap(new Dimension(0, 1));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("size; java.awt.Dimension[width=0,height=1]", e.getMessage());
        }
        try {
            new TileMap(new Dimension(1, 0));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("size; java.awt.Dimension[width=1,height=0]", e.getMessage());
        }
    }

    @Test
    public void requireThatLayerIdMustBeUnique() {
        TileMap map = new TileMap(new Dimension(1, 1));
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
        TileMap map = new TileMap(new Dimension(1, 1));
        MapLayer foo = map.newLayer("foo");
        assertNotNull(foo);
        MapLayer bar = map.newLayer("bar");
        assertNotNull(bar);
        assertSame(foo, map.getLayer("foo"));
        assertSame(bar, map.getLayer("bar"));
    }

    @Test
    public void requireThatLayerCanBeRemoved() {
        TileMap map = new TileMap(new Dimension(1, 1));
        MapLayer foo = map.newLayer("foo");
        assertSame(foo, map.removeLayer("foo"));
        assertNull(map.getLayer("foo"));
        assertNull(map.removeLayer("foo"));
    }

    @Test
    public void requireThatMapCanBePainted() {
        TileMap map = new TileMap(new Dimension(3, 3));
        MapLayers.fillColorGrid(map.newLayer("foo"), new Color[][] {
                { C1, C2, C3 },
                { C2, C_, C_ },
                { C_, C_, C_ },
        });
        MapLayers.fillColorGrid(map.newLayer("bar"), new Color[][] {
                { C4, C_, C_ },
                { C_, C_, C1 },
                { C3, C1, C2 },
        });
        BufferedImage image = Images.newInstance(new Dimension(9, 9), C0);
        Graphics2D g = image.createGraphics();
        map.paint(g, new Rectangle(3, 3, 6, 6), new Rectangle(0, 0, 3, 3));
        g.dispose();
        assertPixels(image, new Color[][] {
                { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                { C0, C0, C0, C4, C4, C2, C2, C3, C3 },
                { C0, C0, C0, C4, C4, C2, C2, C3, C3 },
                { C0, C0, C0, C2, C2, C0, C0, C1, C1 },
                { C0, C0, C0, C2, C2, C0, C0, C1, C1 },
                { C0, C0, C0, C3, C3, C1, C1, C2, C2 },
                { C0, C0, C0, C3, C3, C1, C1, C2, C2 },
        });
    }
}
