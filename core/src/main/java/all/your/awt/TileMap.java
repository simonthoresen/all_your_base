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

    private final LinkedHashMap<String, TileMapLayer> layers = new LinkedHashMap<>();
    private final Dimension size;

    public TileMap(Dimension size) {
        Preconditions.checkArgument(size.width > 0 && size.height > 0, "size; %s", size);
        this.size = new Dimension(size);
    }

    public TileMapLayer getLayer(String id) {
        return layers.get(id);
    }

    public TileMapLayer newLayer(String id, Tile nullTile) {
        Preconditions.checkState(!layers.containsKey(id), "id '" + id + "' already in use");
        TileMapLayer layer = new TileMapLayer(size, nullTile);
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
