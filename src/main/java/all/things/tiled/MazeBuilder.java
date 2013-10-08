package all.things.tiled;

import java.awt.Point;
import java.util.Arrays;
import java.util.Random;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class MazeBuilder {

    private final Random random = new Random(System.nanoTime());
    private int height = 49;
    private int width = 49;
    private int blockScale = 10;
    private int tunnelTile = 0;
    private int wallTile = 1;
    private int entryTile = 2;
    private int exitTile = 3;
    private double connectedness = 0.4;

    public MazeBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public MazeBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    public MazeBuilder setBlockScale(int blockScale) {
        if (blockScale < 1) {
            throw new IllegalArgumentException();
        }
        this.blockScale = blockScale;
        return this;
    }

    public MazeBuilder setConnectedness(double connectedness) {
        this.connectedness = connectedness;
        return this;
    }

    public MazeBuilder setEntryTile(int entryTile) {
        this.entryTile = entryTile;
        return this;
    }

    public MazeBuilder setExitTile(int exitTile) {
        this.exitTile = exitTile;
        return this;
    }

    public MazeBuilder setTunnelTile(int tunnelTile) {
        this.tunnelTile = tunnelTile;
        return this;
    }

    public MazeBuilder setWallTile(int wallTile) {
        this.wallTile = wallTile;
        return this;
    }

    public TileSheet build() {
        int[][] arr = buildBasicMaze();

        Point entry = randomTile(arr, wallTile);
        arr[entry.y][entry.x] = entryTile;

        Point exit = randomTile(arr, tunnelTile);
        arr[exit.y][exit.x] = exitTile;

        return new MazeSheet(wallTile, arr);
    }

    private Point randomTile(int[][] arr, int targetTile) {
        Random rnd = new Random();
        int y = rnd.nextInt(arr.length);
        int x = rnd.nextInt(arr[y].length);

        Point pos = new Point(x, y);
        while (arr[pos.y][pos.x] != targetTile) {
            if (++pos.x >= arr[pos.y].length) {
                pos.x = 0;
                if (++pos.y >= arr.length) {
                    pos.y = 0;
                }
            }
            if (pos.x == x && pos.y == y) {
                throw new IllegalStateException(String.valueOf(targetTile));
            }
        }
        return pos;
    }


    private int[][] buildBasicMaze() {
        int rowLen = (height - 1) / 2;
        int colLen = (width + 1) / 2;
        int[] lhs = new int[colLen];
        int[] rhs = new int[colLen];
        int idx;
        for (lhs[0] = 1, idx = colLen; --idx > 0; lhs[idx] = rhs[idx] = idx) {
            /* get it? */
        }
        int[][] arr = new int[height][width];
        for (int row = 0; row < height; ++row) {
            Arrays.fill(arr[row], wallTile);
        }
        for (int row = 0; row < rowLen; ++row) {
            for (int col = colLen; --col > 0; ) {
                arr[row * 2 + 1][width - col * 2] = tunnelTile;
                if ((col != (idx = lhs[col - 1])) &&
                    (random.nextDouble() > connectedness || (row == rowLen - 1 && col == rhs[col]))) {
                    lhs[rhs[idx] = rhs[col]] = idx;
                    lhs[rhs[col] = col - 1] = col;
                    arr[row * 2 + 1][width - col * 2 + 1] = tunnelTile;
                }
                if ((col != (idx = lhs[col]) && random.nextDouble() > connectedness) ||
                    (row == rowLen - 1)) {
                    lhs[rhs[idx] = rhs[col]] = idx;
                    lhs[col] = rhs[col] = col;
                } else {
                    arr[row * 2 + 2][width - col * 2] = tunnelTile;
                }
            }
        }
        return arr;
    }

    private static class MazeSheet implements TileSheet {

        final int wallTile;
        final int[][] tiles;

        public MazeSheet(int wallTile, int[][] tiles) {
            this.wallTile = wallTile;
            this.tiles = tiles;
        }

        @Override
        public int getTileAt(int x, int y) {
            return y < 0 || y >= tiles.length || x < 0 || x >= tiles[y].length ? wallTile : tiles[y][x];
        }

        @Override
        public int getWidth() {
            return tiles[0].length;
        }

        @Override
        public int getHeight() {
            return tiles.length;
        }

        @Override
        public String toString() {
            StringBuilder out = new StringBuilder();
            for (int y = 0; y < tiles.length; ++y) {
                for (int x = 0; x < tiles[y].length; ++x) {
                    out.append(Integer.toString(tiles[y][x]));
                }
                out.append('\n');
            }
            return out.toString();
        }
    }
}