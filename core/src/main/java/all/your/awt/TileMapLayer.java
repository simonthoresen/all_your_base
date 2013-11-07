package all.your.awt;

import all.your.util.Preconditions;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TileMapLayer {

    private final Tile[][] tiles;

    public TileMapLayer(Dimension size) {
        Preconditions.checkArgument(size.width > 0 && size.height > 0, "size; %s", size);
        tiles = new Tile[size.height][size.width];
    }

    public Tile getTile(Point p) {
        if (p.y < 0 || p.y >= tiles.length ||
            p.x < 0 || p.x >= tiles[p.y].length) {
            return null;
        }
        return tiles[p.y][p.x];
    }

    public Tile putTile(Point p, Tile tile) {
        Preconditions.checkIndex(p.y, tiles.length);
        Preconditions.checkIndex(p.x, tiles[p.y].length);
        Tile oldTile = tiles[p.y][p.x];
        tiles[p.y][p.x] = tile;
        return oldTile;
    }

    public void paint(Graphics2D g, Rectangle viewport, Rectangle mapRegion) {
        Dimension tileSize = new Dimension(viewport.width / mapRegion.width,
                                           viewport.height / mapRegion.height);
        Rectangle viewportRegion = new Rectangle(tileSize);

        Point mapPos = new Point();
        Point mapPosMax = new Point(mapRegion.x + (viewport.width + tileSize.width - 1) / tileSize.width,
                                    mapRegion.y + (viewport.height + tileSize.height - 1) / tileSize.height);
        viewportRegion.y = viewport.y;
        for (mapPos.y = mapRegion.y; mapPos.y < mapPosMax.y; ++mapPos.y) {
            viewportRegion.x = viewport.x;
            for (mapPos.x = mapRegion.x; mapPos.x < mapPosMax.x; ++mapPos.x) {
                Tile tile = getTile(mapPos);
                if (tile != null) {
                    tile.getTexture().paint(g, viewportRegion);
                    // TODO: clipping
                }
                viewportRegion.x += tileSize.width;
            }
            viewportRegion.y += tileSize.height;
        }
    }
}