package all.your.base.tiled;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public interface TileSheet {

    public int getTileAt(int x, int y);

    public int getWidth();

    public int getHeight();
}
