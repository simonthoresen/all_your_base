package all.your.base.math;

import all.your.base.tiled.Tile;
import all.your.base.tiled.TileSet;
import all.your.base.tiled.TileSheets;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class FovMapTestCase {

    @Test
    public void requireThatFieldOfViewIsOnlyCalculatedWithinRectangle() {
        Board board = newBoard("111111111111111\n" +
                               "100000000000001\n" +
                               "100000000000001\n" +
                               "100111000111001\n" +
                               "100100000001001\n" +
                               "100100000001001\n" +
                               "100000000000001\n" +
                               "100100000001001\n" +
                               "100100000001001\n" +
                               "100111000111001\n" +
                               "100000000000001\n" +
                               "100000000000001\n" +
                               "111111111111111\n");
        assertFov(board, new Rectangle(4, 4, 10, 5), new Point(5, 5),
                  "###############\n" +
                  "###############\n" +
                  "###############\n" +
                  "###############\n" +
                  "####........###\n" +
                  "####.@......###\n" +
                  "####..........#\n" +
                  "####..........#\n" +
                  "####........###\n" +
                  "###############\n" +
                  "###############\n" +
                  "###############\n" +
                  "###############\n");
    }

    @Test
    public void requireThatFieldOfViewCanBeCalculated() {
        Board board = newBoard("1111111111111\n" +
                               "1000000000001\n" +
                               "1011100011101\n" +
                               "1010000000101\n" +
                               "1010011100101\n" +
                               "1000011100001\n" +
                               "1010011100101\n" +
                               "1010000000101\n" +
                               "1011100011101\n" +
                               "1000000000001\n" +
                               "1111111111111\n");
        assertFov(board, new Point(1, 1),
                  ".............\n" +
                  ".@...........\n" +
                  ".............\n" +
                  "...#####...##\n" +
                  "...##########\n" +
                  "...##########\n" +
                  "...##########\n" +
                  "...##########\n" +
                  "...##########\n" +
                  "...##########\n" +
                  "...##########\n");
        assertFov(board, new Point(3, 3),
                  "#######.....#\n" +
                  "######.....##\n" +
                  "##.........##\n" +
                  "##.@.......##\n" +
                  "##.........##\n" +
                  "##....#######\n" +
                  "##....#######\n" +
                  "##....#######\n" +
                  "##.....######\n" +
                  "#####..######\n" +
                  "#####..######\n");
        assertFov(board, new Point(8, 8),
                  "#######.#####\n" +
                  "#######.#####\n" +
                  "#######....##\n" +
                  "#######....##\n" +
                  "..#####....##\n" +
                  "#...###....##\n" +
                  "##.........##\n" +
                  "##.........##\n" +
                  "####....@.###\n" +
                  ".............\n" +
                  ".............\n");
        assertFov(board, new Point(11, 9),
                  "##########...\n" +
                  "##########...\n" +
                  "##########...\n" +
                  "##########...\n" +
                  "##########...\n" +
                  "##########...\n" +
                  "##########...\n" +
                  "##...#####...\n" +
                  ".............\n" +
                  "...........@.\n" +
                  ".............\n");
    }

    private static Board newBoard(String map) {
        return new Board(new TileSet.Builder().addTile(new Tile.Builder().setIsOpaque(false).build())
                                              .addTile(new Tile.Builder().setIsOpaque(true).build())
                                              .build(),
                         TileSheets.fromString(map));
    }

    private static void assertFov(Board board, Point eye, String expectedFov) {
        assertFov(board, new Rectangle(0, 0, board.width(), board.height()), eye, expectedFov);
    }

    private static void assertFov(Board board, Rectangle viewRect, Point eye, String expectedFov) {
        FovMap map = FovMap.newInstance(board, viewRect, eye);
        StringBuilder out = new StringBuilder();
        for (int y = 0; y < board.height(); ++y) {
            for (int x = 0; x < board.width(); ++x) {
                if (map.canSee(x, y)) {
                    if (x == eye.x() && y == eye.y()) {
                        out.append('@');
                    } else {
                        out.append(".");
                    }
                } else {
                    out.append("#");
                }
            }
            out.append("\n");
        }
        assertEquals(expectedFov, out.toString());
    }
}
