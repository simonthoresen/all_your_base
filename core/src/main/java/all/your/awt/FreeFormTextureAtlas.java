package all.your.awt;

import all.your.util.Preconditions;

import java.awt.Rectangle;
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

        // TODO: replace image with texture
        public Builder(BufferedImage image) {
            Objects.requireNonNull(image, "image");
            this.image = image;
        }

        public Builder addTexture(int id, Rectangle region) {
            return addTexture(String.valueOf(id), region);
        }

        public Builder addTexture(String id, Rectangle region) {
            Preconditions.checkState(!textures.containsKey(id), "id '" + id + "' already in use");
            Preconditions.checkArgument(BufferedImages.getBounds(image, new Rectangle()).contains(region),
                                        "region; %s", region);
            textures.put(id, new AtlasTexture(image, region));
            return this;
        }

        public FreeFormTextureAtlas build() {
            return new FreeFormTextureAtlas(this);
        }
    }
}
