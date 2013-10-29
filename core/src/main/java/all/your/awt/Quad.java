package all.your.awt;

import java.awt.Graphics2D;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class Quad {

    private Texture texture;
    private int x, y, width, height;

    public void paint(Graphics2D g) {
        texture.paint(g, x, y, width, height);
    }
}
