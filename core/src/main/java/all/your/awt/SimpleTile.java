package all.your.awt;

import java.util.Objects;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class SimpleTile implements Tile {

    private final Texture texture;

    public SimpleTile(Texture texture) {
        Objects.requireNonNull(texture, "texture");
        this.texture = texture;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
