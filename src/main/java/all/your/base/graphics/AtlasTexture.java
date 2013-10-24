package all.your.base.graphics;

import java.awt.Graphics2D;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class AtlasTexture implements Texture {

    private final TextureAtlas atlas;
    private final int x, y, width, height;

    AtlasTexture(TextureAtlas atlas, int x, int y, int width, int height) {
        this.atlas = atlas;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void paint(Graphics2D g, int x, int y, int width, int height) {
        g.drawImage(atlas.getImage(), x, y, x + width, y + height,
                    this.x, this.y, this.x + this.width, this.y + this.height, null);
    }
}
