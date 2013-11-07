package all.your.awt;

import all.your.util.Preconditions;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class BufferedImages {

    public static BufferedImage newFilled(Dimension imageSize, Color fillColor) {
        return newSquareGrid(imageSize, new Color[][] { { fillColor } });
    }

    public static BufferedImage newSquareGrid(Dimension squareSize, Color[][] squares) {
        Preconditions.checkArgument(squareSize.width > 0 && squareSize.height > 0, "squareSize; %s", squareSize);
        Objects.requireNonNull(squares, "squares");
        BufferedImage image = new BufferedImage(squareSize.width * squares[0].length,
                                                squareSize.height * squares.length,
                                                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        for (int y = 0; y < squares.length; ++y) {
            for (int x = 0; x < squares[y].length; ++x) {
                g.setColor(squares[y][x]);
                g.fillRect(squareSize.width * x, squareSize.height * y, squareSize.width, squareSize.height);
            }
        }
        g.dispose();
        return image;
    }

    public static BufferedImage fromFile(String fileName) throws IOException {
        Objects.requireNonNull(fileName, "fileName");
        InputStream in = BufferedImages.class.getResourceAsStream(fileName);
        if (in == null) {
            throw new FileNotFoundException(fileName);
        }
        return ImageIO.read(in);
    }

    public static Rectangle getBounds(BufferedImage image, Rectangle out) {
        out.x = 0;
        out.y = 0;
        out.width = image.getWidth();
        out.height = image.getHeight();
        return out;
    }
}
