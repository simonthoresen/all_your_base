package all.your.util.concurrent;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public enum SystemTimer implements Timer {

    INSTANCE;

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
