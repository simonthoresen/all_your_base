package all.your.swing;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
class SurfaceBuffers {

    public static SurfaceBuffer newDoubleBuffer(int width, int height) {
        return new CircularSurfaceBuffer(new Surface(width, height),
                                         new Surface(width, height));
    }
}
