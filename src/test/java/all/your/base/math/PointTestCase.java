package all.your.base.math;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class PointTestCase {

    @Test
    public void requireThatHashCodeAndEqualsAreImplemented() {
        assertEquals(new Point(1, 2).hashCode(),
                     new Point(1, 2).hashCode());

        assertTrue(new Point(1, 2).equals(new Point(1, 2)));
        assertFalse(new Point(1, 2).equals(new Point(2, 2)));
        assertFalse(new Point(1, 2).equals(new Point(2, 3)));
        assertFalse(new Point(1, 2).equals(new Point(1, 3)));
    }

    @Test
    public void requireThatAddMethodWorks() {
        assertEquals(new Point(3, 4), new Point(1, 1).add(new Point(2, 3)));
        assertEquals(new Point(18, 29), new Point(5, 8).add(new Point(13, 21)));
    }

    @Test
    public void requireThatSubMethodWorks() {
        assertEquals(new Point(-1, -2), new Point(1, 1).sub(new Point(2, 3)));
        assertEquals(new Point(-8, -13), new Point(5, 8).sub(new Point(13, 21)));
    }

    @Test
    public void requireThatDistanceToRectangleWorks() {
        Rectangle rect = new Rectangle(5, 10, 10, 10);
        assertEquals(Math.sqrt(2 * 2 + 2 * 2), new Point(3, 8).distanceTo(rect), 1E-6);
        assertEquals(Math.sqrt(2 * 2), new Point(10, 8).distanceTo(rect), 1E-6);
        assertEquals(Math.sqrt(2 * 2 + 2 * 2), new Point(17, 8).distanceTo(rect), 1E-6);
        assertEquals(Math.sqrt(2 * 2), new Point(3, 15).distanceTo(rect), 1E-6);
        assertEquals(0, new Point(10, 15).distanceTo(rect), 1E-6);
        assertEquals(Math.sqrt(2 * 2), new Point(17, 15).distanceTo(rect), 1E-6);
        assertEquals(Math.sqrt(2 * 2 + 2 * 2), new Point(3, 22).distanceTo(rect), 1E-6);
        assertEquals(Math.sqrt(2 * 2), new Point(10, 22).distanceTo(rect), 1E-6);
        assertEquals(Math.sqrt(2 * 2 + 2 * 2), new Point(17, 22).distanceTo(rect), 1E-6);
    }
}
