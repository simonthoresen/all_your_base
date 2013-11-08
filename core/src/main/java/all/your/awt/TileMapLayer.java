package all.your.awt;

import all.your.util.Preconditions;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Objects;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TileMapLayer {

    private final Tile[][] tiles;
    private final Tile nullTile;

    public TileMapLayer(Dimension size, Tile nullTile) {
        Preconditions.checkArgument(size.width > 0 && size.height > 0, "size; %s", size);
        Objects.requireNonNull(nullTile, "nullTile");
        this.tiles = new Tile[size.height][size.width];
        this.nullTile = nullTile;
    }

    public Tile getTile(Point p) {
        if (p.y < 0 || p.y >= tiles.length ||
            p.x < 0 || p.x >= tiles[p.y].length) {
            return nullTile;
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

        // TODO: cap map pos to valid positions to avoid having to call getTile()
        // TODO: explicitly render null tile as a different pass


        Point mapPos = new Point();
        Point mapPosMax = new Point(mapRegion.x + (viewport.width + tileSize.width - 1) / tileSize.width,
                                    mapRegion.y + (viewport.height + tileSize.height - 1) / tileSize.height);
        viewportRegion.y = viewport.y;
        for (mapPos.y = mapRegion.y; mapPos.y < mapPosMax.y; ++mapPos.y) {
            viewportRegion.x = viewport.x;
            for (mapPos.x = mapRegion.x; mapPos.x < mapPosMax.x; ++mapPos.x) {
                getTile(mapPos).getTexture().paint(g, viewportRegion);
                viewportRegion.x += tileSize.width;
            }
            viewportRegion.y += tileSize.height;
        }
    }
}