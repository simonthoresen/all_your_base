package all.your.awt;

import all.your.util.Preconditions;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TileMapLayer {

    private final List<Tile> tiles;
    private final Dimension size;

    public TileMapLayer(Dimension size) {
        Preconditions.checkArgument(size.width > 0 && size.height > 0, "size; %s", size);
        this.size = new Dimension(size);
        this.tiles = Arrays.asList(new Tile[size.height * size.width]);
        Collections.fill(this.tiles, NullTile.INSTANCE);
    }

    public Tile getTile(Point p) {
        if (p.x < 0 || p.x >= size.width || p.y < 0 || p.y >= size.height) {
            return null;
        }
        Tile tile = tiles.get(p.y * size.width + p.x);
        if (tile == NullTile.INSTANCE) {
            tile = null;
        }
        return tile;
    }

    public Tile putTile(Point p, Tile tile) {
        Preconditions.checkIndex(p.x, size.width);
        Preconditions.checkIndex(p.y, size.height);
        if (tile == null) {
            tile = NullTile.INSTANCE;
        }
        tile = tiles.set(p.y * size.width + p.x, tile);
        if (tile == NullTile.INSTANCE) {
            tile = null;
        }
        return tile;
    }

    public void paint(Graphics2D g, Rectangle viewport, Rectangle mapRegion) {
        Dimension tileSize = new Dimension(viewport.width / mapRegion.width,
                                           viewport.height / mapRegion.height);
        Rectangle viewportRegion = new Rectangle(tileSize);

        Point mapPos = new Point();
        Point mapPosMax = new Point(mapRegion.x + (viewport.width + tileSize.width - 1) / tileSize.width,
                                    mapRegion.y + (viewport.height + tileSize.height - 1) / tileSize.height);
        mapPosMax.x = Math.min(mapPosMax.x, size.width);
        mapPosMax.y = Math.min(mapPosMax.y, size.height);

        viewportRegion.y = viewport.y;
        for (mapPos.y = mapRegion.y; mapPos.y < mapPosMax.y; ++mapPos.y) {
            viewportRegion.x = viewport.x;
            for (mapPos.x = mapRegion.x; mapPos.x < mapPosMax.x; ++mapPos.x) {
                tiles.get(mapPos.y * size.width + mapPos.x).getTexture().paint(g, viewportRegion);
                viewportRegion.x += tileSize.width;
            }
            viewportRegion.y += tileSize.height;
        }
    }
}