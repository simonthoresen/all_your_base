package all.your.awt;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
enum NullTexture implements Texture {

    INSTANCE;

    @Override
    public void paint(Graphics2D g, Rectangle viewport) {

    }

    @Override
    public void paint(Graphics2D g, Rectangle viewport, Rectangle textureRegion) {

    }

    @Override
    public Dimension getSize(Dimension out) {
        return out;
    }
}
