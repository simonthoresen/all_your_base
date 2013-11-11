package all.your.awt;

import all.your.util.LazyMap;
import all.your.util.Preconditions;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class TextureAtlas {

    private final Map<Integer, Texture> textures = LazyMap.newHashMap();
    private final Map<Point, Texture> gridCache = LazyMap.newHashMap();
    private final Texture atlasTexture;
    private final Dimension squareSize;

    private TextureAtlas(Builder builder) {
        Objects.requireNonNull(builder.atlasTexture, "atlasTexture");
        this.atlasTexture = builder.atlasTexture;
        this.squareSize = builder.squareSize;
        this.textures.putAll(builder.textures);
    }

    public Texture getTexture(int id) {
        return textures.get(id);
    }

    public Texture getTexture(Point p) {
        Objects.requireNonNull(squareSize, "squareSize");
        Texture texture = gridCache.get(p);
        if (texture == null) {
            texture = new Texture(atlasTexture, new Rectangle(squareSize.width * p.x, squareSize.height * p.y,
                                                              squareSize.width, squareSize.height));
            gridCache.put(new Point(p), texture);
        }
        return texture;
    }

    public static class Builder {

        private final Map<Integer, Texture> textures = LazyMap.newHashMap();
        private Dimension squareSize;
        private Texture atlasTexture;

        public Builder setAtlasTexture(Texture atlasTexture) {
            Objects.requireNonNull(atlasTexture, "atlasTexture");
            this.atlasTexture = atlasTexture;
            return this;
        }

        public Builder setSquareSize(Dimension squareSize) {
            Preconditions.checkArgument(squareSize.width > 0 && squareSize.height > 0, "squareSize; %s", squareSize);
            this.squareSize = new Dimension(squareSize);
            return this;
        }

        public Builder addTexture(int id, Rectangle region) {
            Preconditions.checkState(!textures.containsKey(id), "id '" + id + "' already in use");
            textures.put(id, new Texture(atlasTexture, region));
            return this;
        }

        public TextureAtlas build() {
            return new TextureAtlas(this);
        }
    }
}
