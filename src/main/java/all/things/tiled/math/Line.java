package all.things.tiled.math;

import java.util.Iterator;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class Line implements Iterable<Point> {

    private final Point p1;
    private final Point p2;

    public Line(int x1, int y1, int x2, int y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Point p1() {
        return p1;
    }

    public Point p2() {
        return p2;
    }

    public double length() {
        return p2.distanceTo(p1);
    }

    public boolean isBelow(Point p) {
        return relativeSlope(p) > 0;
    }

    public boolean isBelowOrContains(Point p) {
        return relativeSlope(p) >= 0;
    }

    public boolean isAbove(Point p) {
        return relativeSlope(p) < 0;
    }

    public boolean isAboveOrContains(Point p) {
        return relativeSlope(p) <= 0;
    }

    public boolean doesContain(Point p) {
        return relativeSlope(p) == 0;
    }

    // negative if the line is above the p.
    // positive if the line is below the p.
    // 0 if the line is on the p.
    private int relativeSlope(Point p) {
        return (p2.y() - p1.y()) * (p2.x() - p.x()) - (p2.y() - p.y()) * (p2.x() - p1.x());
    }

    @Override
    public String toString() {
        return "(" + p1 + " -> " + p2 + ")";
    }

    @Override
    public Iterator<Point> iterator() {
        return new BresenhamIterator(p1, p2);
    }
}
