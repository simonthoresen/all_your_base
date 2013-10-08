package all.things.tiled;

import junit.framework.Assert;
import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TileSetTest {

    @Test
    public void requireThatTileSetCanNotBeLoadedFromNullImage() throws IOException {
        try {
            TileSet.loadFromImageFile(null, 8, 8);
            fail();
        } catch (NullPointerException e) {

        }
    }

    @Test
    public void requireThatTileSetDoesNotAcceptNegativeTileDimensions() throws IOException {
        try {
            TileSet.loadFromImageFile("/8x8.png", 0, 8);
            fail();
        } catch (IllegalArgumentException e) {

        }
        try {
            TileSet.loadFromImageFile("/8x8.png", 8, 0);
            fail();
        } catch (IllegalArgumentException e) {

        }
        try {
            TileSet.loadFromImageFile("/8x8.png", 0, 0);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void requireThatTileSetCanBeLoadedFromImage() throws IOException {
        TileSet tileSet = TileSet.loadFromImageFile("/8x8.png", 8, 8);
        assertNotNull(tileSet);
    }

    @Test
    public void requireThatTileSetKnowsTileDimensions() throws IOException {
        TileSet tileSet = TileSet.loadFromImageFile("/8x8.png", 4, 8);
        Assert.assertEquals(4, tileSet.tileWidth());
        Assert.assertEquals(8, tileSet.tileHeight());
    }

    @Test
    public void requireThatTileSetKnowsTileCount() {
        TileSet tileSet = new TileSet(Collections.<Tile>emptyList(),
                                      new BufferedImage(16, 24, BufferedImage.TYPE_INT_RGB), 4, 8);
        Assert.assertEquals(12, tileSet.numTiles());
    }

    @Test
    public void requireThatTileSetKnowsRowCount() {
        TileSet tileSet = new TileSet(Collections.<Tile>emptyList(),
                                      new BufferedImage(16, 24, BufferedImage.TYPE_INT_RGB), 4, 8);
        Assert.assertEquals(3, tileSet.numRows());
    }

    @Test
    public void requireThatTileSetKnowsColumnCount() {
        TileSet tileSet = new TileSet(Collections.<Tile>emptyList(),
                                      new BufferedImage(16, 24, BufferedImage.TYPE_INT_RGB), 4, 8);
        Assert.assertEquals(4, tileSet.numCols());
    }

    @Test
    public void requireThatTileSetCanNotLoadedFromFileNotFound() throws IOException {
        try {
            TileSet.loadFromImageFile("/fileNotFound.png", 8, 8);
            fail();
        } catch (FileNotFoundException e) {

        }
    }

    @Test
    public void requireThatTileSetCanBeCreatedFromImage() {
        new TileSet(Collections.<Tile>emptyList(), new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB), 8, 8);
    }

    @Test
    public void requireThatTileSetCanRenderTiles() throws IOException {
        Color[] tiles = { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE };
        TileSet tileSet = TileSets.newTileSet(3, 8, 8, tiles);
        BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < tiles.length; ++i) {
            tileSet.renderTile(i, image, 8, 0);
            for (int x = 0; x < 16; ++x) {
                for (int y = 0; y < 16; ++y) {
                    assertEquals("(" + x + ", " + y + ")",
                                 x >= 8 && y < 8 ? tiles[i] : Color.BLACK,
                                 new Color(image.getRGB(x, y)));
                }
            }
        }
    }
}