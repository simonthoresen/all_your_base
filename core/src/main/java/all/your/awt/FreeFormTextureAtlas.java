package all.your.awt;

import all.your.util.Preconditions;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class FreeFormTextureAtlas implements TextureAtlas {

    private final Map<String, AtlasTexture> textures = new HashMap<>();

    private FreeFormTextureAtlas(Builder builder) {
        textures.putAll(builder.textures);
    }

    @Override
    public AtlasTexture getTexture(int id) {
        return getTexture(String.valueOf(id));
    }

    public AtlasTexture getTexture(String id) {
        return textures.get(id);
    }

    public static class Builder {

        private final BufferedImage image;
        private final Map<String, AtlasTexture> textures = new HashMap<>();

        public Builder(BufferedImage image) {
            Objects.requireNonNull(image, "image");
            this.image = image;
        }

        public Builder addTexture(int id, int x, int y, int width, int height) {
            return addTexture(String.valueOf(id), x, y, width, height);
        }

        public Builder addTexture(String id, int x, int y, int width, int height) {
            Preconditions.checkState(!textures.containsKey(id), "id '" + id + "' already in use");
            Preconditions.checkArgument(x >= 0 && x + width <= image.getWidth() &&
                                        y >= 0 && y + height <= image.getHeight(),
                                        "region [%s, %s, %s, %s] not in image [%s, %s, %s, %s]",
                                        x, y, width, height,
                                        0, 0, image.getWidth(), image.getHeight());
            textures.put(id, new AtlasTexture(image, x, y, width, height));
            return this;
        }

        public FreeFormTextureAtlas build() {
            return new FreeFormTextureAtlas(this);
        }
    }
}
