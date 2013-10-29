package all.your.base.core.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class BufferedImages {

    public static BufferedImage fromFile(String fileName) throws IOException {
        Objects.requireNonNull(fileName, "fileName");
        InputStream in = all.your.base.game.tiled.TileSet.class.getResourceAsStream(fileName);
        if (in == null) {
            throw new FileNotFoundException(fileName);
        }
        return ImageIO.read(in);
    }
}
