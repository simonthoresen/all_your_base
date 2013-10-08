package all.things.tiled.math;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class RectangleTestCase {

    @Test
    public void requireThatHashCodeAndEqualsAreImplemented() {
        assertEquals(new Rectangle(1, 2, 2, 2).hashCode(),
                     new Rectangle(1, 2, 2, 2).hashCode());

        Rectangle rect = new Rectangle(1, 2, 2, 2);
        assertTrue(rect.equals(new Rectangle(1, 2, 2, 2)));
        assertFalse(rect.equals(new Rectangle(3, 4, 1, 1)));
        assertFalse(rect.equals(new Rectangle(3, 4, 2, 2)));
        assertFalse(rect.equals(new Rectangle(1, 2, 1, 1)));
    }

    @Test
    public void requireThatContainsPointWorks() {
        Rectangle rect = new Rectangle(5, 10, 10, 10);
        assertFalse(rect.contains(new Point(3, 8)));
        assertFalse(rect.contains(new Point(10, 8)));
        assertFalse(rect.contains(new Point(17, 8)));
        assertFalse(rect.contains(new Point(3, 15)));
        assertTrue(rect.contains(new Point(10, 15)));
        assertFalse(rect.contains(new Point(17, 15)));
        assertFalse(rect.contains(new Point(3, 22)));
        assertFalse(rect.contains(new Point(10, 22)));
        assertFalse(rect.contains(new Point(17, 22)));
    }

    @Test
    public void requireThatContainsRectangleWorks() {
        Rectangle rect = new Rectangle(5, 10, 15, 20); // 5, 10 - 20, 30
        assertFalse(rect.contains(new Rectangle(0, 0, 5, 5)));
        assertFalse(rect.contains(new Rectangle(0, 0, 10, 10)));
        assertFalse(rect.contains(new Rectangle(0, 0, 5, 15)));
        assertFalse(rect.contains(new Rectangle(0, 0, 10, 15)));

        assertTrue(rect.contains(new Rectangle(10, 15, 5, 5)));
        assertTrue(rect.contains(new Rectangle(5, 10, 15, 20)));
        assertFalse(rect.contains(new Rectangle(0, 5, 25, 30)));

        assertFalse(rect.contains(new Rectangle(20, 30, 10, 10)));
        assertFalse(rect.contains(new Rectangle(20, 20, 10, 10)));
        assertFalse(rect.contains(new Rectangle(15, 30, 10, 10)));
        assertFalse(rect.contains(new Rectangle(15, 25, 10, 10)));
    }

    @Test
    public void requireThatIntersectWorks() {
        Rectangle rect = new Rectangle(5, 10, 15, 20); // 5, 10 - 20, 30
        assertFalse(rect.intersects(new Rectangle(0, 0, 5, 5)));
        assertFalse(rect.intersects(new Rectangle(0, 0, 10, 10)));
        assertFalse(rect.intersects(new Rectangle(0, 0, 5, 15)));
        assertTrue(rect.intersects(new Rectangle(0, 0, 10, 15)));

        assertTrue(rect.intersects(new Rectangle(10, 15, 5, 5)));
        assertTrue(rect.intersects(new Rectangle(5, 10, 15, 20)));
        assertTrue(rect.intersects(new Rectangle(0, 5, 25, 30)));

        assertFalse(rect.intersects(new Rectangle(20, 30, 10, 10)));
        assertFalse(rect.intersects(new Rectangle(20, 20, 10, 10)));
        assertFalse(rect.intersects(new Rectangle(15, 30, 10, 10)));
        assertTrue(rect.intersects(new Rectangle(15, 25, 10, 10)));
    }

    @Test
    public void requireThatIntersectionWorks() {
        Rectangle rect = new Rectangle(5, 10, 15, 20); // 5, 10 - 20, 30
        assertNull(rect.intersection(new Rectangle(0, 0, 5, 5)));
        assertNull(rect.intersection(new Rectangle(0, 0, 10, 10)));
        assertNull(rect.intersection(new Rectangle(0, 0, 5, 15)));
        assertEquals(new Rectangle(5, 10, 5, 5), rect.intersection(new Rectangle(0, 0, 10, 15)));

        assertEquals(new Rectangle(10, 15, 5, 5), rect.intersection(new Rectangle(10, 15, 5, 5)));
        assertEquals(new Rectangle(5, 10, 15, 20), rect.intersection(new Rectangle(5, 10, 15, 20)));
        assertEquals(new Rectangle(5, 10, 15, 20), rect.intersection(new Rectangle(0, 5, 25, 30)));

        assertNull(rect.intersection(new Rectangle(20, 30, 10, 10)));
        assertNull(rect.intersection(new Rectangle(20, 20, 10, 10)));
        assertNull(rect.intersection(new Rectangle(15, 30, 10, 10)));
        assertEquals(new Rectangle(15, 25, 5, 5), rect.intersection(new Rectangle(15, 25, 10, 10)));
    }

    @Test
    public void requireThatGrowToWorks() {
        Rectangle rect = new Rectangle(1, 1, 2, 3);
        assertEquals(new Rectangle(1, 1, 2, 3), rect.growTo(new Point(1, 1)));
        assertEquals(new Rectangle(1, 1, 2, 3), rect.growTo(new Point(2, 1)));
        assertEquals(new Rectangle(1, 1, 3, 3), rect.growTo(new Point(3, 1)));
        assertEquals(new Rectangle(-6, 1, 8, 9), rect.growTo(new Point(-6, 9)));
    }
}
