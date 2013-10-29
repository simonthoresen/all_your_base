package all.your.core.application;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
interface SurfaceBuffer {

    public Surface peek();

    public Surface commit();
}
