package all.your.base.game.tiled;

import all.your.base.core.application.Applications;
import org.junit.Test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TileSheetRendererTest {

    private static final Color B = Color.BLACK;
    private static final Color R = Color.RED;
    private static final Color O = Color.ORANGE;
    private static final Color Y = Color.YELLOW;
    private static final Color G = Color.GREEN;

    @Test
    public void requireThatRendererCanRenderTiles() {
        TileSheetRenderer renderer = new TileSheetRenderer(
                TileSheets.fromString("1110111\n" +
                                      "1000001\n" +
                                      "0001000\n" +
                                      "1000001\n" +
                                      "1110111"),
                TileSets.newTileSet(1, 128, 128, R, O, Y));

        BufferedImage image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        renderer.setSheetRegion(0, 0, 4, 4);
        renderer.setRenderRegion(0, 0, 128, 64);
        renderer.render(g);
        assertRendered(image, new Color[][] { { O, O, O, R },
                                              { O, R, R, R },
                                              { R, R, R, O },
                                              { O, R, R, R },
                                              { B, B, B, B },
                                              { B, B, B, B },
                                              { B, B, B, B },
                                              { B, B, B, B } });

        renderer.setSheetRegion(2, 2, 2, 2);
        renderer.setRenderRegion(0, 0, 128, 128);
        renderer.render(g);
        assertRendered(image, new Color[][] { { R, O },
                                              { R, R } });
        g.dispose();
    }

    @Test
    public void requireThatTilesCanStretchNonUniformlyToCoverRegion() {
        assertRegionFilled(8, 8, 128, 128);
    }

    @Test
    public void requireThatTilesCanShrinkNonUniformlyToCoverRegion() {
        assertRegionFilled(128, 128, 128, 128);
    }

    @Test
    public void requireThatTilesStretchSmoothly() {
        TileSheet sheet = TileSheets.fromString("12121\n" +
                                                "21212\n" +
                                                "12121\n" +
                                                "21212\n" +
                                                "12121");
        assertRendered(sheet, TileSets.newTileSet(1, 7, 7, R, O, G),
                       new Color[][] { { O, G, O, G, O },
                                       { G, O, G, O, G },
                                       { O, G, O, G, O },
                                       { G, O, G, O, G },
                                       { O, G, O, G, O } });
        assertRendered(sheet, TileSets.newTileSet(1, 7, 7, R, O, G),
                       new Color[][] { { O, G, O, O, G, O },
                                       { G, O, G, G, O, G },
                                       { O, G, O, O, G, O },
                                       { G, O, G, G, O, G },
                                       { O, G, O, O, G, O } });
        assertRendered(sheet, TileSets.newTileSet(1, 7, 7, R, O, G),
                       new Color[][] { { O, G, O, G, O },
                                       { G, O, G, O, G },
                                       { O, G, O, G, O },
                                       { O, G, O, G, O },
                                       { G, O, G, O, G },
                                       { O, G, O, G, O } });
        assertRendered(sheet, TileSets.newTileSet(1, 7, 7, R, O, G),
                       new Color[][] { { O, G, O, O, G, O },
                                       { G, O, G, G, O, G },
                                       { O, G, O, O, G, O },
                                       { O, G, O, O, G, O },
                                       { G, O, G, G, O, G },
                                       { O, G, O, O, G, O } });
        assertRendered(sheet, TileSets.newTileSet(1, 7, 7, R, O, G),
                       new Color[][] { { O, G, G, O, G, G, O },
                                       { G, O, O, G, O, O, G },
                                       { O, G, G, O, G, G, O },
                                       { G, O, O, G, O, O, G },
                                       { O, G, G, O, G, G, O } });
        assertRendered(sheet, TileSets.newTileSet(1, 7, 7, R, O, G),
                       new Color[][] { { O, G, O, G, O },
                                       { G, O, G, O, G },
                                       { G, O, G, O, G },
                                       { O, G, O, G, O },
                                       { G, O, G, O, G },
                                       { G, O, G, O, G },
                                       { O, G, O, G, O } });
        assertRendered(sheet, TileSets.newTileSet(1, 7, 7, R, O, G),
                       new Color[][] { { O, G, G, O, G, G, O },
                                       { G, O, O, G, O, O, G },
                                       { G, O, O, G, O, O, G },
                                       { O, G, G, O, G, G, O },
                                       { G, O, O, G, O, O, G },
                                       { G, O, O, G, O, O, G },
                                       { O, G, G, O, G, G, O } });
    }

    @Test
    public void requireThatTilesShrinkSmoothly() {
        TileSheet sheet = TileSheets.fromString("12121\n" +
                                                "21212\n" +
                                                "12121\n" +
                                                "21212\n" +
                                                "12121");
        assertRendered(sheet, TileSets.newTileSet(1, 7, 7, R, O, G),
                       new Color[][] { { O, G, O, G, O },
                                       { G, O, G, O, G },
                                       { O, G, O, G, O },
                                       { G, O, G, O, G },
                                       { O, G, O, G, O } });
        assertRendered(sheet, TileSets.newTileSet(1, 7, 7, R, O, G),
                       new Color[][] { { O, G, G, O },
                                       { G, O, O, G },
                                       { O, G, G, O },
                                       { G, O, O, G },
                                       { O, G, G, O } });
        assertRendered(sheet, TileSets.newTileSet(1, 7, 7, R, O, G),
                       new Color[][] { { O, G, O, G, O },
                                       { G, O, G, O, G },
                                       { G, O, G, O, G },
                                       { O, G, O, G, O } });
        assertRendered(sheet, TileSets.newTileSet(1, 7, 7, R, O, G),
                       new Color[][] { { O, G, G, O },
                                       { G, O, O, G },
                                       { G, O, O, G },
                                       { O, G, G, O } });
        assertRendered(sheet, TileSets.newTileSet(1, 7, 7, R, O, G),
                       new Color[][] { { O, O, O },
                                       { G, G, G },
                                       { G, G, G },
                                       { O, O, O } });
        assertRendered(sheet, TileSets.newTileSet(1, 7, 7, R, O, G),
                       new Color[][] { { O, G, G, O },
                                       { O, G, G, O },
                                       { O, G, G, O } });
        assertRendered(sheet, TileSets.newTileSet(1, 7, 7, R, O, G),
                       new Color[][] { { O, O, O },
                                       { O, O, O },
                                       { O, O, O } });
    }

    private static void assertRendered(TileSheet sheet, TileSet tileSet, Color[][] expectedPixels) {
        TileSheetRenderer renderer = new TileSheetRenderer(sheet, tileSet);
        BufferedImage image = new BufferedImage(expectedPixels[0].length, expectedPixels.length,
                                                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        renderer.setRenderRegion(0, 0, expectedPixels[0].length, expectedPixels.length);
        renderer.setSheetRegion(0, 0, sheet.getWidth(), sheet.getHeight());
        renderer.render(g);
        //displayImage(image, 50);
        assertRendered(image, expectedPixels);
    }

    private static void assertRegionFilled(int tileWidth, int tileHeight, int renderWidth, int renderHeight) {
        TileSheet sheet = TileSheets.fromString("12121\n" +
                                                "21212\n" +
                                                "12121\n" +
                                                "21212\n" +
                                                "12121");
        TileSheetRenderer renderer = new TileSheetRenderer(
                sheet,
                TileSets.newTileSet(1, tileWidth, tileHeight, R, O, G));

        BufferedImage image = new BufferedImage(renderWidth, renderHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        renderer.setRenderRegion(0, 0, renderWidth, renderHeight);
        for (int sheetWidth = 1; sheetWidth <= sheet.getWidth(); ++sheetWidth) {
            for (int sheetHeight = 1; sheetHeight <= sheet.getHeight(); ++sheetHeight) {
                g.clearRect(0, 0, renderWidth, renderHeight);
                renderer.setSheetRegion(0, 0, sheetWidth, sheetHeight);
                renderer.render(g);
                assertEquals("sheetWidth = " + sheetWidth + ", sheetHeight = " + sheetHeight,
                             sheet.getTileAt(sheetWidth - 1, sheetHeight - 1) == 1 ? O : G,
                             new Color(image.getRGB(renderWidth - 1, renderHeight - 1)));
            }
        }
        g.dispose();
    }

    private static void assertRendered(BufferedImage image, Color[][] expectedTiles) {
        int tileHeight = image.getHeight() / expectedTiles.length;
        int tileWidth = image.getWidth() / expectedTiles[0].length;
        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                assertEquals("x = " + x + ", y = " + y,
                             expectedTiles[y / tileHeight][x / tileWidth],
                             new Color(image.getRGB(x, y)));
            }
        }
    }

    private static void displayImage(BufferedImage image, int scale) {
        try {
            Applications.fromImage(image, scale).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
