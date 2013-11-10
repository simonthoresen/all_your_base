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
public class MapLayer {

    private final List<Tile> tiles;
    private final Rectangle bounds;

    public MapLayer(Dimension size) {
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
        Rectangle intersection = bounds.intersection(mapRegion);
        viewport = new Rectangle(viewport.x + (intersection.x - mapRegion.x) * tileWidth,
                                 viewport.y + (intersection.y - mapRegion.y) * tileHeight,
                                 viewport.width + (intersection.width - mapRegion.width) * tileWidth,
                                 viewport.height + (intersection.height - mapRegion.height) * tileHeight);
        mapRegion = intersection;
        int xMin = mapRegion.x;
        int xMax = xMin + mapRegion.width;
        int yMin = mapRegion.y;
        int yMax = yMin + mapRegion.height;

        // we then go ahead and render all the tiles of the map region
        Rectangle viewportRegion = new Rectangle(viewport.x, viewport.y, tileWidth, tileHeight);
        for (int y = yMin; y < yMax; ++y) {
            for (int x = xMin; x < xMax; ++x) {
                tiles.get(y * bounds.width + x).getTexture().paint(g, viewportRegion);
                viewportRegion.x += tileWidth;
            }
            viewportRegion.x = viewport.x;
            viewportRegion.y += tileHeight;
        }

        // in case the viewport width is not dividable by the map region width, the painted layer will have an empty
        // right column. cover this by painting a fraction of the next map column, if any
        int fracWidth = viewport.width - mapRegion.width * tileWidth;
        if (fracWidth > 0 && xMax < bounds.width) {
            viewportRegion.setBounds(viewport.x + mapRegion.width * tileWidth, viewport.y, fracWidth, tileHeight);
            for (int y = yMin; y < yMax; ++y) {
                Dimension textureSize = new Dimension();
                Texture texture = tiles.get(y * bounds.width + xMax).getTexture();
                texture.getSize(textureSize);
                Rectangle textureRegion = new Rectangle(0, 0,
                                                        Math.max(1, (textureSize.width * fracWidth) / tileWidth),
                                                        textureSize.height);
                texture.paint(g, viewportRegion, textureRegion);
                viewportRegion.y += tileHeight;
            }
        }

        // similarly, cover the empty bottom row if applicable
        int fracHeight = viewport.height - mapRegion.height * tileHeight;
        if (fracHeight > 0 && yMax < bounds.height) {
            viewportRegion.setBounds(viewport.x, viewport.y + mapRegion.height * tileHeight, tileWidth, fracHeight);
            for (int x = xMin; x < xMax; ++x) {
                Dimension textureSize = new Dimension();
                Texture texture = tiles.get(yMax * bounds.width + x).getTexture();
                texture.getSize(textureSize);
                Rectangle textureRegion = new Rectangle(0, 0,
                                                        textureSize.width,
                                                        Math.max(1, (textureSize.height * fracHeight) / tileHeight));
                texture.paint(g, viewportRegion, textureRegion);
                viewportRegion.x += tileWidth;
            }
        }

        // if there is both an empty right column and an empty bottom row, we also need to cover the bottom right tile
        // when possible
        if (fracWidth > 0 && fracHeight > 0 && xMax < bounds.width && yMax < bounds.height) {
            viewportRegion.setBounds(viewport.x + mapRegion.width * tileWidth,
                                     viewport.y + mapRegion.height * tileHeight,
                                     fracWidth, fracHeight);
            Dimension textureSize = new Dimension();
            Texture texture = tiles.get(yMax * bounds.width + xMax).getTexture();
            texture.getSize(textureSize);
            Rectangle textureRegion = new Rectangle(0, 0,
                                                    Math.max(1, (textureSize.width * fracWidth) / tileWidth),
                                                    Math.max(1, (textureSize.height * fracHeight) / tileHeight));
            texture.paint(g, viewportRegion, textureRegion);
        }
    }
}