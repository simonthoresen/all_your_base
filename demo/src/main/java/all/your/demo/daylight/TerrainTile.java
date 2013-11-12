package all.your.demo.daylight;

import all.your.awt.SimpleTile;
import all.your.awt.Texture;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TerrainTile extends SimpleTile {

    private final int elevation;

    public TerrainTile(Texture texture, int elevation) {
        super(texture);
        this.elevation = elevation;
    }

    public int getElevation() {
        return elevation;
    }
}
