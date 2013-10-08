package all.your.base.math;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class Point {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public double distanceTo(Point p) {
        return Math.sqrt((p.x - x) * (p.x - x) + (p.y - y) * (p.y - y));
    }

    public double distanceTo(Rectangle rect) {
        int dx, dy;
        if (x < rect.x()) {
            dx = rect.x() - x;
        } else if (x <= rect.x() + rect.width()) {
            dx = 0;
        } else {
            dx = x - (rect.x() + rect.width());
        }
        if (y < rect.y()) {
            dy = rect.y() - y;
        } else if (y <= rect.y() + rect.height()) {
            dy = 0;
        } else {
            dy = y - (rect.y() + rect.height());
        }
        if (dx == 0 && dy == 0) {
            return 0;
        }
        return Math.sqrt(dx * dx + dy * dy);
    }

    public Point add(Point p) {
        return add(p.x, p.y);
    }

    public Point add(int x, int y) {
        return new Point(this.x + x, this.y + y);
    }

    public Point sub(Point p) {
        return sub(p.x, p.y);
    }

    public Point sub(int x, int y) {
        return new Point(this.x - x, this.y - y);
    }

    @Override
    public int hashCode() {
        return ((x & 0xFFFF) << 16) + (y & 0xFFFF);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point)) {
            return false;
        }
        Point rhs = (Point)obj;
        if (x != rhs.x) {
            return false;
        }
        if (y != rhs.y) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
