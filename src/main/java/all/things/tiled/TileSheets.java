package all.things.tiled;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TileSheets {

    public static TileSheet fromString(String map) {
        String[] arr = map.split("\n");
        int numRows = arr.length;
        byte[][] tileIds = new byte[numRows][];
        for (int row = 0; row < numRows; ++row) {
            int numCols = arr[row].length();
            tileIds[row] = new byte[numCols];
            for (int col = 0; col < numCols; ++col) {
                tileIds[row][col] = (byte)Character.digit(arr[row].charAt(col), 16);
            }
        }
        return new ByteArrayTileSheet(tileIds);
    }
}
