package all.your.awt;

import org.junit.Test;
import org.mockito.Mockito;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static all.your.awt.AssertImage.assertPixels;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class MapLayerTest {

    @Test
    public void requireThatConstructorChecksDimensions() {
        try {
            new MapLayer(0, 1);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("width", e.getMessage());
        }
        try {
            new MapLayer(1, 0);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("height", e.getMessage());
        }
    }

    @Test
    public void requireThatPutChecksPosition() {
        MapLayer map = new MapLayer(6, 9);
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
        MapLayer map = new MapLayer(6, 9);
        Tile tile = Mockito.mock(Tile.class);
        assertNull(map.putTile(0, 0, tile));
        assertSame(tile, map.getTile(0, 0));
    }

    @Test
    public void requireThatPutTileReturnsPreviousTile() {
        MapLayer map = new MapLayer(6, 9);
        Tile tile = Mockito.mock(Tile.class);
        assertNull(map.putTile(0, 0, tile));
        assertSame(tile, map.putTile(0, 0, Mockito.mock(Tile.class)));
    }

    @Test
    public void requireThatNullCanBePut() {
        MapLayer map = new MapLayer(6, 9);
        map.putTile(0, 0, Mockito.mock(Tile.class));
        map.putTile(0, 0, null);
        assertNull(map.getTile(0, 0));
    }

    @Test
    public void requireThatGetTileReturnsNullOutsideBoundaries() {
        MapLayer map = new MapLayer(1, 1);
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
        MapLayer map = newMapLayer(new Color[][] {
                { Color.RED, Color.ORANGE, Color.YELLOW },
                { Color.ORANGE, Color.YELLOW, Color.RED },
                { Color.YELLOW, Color.RED, Color.ORANGE },
        });
        BufferedImage image = new BufferedImage(9, 9, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        map.paint(g, 1, 1, 6, 6, 1, 0, 2, 2);
        g.dispose();
        ImageViewer.view(image, 10);
        assertPixels(image, new Color[][] {
                { },
        });
    }

    private static MapLayer newMapLayer(Color[][] tiles) {
        MapLayer map = new MapLayer(tiles[0].length, tiles.length);
        for (int row = 0; row < tiles.length; ++row) {
            for (int col = 0; col < tiles[row].length; ++col) {
                BufferedImage image = BufferedImages.newSquareGrid(1, 1, new Color[][] { { tiles[row][col] } });
                map.putTile(col, row, new SimpleTile(new SimpleTexture(image)));
            }
        }
        return map;
    }
}
