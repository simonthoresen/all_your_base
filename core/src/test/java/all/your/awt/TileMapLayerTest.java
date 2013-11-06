package all.your.awt;

import org.junit.Test;
import org.mockito.Mockito;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static all.your.awt.AssertImage.assertPixels;
import static java.awt.Color.GREEN;
import static java.awt.Color.ORANGE;
import static java.awt.Color.RED;
import static java.awt.Color.YELLOW;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TileMapLayerTest {

    @Test
    public void requireThatConstructorChecksDimensions() {
        try {
            new TileMapLayer(0, 1);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("width", e.getMessage());
        }
        try {
            new TileMapLayer(1, 0);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("height", e.getMessage());
        }
    }

    @Test
    public void requireThatPutChecksPosition() {
        TileMapLayer map = new TileMapLayer(6, 9);
        try {
            map.putTile(-1, 0, Mockito.mock(Tile.class));
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("-1", e.getMessage());
        }
        try {
            map.putTile(6, 0, Mockito.mock(Tile.class));
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("6", e.getMessage());
        }
        try {
            map.putTile(0, -1, Mockito.mock(Tile.class));
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("-1", e.getMessage());
        }
        try {
            map.putTile(0, 9, Mockito.mock(Tile.class));
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("9", e.getMessage());
        }
    }

    @Test
    public void requireThatTileCanBePut() {
        TileMapLayer map = new TileMapLayer(6, 9);
        Tile tile = Mockito.mock(Tile.class);
        assertNull(map.putTile(0, 0, tile));
        assertSame(tile, map.getTile(0, 0));
    }

    @Test
    public void requireThatPutTileReturnsPreviousTile() {
        TileMapLayer map = new TileMapLayer(6, 9);
        Tile tile = Mockito.mock(Tile.class);
        assertNull(map.putTile(0, 0, tile));
        assertSame(tile, map.putTile(0, 0, Mockito.mock(Tile.class)));
    }

    @Test
    public void requireThatNullCanBePut() {
        TileMapLayer map = new TileMapLayer(6, 9);
        map.putTile(0, 0, Mockito.mock(Tile.class));
        map.putTile(0, 0, null);
        assertNull(map.getTile(0, 0));
    }

    @Test
    public void requireThatGetTileReturnsNullOutsideBoundaries() {
        TileMapLayer map = new TileMapLayer(1, 1);
        map.putTile(0, 0, Mockito.mock(Tile.class));
        assertNull(map.getTile(-1, -1));
        assertNull(map.getTile(-1, 0));
        assertNull(map.getTile(0, -1));
        assertNull(map.getTile(1, 1));
        assertNull(map.getTile(1, 0));
        assertNull(map.getTile(0, 1));
    }

    @Test
    public void requireThatLayerCanBePainted() {
        TileMapLayer map = TileMapLayers.newColorGrid(new Color[][] {
                { RED, ORANGE, YELLOW },
                { ORANGE, YELLOW, RED },
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
                { GREEN, GREEN, GREEN, RED, RED, ORANGE, ORANGE, YELLOW, YELLOW },
                { GREEN, GREEN, GREEN, RED, RED, ORANGE, ORANGE, YELLOW, YELLOW },
                { GREEN, GREEN, GREEN, ORANGE, ORANGE, YELLOW, YELLOW, RED, RED },
                { GREEN, GREEN, GREEN, ORANGE, ORANGE, YELLOW, YELLOW, RED, RED },
                { GREEN, GREEN, GREEN, YELLOW, YELLOW, RED, RED, ORANGE, ORANGE },
                { GREEN, GREEN, GREEN, YELLOW, YELLOW, RED, RED, ORANGE, ORANGE },
        });
    }
}