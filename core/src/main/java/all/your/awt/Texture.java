package all.your.awt;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public interface Texture {

    public void paint(Graphics2D g, Rectangle viewport);

    public void paint(Graphics2D g, Rectangle viewport, Rectangle textureRegion);

    public Dimension getSize(Dimension out);
}
