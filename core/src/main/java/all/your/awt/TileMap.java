package all.your.awt;

import all.your.util.Preconditions;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedHashMap;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TileMap {

    private LinkedHashMap<String, TileMapLayer> layers = new LinkedHashMap<>();
    private final int width;
    private final int height;

    public TileMap(int width, int height) {
        Preconditions.checkArgument(width > 0, "width");
        Preconditions.checkArgument(height > 0, "height");
        this.width = width;
        this.height = height;
    }

    public TileMapLayer getLayer(String id) {
        return layers.get(id);
    }

    public TileMapLayer newLayer(String id) {
        Preconditions.checkState(!layers.containsKey(id), "id '" + id + "' already in use");
        TileMapLayer layer = new TileMapLayer(width, height);
        layers.put(id, layer);
        return layer;
    }

    public TileMapLayer removeLayer(String id) {
        return layers.remove(id);
    }

    public void paint(Graphics2D g, Rectangle viewport, Rectangle mapRegion) {
        for (TileMapLayer layer : layers.values()) {
            layer.paint(g, viewport, mapRegion);
        }
    }
}
