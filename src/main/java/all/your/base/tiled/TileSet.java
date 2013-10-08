package all.your.base.tiled;

import com.google.common.base.Preconditions;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TileSet {

    private final Tile[] tiles;
    private final BufferedImage image;
    private final int numCols;
    private final int numRows;
    private final int tileWidth;
    private final int tileHeight;

    public TileSet(Collection<Tile> tiles, BufferedImage image, int tileWidth, int tileHeight) {
        Objects.requireNonNull(tiles, "tiles");
        Objects.requireNonNull(image, "image");
        this.tiles = tiles.toArray(new Tile[tiles.size()]);
        this.image = image;
        this.numCols = image.getWidth() / tileWidth;
        this.numRows = image.getHeight() / tileHeight;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public int numCols() {
        return numCols;
    }

    public int numRows() {
        return numRows;
    }

    public int numTiles() {
        return numRows * numCols;
    }

    public int tileWidth() {
        return tileWidth;
    }

    public int tileHeight() {
        return tileHeight;
    }

    public Tile tile(int i) {
        return tiles[i];
    }

    public void renderTile(int tileIndex, Image dst, int dstRow, int dstCol) {
        Graphics2D g = (Graphics2D)dst.getGraphics();
        renderTile(tileIndex, g, dstRow, dstCol);
        g.dispose();
    }

    public void renderTile(int tileIndex, Graphics2D dst, int dstX, int dstY) {
        renderTile(tileIndex, dst, dstX, dstY, dstX + tileWidth, dstY + tileHeight);
    }

    public void renderTile(int tileIndex, Graphics2D dst, int dstX1, int dstY1, int dstX2, int dstY2) {
        int srcX = (tileIndex % numCols) * tileWidth;
        int srcY = (tileIndex / numCols) * tileHeight;
        dst.drawImage(image,
                      dstX1, dstY1, dstX2, dstY2,
                      srcX, srcY, srcX + tileWidth, srcY + tileHeight,
                      null);
    }

    public static TileSet loadFromImageFile(String fileName, int tileWidth, int tileHeight) throws IOException {
        Preconditions.checkArgument(tileWidth > 0, "tileWidth <= 0");
        Preconditions.checkArgument(tileHeight > 0, "tileHeight <= 0");
        return new TileSet(Collections.<Tile>emptyList(), BufferedImages.fromFile(fileName), tileWidth, tileHeight);
    }

    public static class Builder {

        private final List<Tile> tiles = new ArrayList<>();
        private BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        private int tileWidth = 1;
        private int tileHeight = 1;

        public Builder loadImage(String fileName) throws IOException {
            this.image = BufferedImages.fromFile(fileName);
            return this;
        }

        public Builder setImage(BufferedImage image) {
            this.image = image;
            return this;
        }

        public Builder setTileWidth(int tileWidth) {
            this.tileWidth = tileWidth;
            return this;
        }

        public Builder setTileHeight(int tileHeight) {
            this.tileHeight = tileHeight;
            return this;
        }

        public Builder addTile(Tile tile) {
            tiles.add(tile);
            return this;
        }

        public TileSet build() {
            return new TileSet(tiles, image, tileWidth, tileHeight);
        }
    }

}
