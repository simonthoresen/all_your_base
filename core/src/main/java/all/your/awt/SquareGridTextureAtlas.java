package all.your.awt;

import all.your.util.Preconditions;

import java.awt.Dimension;
import java.awt.Rectangle;
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
    private final Dimension squareSize;

    // TODO: replace image with texture
    public SquareGridTextureAtlas(BufferedImage image, Dimension squareSize) {
        Objects.requireNonNull(image, "image");
        Preconditions.checkArgument(squareSize.width > 0 && squareSize.height > 0, "squareSize; %s", squareSize);
        this.image = image;
        this.squareSize = new Dimension(squareSize);
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
            texture = new AtlasTexture(image, new Rectangle(squareSize.width * col, squareSize.height * row,
                                                            squareSize.width, squareSize.height));
            textures.put(id, texture);
        }
        return texture;
    }
}
