package all.your.base.game.tiled;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TileSheetsTest {

    @Test
    public void requireThatFactoryWorks() {
        TileSheet map = TileSheets.fromString("02468ace\n" +
                                              "13579bdf");
        assertEquals(0x00, map.getTileAt(0, 0));
        assertEquals(0x02, map.getTileAt(1, 0));
        assertEquals(0x04, map.getTileAt(2, 0));
        assertEquals(0x06, map.getTileAt(3, 0));
        assertEquals(0x08, map.getTileAt(4, 0));
        assertEquals(0x0a, map.getTileAt(5, 0));
        assertEquals(0x0c, map.getTileAt(6, 0));
        assertEquals(0x0e, map.getTileAt(7, 0));
        assertEquals(0x01, map.getTileAt(0, 1));
        assertEquals(0x03, map.getTileAt(1, 1));
        assertEquals(0x05, map.getTileAt(2, 1));
        assertEquals(0x07, map.getTileAt(3, 1));
        assertEquals(0x09, map.getTileAt(4, 1));
        assertEquals(0x0b, map.getTileAt(5, 1));
        assertEquals(0x0d, map.getTileAt(6, 1));
        assertEquals(0x0f, map.getTileAt(7, 1));
    }
}
