package all.your.awt;

import all.your.util.Preconditions;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class SquareGridTextureAtlas implements TextureAtlas {

    private final Map<Integer, Texture> textures = new HashMap<>();
    private final Texture atlas;
    private final Dimension squareSize;

    public SquareGridTextureAtlas(Texture atlas, Dimension squareSize) {
        Objects.requireNonNull(atlas, "atlas");
        Preconditions.checkArgument(squareSize.width > 0 && squareSize.height > 0, "squareSize; %s", squareSize);
        this.atlas = atlas;
        this.squareSize = new Dimension(squareSize);
    }

    @Override
    public Texture getTexture(int id) {
        return getOrCreateTexture(id, (id >> 16) & 0xFFFF, id & 0xFFFF);
    }

    public Texture getTexture(int row, int col) {
        return getOrCreateTexture((row << 16) | (col & 0xFFFF), row, col);
    }

    private Texture getOrCreateTexture(int id, int row, int col) {
        Texture texture = textures.get(id);
        if (texture == null) {
            texture = new Texture(atlas, new Rectangle(squareSize.width * col, squareSize.height * row,
                                                       squareSize.width, squareSize.height));
            textures.put(id, texture);
        }
        return texture;
    }
}
