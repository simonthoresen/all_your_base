package all.your.base.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class Main {

    public static void main(String[] args) throws IOException {
        InputStream in = Main.class.getResourceAsStream("/build.properties");
        if (in == null) {
            throw new FileNotFoundException("/build.properties");
        }
        Properties props = new Properties();
        props.load(in);
        System.out.println(">>> all_your_base " + props.getProperty("project.version"));
    }
}
