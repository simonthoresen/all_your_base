package all.your.base.game.tiled;

import com.google.common.base.Preconditions;

import java.util.Objects;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class ByteArrayTileSheet implements TileSheet {

    private final byte[][] tileIds;
    private final TileIdMapper tileIdMapper;

    public ByteArrayTileSheet(byte[][] tileIds) {
        this(tileIds, TileIdMapper.IDENTITY);
    }

    public ByteArrayTileSheet(byte[][] tileIds, TileIdMapper tileIdMapper) {
        Objects.requireNonNull(tileIds, "tileIds");
        Objects.requireNonNull(tileIdMapper, "tileIdMapper");
        Preconditions.checkArgument(tileIds.length > 0, "tileIds.length == 0");
        Preconditions.checkArgument(tileIds[0].length > 0, "tileIds[0].length == 0");
        for (int i = 0; i < tileIds.length; ++i) {
            Preconditions.checkArgument(tileIds[i].length == tileIds[0].length,
                                        "tileIds[" + i + "].length != tileIds[0].length");
        }
        this.tileIds = tileIds;
        this.tileIdMapper = tileIdMapper;
    }

    public byte getTileIdAt(int x, int y) {
        return y < 0 || y >= tileIds.length || x < 0 || x >= tileIds[y].length ? 0 : tileIds[y][x];
    }

    @Override
    public int getTileAt(int x, int y) {
        return tileIdMapper.getTileSetIndex(getTileIdAt(x, y));
    }

    @Override
    public int getWidth() {
        return tileIds[0].length;
    }

    @Override
    public int getHeight() {
        return tileIds.length;
    }

    public static interface TileIdMapper {

        public static final TileIdMapper IDENTITY = new TileIdMapper() {

            @Override
            public int getTileSetIndex(byte tileSheetId) {
                return tileSheetId;
            }
        };

        int getTileSetIndex(byte tileSheetId);
    }
}
