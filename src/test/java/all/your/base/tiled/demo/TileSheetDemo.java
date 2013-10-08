package all.your.base.tiled.demo;

import all.your.base.application.ApplicationManager;
import all.your.base.application.ApplicationState;
import all.your.base.application.Applications;
import all.your.base.tiled.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TileSheetDemo implements ApplicationState, KeyListener {

    private final TileSet tileSet = TileSets.newTileSet(3, 8, 8, Color.RED, Color.GREEN, Color.BLUE);
    private final TileSheet tileSheet = TileSheets.fromString("11111011111\n" +
                                                              "10000000001\n" +
                                                              "10000000001\n" +
                                                              "10022200001\n" +
                                                              "10020000001\n" +
                                                              "00020002000\n" +
                                                              "10000002001\n" +
                                                              "10000222001\n" +
                                                              "10000000001\n" +
                                                              "10000000001\n" +
                                                              "11111011111");
    private final TileSheetRenderer renderer = new TileSheetRenderer(tileSheet, tileSet);
    private final Rectangle sheetRegion = new Rectangle(0, 0, 4, 4);

    @Override
    public void update(ApplicationManager appManager, long currentTimeNanos, long deltaTimeNanos) {

    }

    @Override
    public void render(Graphics2D g) {
        g.clearRect(0, 0, 640, 480);
        renderer.setSheetRegion(sheetRegion.x, sheetRegion.y, sheetRegion.width, sheetRegion.height);
        renderer.setRenderRegion(0, 0, 640, 480);
        renderer.render(g);

        g.clearRect(528, 10, 102, 102);
        renderer.setSheetRegion(0, 0, tileSheet.getWidth(), tileSheet.getHeight());
        renderer.setRenderRegion(529, 11, 100, 100);
        renderer.render(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {
        case 'w':
            --sheetRegion.y;
            break;
        case 'a':
            --sheetRegion.x;
            break;
        case 's':
            ++sheetRegion.y;
            break;
        case 'd':
            ++sheetRegion.x;
            break;
        case 'W':
            ++sheetRegion.height;
            break;
        case 'A':
            sheetRegion.width = Math.max(1, sheetRegion.width - 1);
            break;
        case 'S':
            sheetRegion.height = Math.max(1, sheetRegion.height - 1);
            break;
        case 'D':
            ++sheetRegion.width;
            break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) throws Exception {
        Applications.fromState(new TileSheetDemo()).run();
    }
}
