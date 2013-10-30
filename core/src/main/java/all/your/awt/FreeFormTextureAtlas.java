package all.your.awt;

import com.google.common.base.Preconditions;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class FreeFormTextureAtlas implements TextureAtlas {

    private final Map<Integer, AtlasTexture> textures = new HashMap<>();

    private FreeFormTextureAtlas(Builder builder) {
        textures.putAll(builder.textures);
    }

    @Override
    public AtlasTexture getTexture(int id) {
        return textures.get(id);
    }

    public static class Builder {

        private final BufferedImage image;
        private final Map<Integer, AtlasTexture> textures = new HashMap<>();

        public Builder(BufferedImage image) {
            Objects.requireNonNull(image, "image");
            this.image = image;
        }

        public void addTexture(int id, int x, int y, int width, int height) {
            Preconditions.checkState(!textures.containsKey(id), "id " + id + " already in use");
            Preconditions.checkArgument(x >= 0 && x + width <= image.getWidth() &&
                                        y >= 0 && y + height <= image.getHeight(),
                                        "region must be in image; [%s, %s, %s, %s]", x, y, width, height);
            textures.put(id, new AtlasTexture(image, x, y, width, height));
        }

        public TextureAtlas build() {
            return new FreeFormTextureAtlas(this);
        }
    }
}
