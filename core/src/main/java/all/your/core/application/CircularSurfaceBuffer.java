package all.your.core.application;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
class CircularSurfaceBuffer implements SurfaceBuffer {

    private final Surface[] surfaces;
    private volatile byte writePos;

    public CircularSurfaceBuffer(Surface... surfaces) {
        this.surfaces = surfaces;
    }

    @Override
    public Surface peek() {
        int readPos = (surfaces.length + writePos - 1) % surfaces.length;
        return surfaces[readPos];
    }

    @Override
    public Surface commit() {
        writePos = (byte)((writePos + 1) % surfaces.length);
        return surfaces[writePos];
    }
}
