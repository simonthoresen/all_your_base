package all.your.awt;

import org.junit.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.awt.image.BufferedImage;

import static all.your.awt.AssertImage.assertPixels;
import static all.your.awt.Palette.*;
import static org.junit.Assert.*;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TileMapLayerTest {

    @Test
    public void requireThatConstructorChecksDimensions() {
        try {
            new TileMapLayer(new Dimension(0, 1));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("size; java.awt.Dimension[width=0,height=1]", e.getMessage());
        }
        try {
            new TileMapLayer(new Dimension(1, 0));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("size; java.awt.Dimension[width=1,height=0]", e.getMessage());
        }
    }

    @Test
    public void requireThatPutChecksPosition() {
        TileMapLayer map = new TileMapLayer(new Dimension(6, 9));
        try {
            map.putTile(new Point(-1, 0), Mockito.mock(Tile.class));
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("-1", e.getMessage());
        }
        try {
            map.putTile(new Point(6, 0), Mockito.mock(Tile.class));
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("6", e.getMessage());
        }
        try {
            map.putTile(new Point(0, -1), Mockito.mock(Tile.class));
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("-1", e.getMessage());
        }
        try {
            map.putTile(new Point(0, 9), Mockito.mock(Tile.class));
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("9", e.getMessage());
        }
    }

    @Test
    public void requireThatTileCanBePut() {
        TileMapLayer map = new TileMapLayer(new Dimension(6, 9));
        Tile tile = Mockito.mock(Tile.class);
        assertNull(map.putTile(new Point(0, 0), tile));
        assertSame(tile, map.getTile(new Point(0, 0)));
    }

    @Test
    public void requireThatPutTileReturnsPreviousTile() {
        TileMapLayer map = new TileMapLayer(new Dimension(6, 9));
        Tile tile = Mockito.mock(Tile.class);
        assertNull(map.putTile(new Point(0, 0), tile));
        assertSame(tile, map.putTile(new Point(0, 0), Mockito.mock(Tile.class)));
    }

    @Test
    public void requireThatNullCanBePut() {
        TileMapLayer map = new TileMapLayer(new Dimension(6, 9));
        map.putTile(new Point(0, 0), Mockito.mock(Tile.class));
        map.putTile(new Point(0, 0), null);
        assertNull(map.getTile(new Point(0, 0)));
    }

    @Test
    public void requireThatGetTileReturnsNullOutsideBoundaries() {
        TileMapLayer map = new TileMapLayer(new Dimension(1, 1));
        map.putTile(new Point(0, 0), Mockito.mock(Tile.class));
        assertNull(map.getTile(new Point(-1, -1)));
        assertNull(map.getTile(new Point(-1, 0)));
        assertNull(map.getTile(new Point(0, -1)));
        assertNull(map.getTile(new Point(1, 1)));
        assertNull(map.getTile(new Point(1, 0)));
        assertNull(map.getTile(new Point(0, 1)));
    }

    @Test
    public void requireThatMapLayerCanBePainted() {
        assertPaint(
                new Rectangle(0, 0, 1, 1),
                new Color[][] {
                        { C1, C2, C3 },
                        { C4, C5, C6 },
                        { C7, C8, C9 } },
                C0,
                new Rectangle(3, 3, 6, 6),
                new Color[][] {
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C1, C1, C1, C1, C1, C1 },
                        { C0, C0, C0, C1, C1, C1, C1, C1, C1 },
                        { C0, C0, C0, C1, C1, C1, C1, C1, C1 },
                        { C0, C0, C0, C1, C1, C1, C1, C1, C1 },
                        { C0, C0, C0, C1, C1, C1, C1, C1, C1 },
                        { C0, C0, C0, C1, C1, C1, C1, C1, C1 },
                });
        assertPaint(
                new Rectangle(0, 0, 2, 1),
                new Color[][] {
                        { C1, C2, C3 },
                        { C4, C5, C6 },
                        { C7, C8, C9 } },
                C0,
                new Rectangle(3, 3, 6, 6),
                new Color[][] {
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C1, C1, C1, C2, C2, C2 },
                        { C0, C0, C0, C1, C1, C1, C2, C2, C2 },
                        { C0, C0, C0, C1, C1, C1, C2, C2, C2 },
                        { C0, C0, C0, C1, C1, C1, C2, C2, C2 },
                        { C0, C0, C0, C1, C1, C1, C2, C2, C2 },
                        { C0, C0, C0, C1, C1, C1, C2, C2, C2 },
                });
        assertPaint(
                new Rectangle(0, 0, 1, 2),
                new Color[][] {
                        { C1, C2, C3 },
                        { C4, C5, C6 },
                        { C7, C8, C9 } },
                C0,
                new Rectangle(3, 3, 6, 6),
                new Color[][] {
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C1, C1, C1, C1, C1, C1 },
                        { C0, C0, C0, C1, C1, C1, C1, C1, C1 },
                        { C0, C0, C0, C1, C1, C1, C1, C1, C1 },
                        { C0, C0, C0, C4, C4, C4, C4, C4, C4 },
                        { C0, C0, C0, C4, C4, C4, C4, C4, C4 },
                        { C0, C0, C0, C4, C4, C4, C4, C4, C4 },
                });
        assertPaint(
                new Rectangle(0, 0, 2, 2),
                new Color[][] {
                        { C1, C2, C3 },
                        { C4, C5, C6 },
                        { C7, C8, C9 } },
                C0,
                new Rectangle(3, 3, 6, 6),
                new Color[][] {
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C1, C1, C1, C2, C2, C2 },
                        { C0, C0, C0, C1, C1, C1, C2, C2, C2 },
                        { C0, C0, C0, C1, C1, C1, C2, C2, C2 },
                        { C0, C0, C0, C4, C4, C4, C5, C5, C5 },
                        { C0, C0, C0, C4, C4, C4, C5, C5, C5 },
                        { C0, C0, C0, C4, C4, C4, C5, C5, C5 },
                });
        assertPaint(
                new Rectangle(0, 0, 3, 2),
                new Color[][] {
                        { C1, C2, C3 },
                        { C4, C5, C6 },
                        { C7, C8, C9 } },
                C0,
                new Rectangle(3, 3, 6, 6),
                new Color[][] {
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C1, C1, C2, C2, C3, C3 },
                        { C0, C0, C0, C1, C1, C2, C2, C3, C3 },
                        { C0, C0, C0, C1, C1, C2, C2, C3, C3 },
                        { C0, C0, C0, C4, C4, C5, C5, C6, C6 },
                        { C0, C0, C0, C4, C4, C5, C5, C6, C6 },
                        { C0, C0, C0, C4, C4, C5, C5, C6, C6 },
                });
        assertPaint(
                new Rectangle(0, 0, 3, 3),
                new Color[][] {
                        { C1, C2, C3 },
                        { C4, C5, C6 },
                        { C7, C8, C9 } },
                C0,
                new Rectangle(3, 3, 6, 6),
                new Color[][] {
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C1, C1, C2, C2, C3, C3 },
                        { C0, C0, C0, C1, C1, C2, C2, C3, C3 },
                        { C0, C0, C0, C4, C4, C5, C5, C6, C6 },
                        { C0, C0, C0, C4, C4, C5, C5, C6, C6 },
                        { C0, C0, C0, C7, C7, C8, C8, C9, C9 },
                        { C0, C0, C0, C7, C7, C8, C8, C9, C9 },
                });
        assertPaint(
                new Rectangle(1, 1, 2, 2),
                new Color[][] {
                        { C1, C2, C3 },
                        { C4, C5, C6 },
                        { C7, C8, C9 } },
                C0,
                new Rectangle(3, 3, 6, 6),
                new Color[][] {
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C5, C5, C5, C6, C6, C6 },
                        { C0, C0, C0, C5, C5, C5, C6, C6, C6 },
                        { C0, C0, C0, C5, C5, C5, C6, C6, C6 },
                        { C0, C0, C0, C8, C8, C8, C9, C9, C9 },
                        { C0, C0, C0, C8, C8, C8, C9, C9, C9 },
                        { C0, C0, C0, C8, C8, C8, C9, C9, C9 },
                });
        assertPaint(
                new Rectangle(1, 1, 2, 2),
                new Color[][] {
                        { C1, C2, C3 },
                        { C4, C5, C6 },
                        { C7, C8, C9 } },
                C0,
                new Rectangle(3, 3, 5, 5),
                new Color[][] {
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C5, C5, C6, C6, C0, C0 },
                        { C0, C0, C0, C5, C5, C6, C6, C0, C0 },
                        { C0, C0, C0, C8, C8, C9, C9, C0, C0 },
                        { C0, C0, C0, C8, C8, C9, C9, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                });
        assertPaint(
                new Rectangle(0, 0, 2, 2),
                new Color[][] {
                        { C1, C2, C3 },
                        { C4, C5, C6 },
                        { C7, C8, C9 } },
                C0,
                new Rectangle(3, 3, 5, 5),
                new Color[][] {
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C1, C1, C2, C2, C3, C0 },
                        { C0, C0, C0, C1, C1, C2, C2, C3, C0 },
                        { C0, C0, C0, C4, C4, C5, C5, C6, C0 },
                        { C0, C0, C0, C4, C4, C5, C5, C6, C0 },
                        { C0, C0, C0, C7, C7, C8, C8, C9, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                });
    }

    private static void assertPaint(Rectangle mapRegion, Color[][] mapLayer, Color background,
                                    Rectangle viewport, Color[][] expectedPixels)
    {
        TileMapLayer map = TileMapLayers.newColorGrid(mapLayer);
        BufferedImage image = BufferedImages.newFilled(new Dimension(expectedPixels[0].length, expectedPixels.length),
                                                       background);
        Graphics2D g = image.createGraphics();
        map.paint(g, viewport, mapRegion);
        g.dispose();
        assertPixels(image, expectedPixels);
    }
}
