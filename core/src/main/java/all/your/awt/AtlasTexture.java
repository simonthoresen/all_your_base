package all.your.awt;

import all.your.util.Preconditions;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class AtlasTexture implements Texture {

    private final BufferedImage image;
    private final Rectangle atlasRegion;

    // TODO: replace image with texture
    AtlasTexture(BufferedImage image, Rectangle region) {
        Objects.requireNonNull(image, "image");
        Preconditions.checkArgument(BufferedImages.getBounds(image, new Rectangle()).contains(region),
                                    "region; %s", region);
        this.image = image;
        this.atlasRegion = new Rectangle(region);
    }

    @Override
    public void paint(Graphics2D g, Rectangle viewport) {
        paint(g, viewport, atlasRegion);
    }

    @Override
    public void paint(Graphics2D g, Rectangle viewport, Rectangle textureRegion) {
        g.drawImage(image, viewport.x, viewport.y, viewport.x + viewport.width, viewport.y + viewport.height,
                    atlasRegion.x, atlasRegion.y, atlasRegion.x + atlasRegion.width, atlasRegion.y + atlasRegion.height,
                    null);
    }

    @Override
    public Dimension getSize(Dimension out) {
        out.width = atlasRegion.width;
        out.height = atlasRegion.height;
        return out;
    }
}
