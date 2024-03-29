package all.your.awt;

import all.your.util.Preconditions;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class Texture {

    private final BufferedImage image;
    private final Rectangle region;

    public Texture(BufferedImage image) {
        Objects.requireNonNull(image, "image");
        this.image = image;
        this.region = new Rectangle(0, 0, image.getWidth(), image.getHeight());
    }

    public Texture(Texture texture, Rectangle region) {
        Objects.requireNonNull(texture, "texture");
        Preconditions.checkArgument(texture.region.contains(region), "region; %s", region);
        this.image = texture.image;
        this.region = new Rectangle(texture.region.x + region.x, texture.region.y + region.y,
                                    region.width, region.height);
    }

    public void paint(Graphics2D g, Rectangle viewport) {
        g.drawImage(image, viewport.x, viewport.y, viewport.x + viewport.width, viewport.y + viewport.height,
                    region.x, region.y, region.x + region.width, region.y + region.height, null);
    }

    public Rectangle getBounds(Rectangle out) {
        out.setBounds(region);
        return out;
    }
}
