package all.your.demo.daylight;

import all.your.awt.MapLayer;
import all.your.awt.SimpleTile;
import all.your.awt.Texture;
import all.your.awt.TextureAtlas;
import all.your.awt.Textures;
import all.your.awt.Tile;
import all.your.awt.TileMap;
import all.your.swing.ApplicationBuilder;
import all.your.swing.ApplicationManager;
import all.your.swing.ApplicationState;
import all.your.swing.Surface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class DaylightDemo implements ApplicationState {

    public static void main(String[] args) throws Exception {
        new ApplicationBuilder()
                .setWindowWidth(640)
                .setWindowHeight(640)
                .setInitialState(new DaylightDemo())
                .build().run();
    }

    private final TileMap map;
    private final MapLayer ground;
    private final MapLayer shadow;

    private final Tile[] terrainTiles = new Tile[16];
    private final Tile shadowTile = new SimpleTile(Textures.newFilled(new Color(0, 0, 0, 64)));

    public DaylightDemo() throws IOException {
        map = new TileMap(new Dimension(20, 20));
        ground = map.newLayer("ground");
        shadow = map.newLayer("shadow");

        Texture texture = new TextureAtlas.Builder().setAtlasTexture(Textures.fromFile("/32x32.png"))
                                                    .setSquareSize(new Dimension(32, 32))
                                                    .build().getTexture(new Point(52, 23));
        for (int i = 0; i < terrainTiles.length; ++i) {
            terrainTiles[i] = new TerrainTile(texture, i);
        }
        for (Point p = new Point(0, 0); p.y < map.getBounds().height; ++p.y) {
            for (p.x = 0; p.x < map.getBounds().width; ++p.x) {
                ground.putTile(p, terrainTiles[0]);
            }
        }
        shadow.putTile(new Point(10, 10), shadowTile);
    }

    @Override
    public void update(ApplicationManager appManager) throws Exception {
        Thread.sleep(1000);

        // TODO: update shadow layer
    }

    @Override
    public void render(Surface surface) {
        Graphics2D g = surface.getGraphics();
        map.paint(g, new Rectangle(0, 0, surface.getWidth(), surface.getHeight()), map.getBounds());
    }
}
