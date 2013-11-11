package all.your.awt;

import org.junit.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.awt.image.BufferedImage;

import static all.your.awt.AssertImage.assertPixels;
import static all.your.awt.MoreColors.*;
import static org.junit.Assert.*;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class MapLayerTest {

    @Test
    public void requireThatConstructorChecksDimensions() {
        try {
            new MapLayer(new Dimension(0, 1));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("size; java.awt.Dimension[width=0,height=1]", e.getMessage());
        }
        try {
            new MapLayer(new Dimension(1, 0));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("size; java.awt.Dimension[width=1,height=0]", e.getMessage());
        }
    }

    @Test
    public void requireThatPutChecksPosition() {
        MapLayer layer = new MapLayer(new Dimension(6, 9));
        try {
            layer.putTile(new Point(-1, 0), Mockito.mock(Tile.class));
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("-1", e.getMessage());
        }
        try {
            layer.putTile(new Point(6, 0), Mockito.mock(Tile.class));
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("6", e.getMessage());
        }
        try {
            layer.putTile(new Point(0, -1), Mockito.mock(Tile.class));
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("-1", e.getMessage());
        }
        try {
            layer.putTile(new Point(0, 9), Mockito.mock(Tile.class));
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("9", e.getMessage());
        }
    }

    @Test
    public void requireThatTileCanBePut() {
        MapLayer layer = new MapLayer(new Dimension(6, 9));
        Tile tile = Mockito.mock(Tile.class);
        assertNull(layer.putTile(new Point(0, 0), tile));
        assertSame(tile, layer.getTile(new Point(0, 0)));
    }

    @Test
    public void requireThatPutTileReturnsPreviousTile() {
        MapLayer layer = new MapLayer(new Dimension(6, 9));
        Tile tile = Mockito.mock(Tile.class);
        assertNull(layer.putTile(new Point(0, 0), tile));
        assertSame(tile, layer.putTile(new Point(0, 0), Mockito.mock(Tile.class)));
    }

    @Test
    public void requireThatNullCanBePut() {
        MapLayer layer = new MapLayer(new Dimension(6, 9));
        layer.putTile(new Point(0, 0), Mockito.mock(Tile.class));
        layer.putTile(new Point(0, 0), null);
        assertNull(layer.getTile(new Point(0, 0)));
    }

    @Test
    public void requireThatGetTileReturnsNullOutsideBoundaries() {
        MapLayer layer = new MapLayer(new Dimension(1, 1));
        layer.putTile(new Point(0, 0), Mockito.mock(Tile.class));
        assertNull(layer.getTile(new Point(-1, -1)));
        assertNull(layer.getTile(new Point(-1, 0)));
        assertNull(layer.getTile(new Point(0, -1)));
        assertNull(layer.getTile(new Point(1, 1)));
        assertNull(layer.getTile(new Point(1, 0)));
        assertNull(layer.getTile(new Point(0, 1)));
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
    }

    @Test
    public void requireThatMapRegionIsClippedToMap() {
        assertPaint(
                new Rectangle(-2, 0, 4, 4),
                new Color[][] {
                        { C1, C2, C3 },
                        { C4, C5, C6 },
                        { C7, C8, C9 } },
                C0,
                new Rectangle(1, 1, 8, 8),
                new Color[][] {
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C1, C1, C2, C2 },
                        { C0, C0, C0, C0, C0, C1, C1, C2, C2 },
                        { C0, C0, C0, C0, C0, C4, C4, C5, C5 },
                        { C0, C0, C0, C0, C0, C4, C4, C5, C5 },
                        { C0, C0, C0, C0, C0, C7, C7, C8, C8 },
                        { C0, C0, C0, C0, C0, C7, C7, C8, C8 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                });
        assertPaint(
                new Rectangle(0, -2, 4, 4),
                new Color[][] {
                        { C1, C2, C3 },
                        { C4, C5, C6 },
                        { C7, C8, C9 } },
                C0,
                new Rectangle(1, 1, 8, 8),
                new Color[][] {
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C1, C1, C2, C2, C3, C3, C0, C0 },
                        { C0, C1, C1, C2, C2, C3, C3, C0, C0 },
                        { C0, C4, C4, C5, C5, C6, C6, C0, C0 },
                        { C0, C4, C4, C5, C5, C6, C6, C0, C0 },
                });
        assertPaint(
                new Rectangle(-1, -2, 3, 4),
                new Color[][] {
                        { C1, C2, C3 },
                        { C4, C5, C6 },
                        { C7, C8, C9 } },
                C0,
                new Rectangle(0, 0, 6, 8),
                new Color[][] {
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C1, C1, C2, C2, C0, C0, C0 },
                        { C0, C0, C1, C1, C2, C2, C0, C0, C0 },
                        { C0, C0, C4, C4, C5, C5, C0, C0, C0 },
                        { C0, C0, C4, C4, C5, C5, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                });
    }

    @Test
    public void requireThatPaintClipsToViewport() {
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

    @Test
    public void requireThatClippedTilesClipTexture() {
        Tile T1 = new SimpleTile(Textures.newSquareGrid(
                new Color[][] {
                        { C1, C2, C3 },
                        { C4, C5, C6 },
                        { C7, C8, C9 },
                }));
        Tile T2 = new SimpleTile(Textures.newFilled(
                new Dimension(1, 1),
                CF));
        MapLayer layer = MapLayers.newInstance(new Tile[][] {
                { T1, T2, T1 },
                { T2, T1, T2 },
                { T1, T2, T1 },
        });
        assertPaint(
                new Rectangle(0, 0, 2, 2),
                layer,
                C0,
                new Rectangle(0, 0, 2, 2),
                new Color[][] {
                        { C5, CF, C0, C0, C0, C0, C0, C0, C0 },
                        { CF, C5, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                });
        assertPaint(
                new Rectangle(0, 0, 2, 2),
                layer,
                C0,
                new Rectangle(0, 0, 3, 3),
                new Color[][] {
                        { C5, CF, C5, C0, C0, C0, C0, C0, C0 },
                        { CF, C5, CF, C0, C0, C0, C0, C0, C0 },
                        { C5, CF, C5, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                });

        assertPaint(
                new Rectangle(0, 0, 2, 2),
                layer,
                C0,
                new Rectangle(0, 0, 4, 4),
                new Color[][] {
                        { C1, C3, CF, CF, C0, C0, C0, C0, C0 },
                        { C7, C9, CF, CF, C0, C0, C0, C0, C0 },
                        { CF, CF, C1, C3, C0, C0, C0, C0, C0 },
                        { CF, CF, C7, C9, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                });
        assertPaint(
                new Rectangle(0, 0, 2, 2),
                layer,
                C0,
                new Rectangle(0, 0, 5, 5),
                new Color[][] {
                        { C1, C3, CF, CF, C1, C0, C0, C0, C0 },
                        { C7, C9, CF, CF, C7, C0, C0, C0, C0 },
                        { CF, CF, C1, C3, CF, C0, C0, C0, C0 },
                        { CF, CF, C7, C9, CF, C0, C0, C0, C0 },
                        { C1, C3, CF, CF, C1, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                });
        assertPaint(
                new Rectangle(0, 0, 2, 2),
                layer,
                C0,
                new Rectangle(0, 0, 6, 6),
                new Color[][] {
                        { C1, C2, C3, CF, CF, CF, C0, C0, C0 },
                        { C4, C5, C6, CF, CF, CF, C0, C0, C0 },
                        { C7, C8, C9, CF, CF, CF, C0, C0, C0 },
                        { CF, CF, CF, C1, C2, C3, C0, C0, C0 },
                        { CF, CF, CF, C4, C5, C6, C0, C0, C0 },
                        { CF, CF, CF, C7, C8, C9, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                });
        assertPaint(
                new Rectangle(0, 0, 2, 2),
                layer,
                C0,
                new Rectangle(0, 0, 7, 7),
                new Color[][] {
                        { C1, C2, C3, CF, CF, CF, C1, C0, C0 },
                        { C4, C5, C6, CF, CF, CF, C4, C0, C0 },
                        { C7, C8, C9, CF, CF, CF, C7, C0, C0 },
                        { CF, CF, CF, C1, C2, C3, CF, C0, C0 },
                        { CF, CF, CF, C4, C5, C6, CF, C0, C0 },
                        { CF, CF, CF, C7, C8, C9, CF, C0, C0 },
                        { C1, C2, C3, CF, CF, CF, C1, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                });
        assertPaint(
                new Rectangle(0, 0, 2, 2),
                layer,
                C0,
                new Rectangle(0, 0, 8, 8),
                new Color[][] {
                        { C1, C2, C2, C3, CF, CF, CF, CF, C0 },
                        { C4, C5, C5, C6, CF, CF, CF, CF, C0 },
                        { C4, C5, C5, C6, CF, CF, CF, CF, C0 },
                        { C7, C8, C8, C9, CF, CF, CF, CF, C0 },
                        { CF, CF, CF, CF, C1, C2, C2, C3, C0 },
                        { CF, CF, CF, CF, C4, C5, C5, C6, C0 },
                        { CF, CF, CF, CF, C4, C5, C5, C6, C0 },
                        { CF, CF, CF, CF, C7, C8, C8, C9, C0 },
                        { C0, C0, C0, C0, C0, C0, C0, C0, C0 },
                });
    }

    private static void assertPaint(Rectangle mapRegion, Color[][] mapLayer, Color background,
                                    Rectangle viewport, Color[][] expectedPixels) {
        assertPaint(mapRegion, MapLayers.newColorGrid(mapLayer), background, viewport, expectedPixels);
    }

    private static void assertPaint(Rectangle mapRegion, MapLayer layer, Color background,
                                    Rectangle viewport, Color[][] expectedPixels) {
        BufferedImage image = Images.newInstance(new Dimension(expectedPixels[0].length, expectedPixels.length),
                                                 background);
        Graphics2D g = image.createGraphics();
        TileMap map = new TileMap(layer.getBounds().getSize());
        map.putLayer("id", layer);
        map.paint(g, viewport, mapRegion);
        g.dispose();
        assertPixels(image, expectedPixels);
    }
}
