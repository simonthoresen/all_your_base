package all.your.core.application;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class ApplicationExecutor {

    public static ListenableFuture<Object> run(final Application app) {
        return MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor()).submit(new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                app.run();
                return null;
            }
        });
    }
}
