package all.your.base.core.graphics;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TextureAtlas {

    private final BufferedImage image;
    private final Map<Integer, AtlasTexture> textures = new HashMap<>();

    public TextureAtlas(BufferedImage image) {
        this.image = image;
    }

    public AtlasTexture getTexture(int id) {
        AtlasTexture texture = textures.get(id);
        if (texture == null) {
            texture = new AtlasTexture(this, 0, 0, 0, 0);
            textures.put(id, texture);
        }
        return texture;
    }

    public AtlasTexture getTexture(int row, int col) {
        return getTexture((row << 16) | (col & 0xFFFF));
    }

    BufferedImage getImage() {
        return image;
    }

    public static TextureAtlas newInstance(BufferedImage image, int regionWidth, int regionHeight) {
        return null;
    }

    public static class Builder {

    }
}
