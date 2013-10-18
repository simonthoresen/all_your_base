package all.your.base.application;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
enum SystemTimer implements Timer {

    INSTANCE;

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
