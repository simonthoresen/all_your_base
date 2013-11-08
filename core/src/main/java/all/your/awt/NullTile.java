package all.your.awt;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
enum NullTile implements Tile {

    INSTANCE;

    @Override
    public Texture getTexture() {
        return NullTexture.INSTANCE;
    }
}
