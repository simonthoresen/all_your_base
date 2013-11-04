package all.your.awt;

import java.awt.Graphics2D;
import java.util.LinkedHashMap;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TileMap {

    private LinkedHashMap<String, MapLayer> layers = new LinkedHashMap<>();
    private final int width;
    private final int height;

    public TileMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public MapLayer getLayer(String id) {
        return layers.get(id);
    }

    public MapLayer newLayer(String id) {
        return layers.put(id, new MapLayer(width, height));
    }

    public MapLayer removeLayer(String id) {
        return layers.remove(id);
    }

    public void paint(Graphics2D g, int x, int y, int width, int height,
                      int mapX, int mapY, int mapWidth, int mapHeight) {
        for (MapLayer layer : layers.values()) {
            layer.paint(g, x, y, width, height, mapX, mapY, mapWidth, mapHeight);
        }
    }
}
