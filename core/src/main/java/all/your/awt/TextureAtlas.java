package all.your.awt;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public interface TextureAtlas {

    // TODO: make this a class. collapse FreeForm and SquareGrid into this
    // TODO: accessor getTexture(Point p) gets you a square grid version
    // TODO: accessor getTexture(int id) gets you a free form version

    public Texture getTexture(int id);
}
