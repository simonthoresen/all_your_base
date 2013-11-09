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
    private final Rectangle bounds;

    public TileMapLayer(Dimension size) {
        Preconditions.checkArgument(size.width > 0 && size.height > 0, "size; %s", size);
        this.bounds = new Rectangle(size);
        this.tiles = Arrays.asList(new Tile[size.height * size.width]);
        Collections.fill(this.tiles, NullTile.INSTANCE);
    }

    public Tile getTile(Point p) {
        if (!bounds.contains(p)) {
            return null;
        }
        Tile tile = tiles.get(p.y * bounds.width + p.x);
        if (tile == NullTile.INSTANCE) {
            tile = null;
        }
        return tile;
    }

    public Tile putTile(Point p, Tile tile) {
        Preconditions.checkIndex(p.x, bounds.width);
        Preconditions.checkIndex(p.y, bounds.height);
        if (tile == null) {
            tile = NullTile.INSTANCE;
        }
        tile = tiles.set(p.y * bounds.width + p.x, tile);
        if (tile == NullTile.INSTANCE) {
            tile = null;
        }
        return tile;
    }

    public void paint(Graphics2D g, Rectangle viewport, Rectangle mapRegion) {
        // if the requested map region does not intersect with the bounds of this map layer, then nothing will be
        // painted. return immediately
        if (!bounds.intersects(mapRegion)) {
            return;
        }

        // calculate size of each tile so that the request region covers the viewport
        int tileWidth = viewport.width / mapRegion.width;
        int tileHeight = viewport.height / mapRegion.height;

        // clip the requested map region to the bounds of this map layer, and translate the viewport accordingly
        Rectangle subRegion = bounds.intersection(mapRegion);
        viewport = new Rectangle(viewport.x + (subRegion.x - mapRegion.x) * tileWidth,
                                 viewport.y + (subRegion.y - mapRegion.y) * tileHeight,
                                 viewport.width + (subRegion.width - mapRegion.width) * tileWidth,
                                 viewport.height + (subRegion.height - mapRegion.height) * tileHeight);
        int mapRegionLeft = mapRegion.x;
        int mapRegionRight = mapRegionLeft + mapRegion.width;
        int mapRegionTop = mapRegion.y;
        int mapRegionBottom = mapRegionTop + mapRegion.height;

        // we then go ahead and render all the tiles of the map region
        Rectangle viewportRegion = new Rectangle(viewport.x, viewport.y, tileWidth, tileHeight);
        for (int y = mapRegionTop; y < mapRegionBottom; ++y) {
            for (int x = mapRegionLeft; x < mapRegionRight; ++x) {
                paint(g, viewportRegion, x, y);
                viewportRegion.x += tileWidth;
            }
            viewportRegion.x = viewport.x;
            viewportRegion.y += tileHeight;
        }

        // in case the viewport width is not dividable by the map region width, the painted layer will have an empty
        // right column. cover this by painting a fraction of the next map column, if any
        int fracWidth = viewport.width % tileWidth;
        if (fracWidth > 0 && mapRegionRight < bounds.width) {
            viewportRegion.x = viewport.x + mapRegion.width * tileWidth;
            viewportRegion.y = viewport.y;
            viewportRegion.width = fracWidth;
            viewportRegion.height = tileHeight;
            for (int y = mapRegionTop; y < mapRegionBottom; ++y) {
                paint(g, viewportRegion, mapRegionRight, y);
                viewportRegion.y += tileHeight;
            }
        }

        // similarly, cover the empty bottom row if applicable
        int fracHeight = viewport.height % tileHeight;
        if (fracHeight > 0 && mapRegionBottom < bounds.height) {
            viewportRegion.x = viewport.x;
            viewportRegion.y = viewport.y + mapRegion.height * tileHeight;
            viewportRegion.width = tileWidth;
            viewportRegion.height = fracHeight;
            for (int x = mapRegionLeft; x < mapRegionRight; ++x) {
                paint(g, viewportRegion, x, mapRegionBottom);
                viewportRegion.x += tileWidth;
            }
        }

        // if there is both an empty right column and an empty bottom row, we also need to cover the bottom right tile
        // when possible
        if (fracWidth > 0 && fracHeight > 0 && mapRegionRight < bounds.width && mapRegionBottom < bounds.height) {
            viewportRegion.x = viewport.x + mapRegion.width * tileWidth;
            viewportRegion.y = viewport.y + mapRegion.height * tileHeight;
            viewportRegion.width = fracWidth;
            viewportRegion.height = fracHeight;
            paint(g, viewportRegion, mapRegionRight, mapRegionBottom);
        }
    }

    private void paint(Graphics2D g, Rectangle viewport, /* TODO: Rectangle textureRegion */ int tileX, int tileY) {
        tiles.get(tileY * bounds.width + tileX).getTexture().paint(g, viewport /* TODO: textureRegion */);
    }
}