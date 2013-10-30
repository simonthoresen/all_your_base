package all.your.awt;

import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class SquareGridTextureAtlasTest {

    @Test
    public void requireNothing() {
        BufferedImage atlasImage = BufferedImages.newSquareGrid(2, 2, new Color[][] {
                { Color.RED, Color.YELLOW, Color.RED, Color.YELLOW },
                { Color.YELLOW, Color.RED, Color.YELLOW, Color.RED },
                { Color.RED, Color.YELLOW, Color.RED, Color.YELLOW },
                { Color.YELLOW, Color.RED, Color.YELLOW, Color.RED },
        });
        SquareGridTextureAtlas atlas = new SquareGridTextureAtlas(atlasImage, 4, 4);
    }
}
