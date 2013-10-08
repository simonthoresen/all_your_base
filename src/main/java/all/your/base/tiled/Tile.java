package all.your.base.tiled;

import java.awt.Color;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 * @version $Id$
 */
public class Tile {

    private final Color color;
    private final boolean canEnter;
    private final boolean isOpaque;
    private char symbol;

    private Tile(Builder builder) {
        color = builder.color;
        canEnter = builder.canEnter;
        isOpaque = builder.isOpaque;
        symbol = builder.symbol;
    }

    public Color color() {
        return color;
    }

    public boolean isOpaque() {
        return isOpaque;
    }

    public boolean canEnter() {
        return canEnter;
    }

    public char getSymbol() {
        return symbol;
    }

    public static class Builder {

        private Color color = Color.WHITE;
        private boolean canEnter = true;
        private boolean isOpaque = true;
        public char symbol = ' ';

        public Builder setColor(Color color) {
            this.color = color;
            return this;
        }

        public Builder setCanEnter(boolean canEnter) {
            this.canEnter = canEnter;
            return this;
        }

        public Builder setIsOpaque(boolean isOpaque) {
            this.isOpaque = isOpaque;
            return this;
        }

        public Builder setSymbol(char symbol) {
            this.symbol = symbol;
            return this;
        }

        public Tile build() {
            return new Tile(this);
        }
    }
}
