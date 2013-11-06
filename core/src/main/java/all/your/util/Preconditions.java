package all.your.util;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class Preconditions {

    public static void checkIndex(int idx, int size) {
        checkIndex(idx, size, String.valueOf(idx));
    }

    public static void checkIndex(int idx, int size, String msg, Object... args) {
        if (idx < 0 || idx >= size) {
            throw new IndexOutOfBoundsException(String.format(msg, args));
        }
    }

    public static void checkArgument(boolean exp, String msg, Object... args) {
        if (!exp) {
            throw new IllegalArgumentException(String.format(msg, args));
        }
    }

    public static void checkState(boolean exp, String msg, Object... args) {
        if (!exp) {
            throw new IllegalStateException(String.format(msg, args));
        }
    }
}
