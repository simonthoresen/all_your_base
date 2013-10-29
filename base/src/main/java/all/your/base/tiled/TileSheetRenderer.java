package all.your.base.tiled;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TileSheetRenderer {

    private final TileSheet sheet;
    private final TileSet tileSet;
    private TileMask tileMask;
    private int sheetX, sheetY, sheetWidth, sheetHeight;
    private int renderX, renderY, renderWidth, renderHeight;

    public TileSheetRenderer(TileSheet sheet, TileSet tileSet) {
        this.sheet = sheet;
        this.tileSet = tileSet;
    }

    public void setSheetRegion(int x, int y, int width, int height) {
        sheetX = x;
        sheetY = y;
        sheetWidth = width;
        sheetHeight = height;
    }

    public void setRenderRegion(int x, int y, int width, int height) {
        renderX = x;
        renderY = y;
        renderWidth = width;
        renderHeight = height;
    }

    public void setRenderMask(TileMask tileMask) {
        this.tileMask = tileMask;
    }

    public static interface TileMask {

        public int getLight(int x, int y);
    }

    public void render(Graphics2D g) {
        int tileWidth = renderWidth / sheetWidth;
        int tileHeight = renderHeight / sheetHeight;
        double tileFractionX = (double)(renderWidth - (tileWidth * sheetWidth)) / sheetWidth;
        double tileFractionY = (double)(renderHeight - (tileHeight * sheetHeight)) / sheetHeight;

        int renderY = this.renderY;
        double renderFractionY = 0;
        for (int tileY = 0; tileY < sheetHeight; ++tileY) {
            int fullTileHeight = tileHeight;
            renderFractionY += tileFractionY;
            if (renderFractionY > 0.5) {
                ++fullTileHeight;
                renderFractionY -= 1;
            }
            int renderX = this.renderX;
            double renderFractionX = 0;
            for (int tileX = 0; tileX < sheetWidth; ++tileX) {
                int fullTileWidth = tileWidth;
                renderFractionX += tileFractionX;
                if (renderFractionX > 0.5) {
                    ++fullTileWidth;
                    renderFractionX -= 1;
                }
                tileSet.renderTile(sheet.getTileAt(sheetX + tileX, sheetY + tileY), g,
                                   renderX, renderY, renderX + fullTileWidth, renderY + fullTileHeight);
                if (tileMask != null) {
                    int rgb = tileMask.getLight(sheetX + tileX, sheetY + tileY);
                    if (rgb != 0xFFFFFF) {
                        Color col = new Color(rgb);
                        int light = (200 * (col.getRed() + col.getGreen() + col.getBlue())) / (255 * 3);
                        g.setColor(new Color(0, 0, 0, 200 - light));
                        g.fillRect(renderX, renderY, renderX + fullTileWidth, renderY + fullTileHeight);
                    }
                }
                renderX += fullTileWidth;
            }
            renderY += fullTileHeight;
        }
    }
}
