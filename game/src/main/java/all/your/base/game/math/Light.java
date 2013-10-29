package all.your.base.game.math;

import java.awt.Color;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class Light {

    private final Color color;
    private final int range;

    public Light(Color color, int range) {
        this.color = color;
        this.range = range;
    }

    public Color color() {
        return color;
    }

    public int range() {
        return range;
    }
}
