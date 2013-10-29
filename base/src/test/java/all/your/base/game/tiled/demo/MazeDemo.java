package all.your.base.game.tiled.demo;

import all.your.base.core.application.Application;
import all.your.base.core.application.ApplicationListener;
import all.your.base.core.application.ApplicationManager;
import all.your.base.core.application.ApplicationState;
import all.your.base.core.application.Surface;
import all.your.base.game.geometry.Rectangle;
import all.your.base.game.math.Board;
import all.your.base.game.math.Light;
import all.your.base.game.math.LightMap;
import all.your.base.game.tiled.MazeBuilder;
import all.your.base.game.tiled.Tile;
import all.your.base.game.tiled.TileSet;
import all.your.base.game.tiled.TileSheet;
import all.your.base.game.tiled.TileSheetRenderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class MazeDemo implements ApplicationListener, ApplicationState, ComponentListener, KeyListener {

    public static void main(String[] args) throws Exception {
        Applications.fromState(new MazeDemo()).run();
    }

    private final int TILE_WIDTH = 32;
    private final int TILE_HEIGHT = 32;

    private final int TILE_PLAYER = 4 * 128 + 5;
    private final int TILE_TUNNEL = 22 * 128 + 22;
    private final int TILE_WALL = 23 * 128 + 37;
    private final int TILE_ENTRY = 23 * 128 + 13;
    private final int TILE_EXIT = 23 * 128 + 15;

    private final Point playerPos;
    private final TileSet tileSet;
    private final TileSheet tileSheet;
    private final Board board;
    private final TileSheetRenderer sheetRenderer;
    private LightMap lightMap;
    private int renderTileCols = 1;
    private int renderTileRows = 1;

    public MazeDemo() throws IOException {
        tileSet = new TileSet.Builder().loadImage("/" + TILE_WIDTH + "x" + TILE_HEIGHT + ".png")
                                       .setTileWidth(TILE_WIDTH)
                                       .setTileHeight(TILE_HEIGHT)
                                       .addTile(new Tile.Builder().setCanEnter(true).setIsOpaque(false).build())
                                       .addTile(new Tile.Builder().setCanEnter(false).setIsOpaque(true).build())
                                       .build();
        tileSheet = new MazeBuilder().setEntryTile(TILE_ENTRY)
                                     .setExitTile(TILE_EXIT)
                                     .setTunnelTile(TILE_TUNNEL)
                                     .setWallTile(TILE_WALL)
                                     .build();
        board = new Board(tileSet, tileSheet, new Board.TileIdMapper() {

            @Override
            public int getTileSetIndex(int tileSheetId) {
                switch (tileSheetId) {
                case TILE_ENTRY:
                case TILE_EXIT:
                case TILE_TUNNEL:
                    return 0;
                case TILE_WALL:
                    return 1;
                default:
                    throw new UnsupportedOperationException(String.valueOf(tileSheetId));
                }
            }
        });

        Random rnd = new Random(System.nanoTime());
        playerPos = findFirstTile(tileSheet, TILE_ENTRY, new Point(0, 0));
        Objects.requireNonNull(playerPos, "playerPos");
        sheetRenderer = new TileSheetRenderer(tileSheet, tileSet);
        sheetRenderer.setRenderMask(new TileSheetRenderer.TileMask() {

            @Override
            public int getLight(int x, int y) {
                return lightMap == null ? 0xFFFFFF : lightMap.getColor(x, y);
            }
        });
    }

    @Override
    public void update(ApplicationManager appManager) throws InterruptedException {
        appManager.processEventQueue(1000 / 60, TimeUnit.MILLISECONDS);
        if (lightMap == null) {
            lightMap = LightMap.newInstance(board, new Rectangle(playerPos.x - renderTileCols / 2,
                                                                 playerPos.y - renderTileRows / 2,
                                                                 renderTileCols, renderTileRows),
                                            new Light(Color.WHITE, 8),
                                            new all.your.base.game.geometry.Point(playerPos.x, playerPos.y));
        }
    }

    @Override
    public void render(Surface surface) {
        Graphics2D g = surface.getGraphics();
        g.clearRect(0, 0, renderTileCols * TILE_WIDTH, renderTileRows * TILE_HEIGHT);
        sheetRenderer.setSheetRegion(playerPos.x - renderTileCols / 2, playerPos.y - renderTileRows / 2,
                                     renderTileCols, renderTileRows);
        sheetRenderer.render(g);
        tileSet.renderTile(TILE_PLAYER, g, TILE_WIDTH * (renderTileCols / 2), TILE_HEIGHT * (renderTileRows / 2));
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {
        case 'w':
            if (isEnterableByPlayer(playerPos.x, playerPos.y - 1)) {
                --playerPos.y;
            }
            break;
        case 'a':
            if (isEnterableByPlayer(playerPos.x - 1, playerPos.y)) {
                --playerPos.x;
            }
            break;
        case 's':
            if (isEnterableByPlayer(playerPos.x, playerPos.y + 1)) {
                ++playerPos.y;
            }
            break;
        case 'd':
            if (isEnterableByPlayer(playerPos.x + 1, playerPos.y)) {
                ++playerPos.x;
            }
            break;
        }
        lightMap = null;
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private boolean isEnterableByPlayer(int x, int y) {
        return board.getTileAt(y, x).canEnter();
    }

    private static Point findFirstTile(TileSheet tileSheet, int targetTile, Point offset) {
        Point pos = new Point(offset);
        while (tileSheet.getTileAt(pos.x, pos.y) != targetTile) {
            if (++pos.x >= tileSheet.getWidth()) {
                pos.x = 0;
                if (++pos.y >= tileSheet.getHeight()) {
                    pos.y = 0;
                }
            }
            if (pos.equals(offset)) {
                return null;
            }
        }
        return pos;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        Component c = e.getComponent();
        renderTileCols = 1 + c.getWidth() / TILE_WIDTH;
        renderTileRows = 1 + c.getHeight() / TILE_HEIGHT;

        sheetRenderer.setRenderRegion(0, 0, renderTileCols * TILE_WIDTH, renderTileRows * TILE_HEIGHT);
        lightMap = null;
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    @Override
    public void applicationStarted(Application app) {
    }

    @Override
    public void applicationStopped(Application app) {

    }
}
