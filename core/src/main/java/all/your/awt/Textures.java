package all.your.awt;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class Textures {

    public static Texture newFilled(Dimension imageSize, Color fillColor) {
        BufferedImage image = new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(fillColor);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.dispose();
        return new Texture(image);
    }

    public static Texture newSquareGrid(Color[][] squares) {
        Objects.requireNonNull(squares, "squares");
        BufferedImage image = new BufferedImage(squares[0].length, squares.length, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        for (int y = 0; y < squares.length; ++y) {
            for (int x = 0; x < squares[y].length; ++x) {
                g.setColor(squares[y][x]);
                g.fillRect(x, y, 1, 1);
            }
        }
        g.dispose();
        return new Texture(image);
    }

    public static Texture fromFile(String fileName) throws IOException {
        Objects.requireNonNull(fileName, "fileName");
        InputStream in = Textures.class.getResourceAsStream(fileName);
        if (in == null) {
            throw new FileNotFoundException(fileName);
        }
        return new Texture(ImageIO.read(in));
    }
}