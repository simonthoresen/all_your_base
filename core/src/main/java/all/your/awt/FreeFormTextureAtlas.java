package all.your.awt;

import all.your.util.Preconditions;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class FreeFormTextureAtlas implements TextureAtlas {

    private final Map<String, Texture> textures = new HashMap<>();

    private FreeFormTextureAtlas(Builder builder) {
        textures.putAll(builder.textures);
    }

    @Override
    public Texture getTexture(int id) {
        return getTexture(String.valueOf(id));
    }

    public Texture getTexture(String id) {
        return textures.get(id);
    }

    public static class Builder {

        private final Texture atlas;
        private final Map<String, Texture> textures = new HashMap<>();

        public Builder(Texture atlas) {
            Objects.requireNonNull(atlas, "atlas");
            this.atlas = atlas;
        }

        public Builder addTexture(int id, Rectangle region) {
            return addTexture(String.valueOf(id), region);
        }

        public Builder addTexture(String id, Rectangle region) {
            Preconditions.checkState(!textures.containsKey(id), "id '" + id + "' already in use");
            textures.put(id, new Texture(atlas, region));
            return this;
        }

        public FreeFormTextureAtlas build() {
            return new FreeFormTextureAtlas(this);
        }
    }
}
