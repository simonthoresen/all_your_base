package all.things.tiled;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class ByteArrayTileSheetTest {

    @Test
    public void requireThatSheetRequiresIdMapper() {
        try {
            new ByteArrayTileSheet(new byte[1][1], null);
            fail();
        } catch (NullPointerException e) {
            assertEquals("tileIdMapper", e.getMessage());
        }
    }

    @Test
    public void requireThatSheetRequiresSquaredData() {
        assertNullPointer("tileIds", null);
        assertIllegalArgument("tileIds.length == 0", new byte[0][]);
        assertNullPointer(null, new byte[1][]);
        assertIllegalArgument("tileIds[0].length == 0", new byte[1][0]);
        assertNullPointer(null, new byte[][] { { 0 }, null });
        assertIllegalArgument("tileIds[1].length != tileIds[0].length", new byte[][] { { 0 }, { } });
    }

    @Test
    public void requireThatAccessorsWork() {
        TileSheet map = new ByteArrayTileSheet(new byte[][] { { 0x11, 0x12 }, { 0x21, 0x22 } });
        assertEquals(0x11, map.getTileAt(0, 0));
        assertEquals(0x12, map.getTileAt(1, 0));
        assertEquals(0x21, map.getTileAt(0, 1));
        assertEquals(0x22, map.getTileAt(1, 1));
    }

    private static void assertNullPointer(String expectedMessage, byte[][] tiles) {
        try {
            new ByteArrayTileSheet(tiles);
            fail();
        } catch (NullPointerException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    private static void assertIllegalArgument(String expectedMessage, byte[][] tiles) {
        try {
            new ByteArrayTileSheet(tiles);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}
