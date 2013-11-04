package all.your.swing;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class ApplicationExecutor {

    public static Future<Void> run(final Application app) {
        return Executors.newSingleThreadExecutor().submit(new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                app.run();
                return null;
            }
        });
    }
}
