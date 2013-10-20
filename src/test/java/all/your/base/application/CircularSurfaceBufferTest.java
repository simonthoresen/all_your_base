package all.your.base.application;

import org.junit.Test;

import static org.junit.Assert.assertSame;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class CircularSurfaceBufferTest {

    @Test
    public void requireThatCommitReturnsSurfacesInCircle() {
        Surface foo = newSurface("foo");
        Surface bar = newSurface("bar");
        Surface baz = newSurface("baz");
        SurfaceBuffer buf = new CircularSurfaceBuffer(foo, bar, baz);
        assertSame(bar, buf.commit());
        assertSame(baz, buf.commit());
        assertSame(foo, buf.commit());
        assertSame(bar, buf.commit());
        assertSame(baz, buf.commit());
        assertSame(foo, buf.commit());
    }

    @Test
    public void requireThatPeekDoesNotMoveReadPos() {
        Surface foo = newSurface("foo");
        Surface bar = newSurface("bar");
        SurfaceBuffer buf = new CircularSurfaceBuffer(foo, bar);
        assertSame(bar, buf.peek());
        assertSame(bar, buf.peek());
    }

    @Test
    public void requireThatPeekReturnsLastCommit() {
        Surface foo = newSurface("foo");
        Surface bar = newSurface("bar");
        Surface baz = newSurface("baz");
        SurfaceBuffer buf = new CircularSurfaceBuffer(foo, bar, baz);
        assertSame(bar, buf.commit());
        assertSame(foo, buf.peek());
        assertSame(baz, buf.commit());
        assertSame(bar, buf.peek());
        assertSame(foo, buf.commit());
        assertSame(baz, buf.peek());
    }

    private static Surface newSurface(final String name) {
        return new Surface(2, 2) {

            @Override
            public String toString() {
                return name;
            }
        };
    }
}
