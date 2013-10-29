package all.your.awt;

import com.google.common.base.Preconditions;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class BufferedImages {

    public static BufferedImage newSquareGrid(int squareWidth, int squareHeight, Color[][] squares) {
        Preconditions.checkArgument(squareWidth > 0, "squareWidth <= 0");
        Preconditions.checkArgument(squareHeight > 0, "squareHeight <= 0");
        Objects.requireNonNull(squares, "squares");
        BufferedImage image = new BufferedImage(squareWidth * squares[0].length, squareHeight * squares.length,
                                                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        for (int row = 0; row < squares.length; ++row) {
            for (int col = 0; col < squares[row].length; ++col) {
                g.setColor(squares[row][col]);
                g.fillRect(squareWidth * col, squareHeight * row, squareWidth, squareHeight);
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
}
