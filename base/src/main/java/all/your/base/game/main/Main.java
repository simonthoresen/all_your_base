package all.your.base.game.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class Main {

    private final static Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException {
        InputStream in = Main.class.getResourceAsStream("/build.properties");
        if (in == null) {
            throw new FileNotFoundException("/build.properties");
        }
        Properties props = new Properties();
        props.load(in);
        System.out.println(">>> all_your_base " + props.getProperty("project.version"));
        while (!Thread.currentThread().isInterrupted()) {
            Thread.sleep(1000);
            log.info("nanoTime : " + System.nanoTime());
        }
    }
}
