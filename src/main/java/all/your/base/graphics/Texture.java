package all.your.base.graphics;

import java.awt.Graphics2D;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public interface Texture {

    public void paint(Graphics2D g, int x, int y, int width, int height);
}
