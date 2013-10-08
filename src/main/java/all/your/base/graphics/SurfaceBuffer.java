package all.your.base.graphics;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public interface SurfaceBuffer {

    public Surface peek();

    public Surface commit();
}
