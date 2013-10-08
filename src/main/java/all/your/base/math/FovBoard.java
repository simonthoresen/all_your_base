package all.your.base.math;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public interface FovBoard {

    public boolean contains(Point p);

    public boolean blocksVision(Point p);

    public void notifyVisible(Point p);
}
