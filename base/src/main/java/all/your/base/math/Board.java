package all.your.base.math;

import all.your.base.tiled.Tile;
import all.your.base.tiled.TileSet;
import all.your.base.tiled.TileSheet;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class Board {

    private final TileSet tileSet;
    private final TileSheet tileSheet;
    private final TileIdMapper tileIdMapper;

    public Board(TileSet tileSet, TileSheet tileSheet) {
        this(tileSet, tileSheet, TileIdMapper.IDENTITY);
    }

    public Board(TileSet tileSet, TileSheet tileSheet, TileIdMapper tileIdMapper) {
        this.tileSet = tileSet;
        this.tileSheet = tileSheet;
        this.tileIdMapper = tileIdMapper;
    }

    public Tile getTileAt(int y, int x) {
        return tileSet.tile(tileIdMapper.getTileSetIndex(tileSheet.getTileAt(x, y)));
    }

    public int width() {
        return tileSheet.getWidth();
    }

    public int height() {
        return tileSheet.getHeight();
    }

    public static interface TileIdMapper {

        public static final TileIdMapper IDENTITY = new TileIdMapper() {

            @Override
            public int getTileSetIndex(int tileSheetId) {
                return tileSheetId;
            }
        };

        int getTileSetIndex(int tileSheetId);
    }
}
