package all.your.base.graphics;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class SurfaceBuffers {

    public static SurfaceBuffer newDoubleBuffer(int width, int height) {
        return new CircularSurfaceBuffer(new Surface(width, height),
                                         new Surface(width, height));
    }
}
