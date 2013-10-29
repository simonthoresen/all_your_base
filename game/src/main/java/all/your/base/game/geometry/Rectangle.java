package all.your.base.game.geometry;

import com.google.common.base.Preconditions;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class Rectangle {

    private final int x, y, width, height;

    public Rectangle(Rectangle rect) {
        this(rect.x, rect.y, rect.width, rect.height);
    }

    public Rectangle(int x, int y, int width, int height) {
        Preconditions.checkArgument(width >= 0, "width < 0");
        Preconditions.checkArgument(height >= 0, "height < 0");
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public Rectangle growTo(Point p) {
        return growTo(p.x(), p.y());
    }

    public Rectangle growTo(int x, int y) {
        if (x >= this.x() && x < this.x + width &&
            y >= this.y() && y < this.y + height) {
            return this;
        }
        return new Rectangle(Math.min(x, this.x), Math.min(y, this.y),
                             Math.max(Math.abs(x - this.x) + 1, width),
                             Math.max(Math.abs(y - this.y) + 1, height));
    }

    public boolean contains(Point p) {
        return contains(p.x(), p.y());
    }

    public boolean contains(int x, int y) {
        return x >= this.x && x <= this.x + this.width &&
               y >= this.y && y <= this.y + this.height;
    }

    public boolean contains(Rectangle rect) {
        return contains(rect.x(), rect.y(), rect.width(), rect.height());
    }

    public boolean contains(int x, int y, int width, int height) {
        return x >= this.x && x + width <= this.x + this.width &&
               y >= this.y && y + height <= this.y + this.height;
    }

    public boolean intersects(Rectangle rect) {
        return intersects(rect.x, rect.y, rect.width, rect.height);
    }

    public boolean intersects(int x, int y, int width, int height) {
        return x + width > this.x && y + height > this.y &&
               this.x + this.width > x && this.y + this.height > y;
    }

    public Rectangle intersection(Rectangle rect) {
        return intersection(rect.x, rect.y, rect.width, rect.height);
    }

    public Rectangle intersection(int x, int y, int width, int height) {
        if (!intersects(x, y, width, height)) {
            return null;
        }
        int rx = Math.max(x, this.x);
        int ry = Math.max(y, this.y);
        int rw = Math.min(x + width, this.x + this.width) - rx;
        int rh = Math.min(y + height, this.y + this.height) - ry;
        return new Rectangle(rx, ry, rw, rh);
    }

    @Override
    public int hashCode() {
        return ((x & 0xFF) << 24) | ((y & 0xFF) << 16) | ((width & 0xFF) << 8) | (height & 0xFF);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Rectangle)) {
            return false;
        }
        Rectangle rhs = (Rectangle)obj;
        if (x != rhs.x) {
            return false;
        }
        if (y != rhs.y) {
            return false;
        }
        if (width != rhs.width) {
            return false;
        }
        if (height != rhs.height) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + width + ", " + height + ")";
    }
}
