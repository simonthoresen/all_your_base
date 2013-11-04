package all.your.util;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class Preconditions {

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
