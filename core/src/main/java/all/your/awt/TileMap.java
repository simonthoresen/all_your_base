package all.your.awt;

import all.your.util.Preconditions;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedHashMap;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TileMap {

    private final LinkedHashMap<String, MapLayer> layers = new LinkedHashMap<>();
    private final Dimension size;

    public TileMap(Dimension size) {
        Preconditions.checkArgument(size.width > 0 && size.height > 0, "size; %s", size);
        this.size = new Dimension(size);
    }

    public MapLayer getLayer(String id) {
        return layers.get(id);
    }

    public MapLayer newLayer(String id) {
        Preconditions.checkState(!layers.containsKey(id), "id '" + id + "' already in use");
        MapLayer layer = new MapLayer(size);
        layers.put(id, layer);
        return layer;
    }

    public MapLayer removeLayer(String id) {
        return layers.remove(id);
    }

    public void paint(Graphics2D g, Rectangle viewport, Rectangle mapRegion) {
        for (MapLayer layer : layers.values()) {
            layer.paint(g, viewport, mapRegion);
        }
    }
}
