package all.your.awt;

import all.your.util.Preconditions;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class SquareGridTextureAtlas implements TextureAtlas {

    private final Map<Integer, AtlasTexture> textures = new HashMap<>();
    private final BufferedImage image;
    private final int squareWidth, squareHeight;

    public SquareGridTextureAtlas(BufferedImage image, int squareWidth, int squareHeight) {
        Objects.requireNonNull(image, "image");
        Preconditions.checkArgument(squareWidth > 0, "squareWidth must be positive; %s", squareWidth);
        Preconditions.checkArgument(squareHeight > 0, "squareHeight must be positive; %s", squareHeight);
        this.image = image;
        this.squareWidth = squareWidth;
        this.squareHeight = squareHeight;
    }

    @Override
    public AtlasTexture getTexture(int id) {
        return getOrCreateTexture(id, (id >> 16) & 0xFFFF, id & 0xFFFF);
    }

    public AtlasTexture getTexture(int row, int col) {
        return getOrCreateTexture((row << 16) | (col & 0xFFFF), row, col);
    }

    private AtlasTexture getOrCreateTexture(int id, int row, int col) {
        AtlasTexture texture = textures.get(id);
        if (texture == null) {
            texture = new AtlasTexture(image, col * squareWidth, row * squareHeight, squareWidth, squareHeight);
            textures.put(id, texture);
        }
        return texture;
    }
}
