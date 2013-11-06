package all.your.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class PreconditionsTest {

    @Test
    public void requireThatIndexMustBePositive() {
        try {
            Preconditions.checkIndex(-1, 69);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("-1", e.getMessage());
        }
        try {
            Preconditions.checkIndex(-1, 69, "foo");
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("foo", e.getMessage());
        }
    }

    @Test
    public void requireThatIndexMustBeLessThanSize() {
        Preconditions.checkIndex(6, 9);
        Preconditions.checkIndex(7, 9);
        Preconditions.checkIndex(8, 9);
        try {
            Preconditions.checkIndex(9, 9);
        } catch (IndexOutOfBoundsException e) {
            assertEquals("9", e.getMessage());
        }
    }

    @Test
    public void requireThatArgumentMustBeTrue() {
        Preconditions.checkArgument(true, "foo");
        try {
            Preconditions.checkArgument(false, "foo");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("foo", e.getMessage());
        }
    }

    @Test
    public void requireThatStateMustBeTrue() {
        Preconditions.checkState(true, "foo");
        try {
            Preconditions.checkState(false, "foo");
            fail();
        } catch (IllegalStateException e) {
            assertEquals("foo", e.getMessage());
        }
    }
}
