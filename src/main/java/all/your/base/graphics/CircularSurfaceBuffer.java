package all.your.base.graphics;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class CircularSurfaceBuffer implements SurfaceBuffer {

    private final Surface[] surfaces;
    private volatile byte writePos;

    public CircularSurfaceBuffer(Surface... surfaces) {
        this.surfaces = surfaces;
    }

    @Override
    public Surface peek() {
        return surfaces[(surfaces.length + writePos - 1) % surfaces.length];
    }

    @Override
    public Surface commit() {
        return surfaces[++writePos];
    }
}
