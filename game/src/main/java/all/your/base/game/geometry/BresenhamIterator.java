package all.your.base.game.geometry;

import java.util.Iterator;

final class BresenhamIterator implements Iterator<Point> {

    private final int dx;
    private final int dy;
    private final int xStep;
    private final int yStep;
    private final int numSteps;
    private int error;
    private int step;
    private int x;
    private int y;

    public BresenhamIterator(Point p1, Point p2) {
        x = p1.x();
        y = p1.y();

        dx = Math.abs(p2.x() - p1.x());
        dy = Math.abs(p2.y() - p1.y());

        xStep = dx >= 0 ? 1 : -1;
        yStep = dy >= 0 ? 1 : -1;

        error = dx > dy ? dx >> 1 : dy >> 1;

        step = 0;
        numSteps = Math.max(dx, dy) + 1;
    }

    @Override
    public boolean hasNext() {
        return step < numSteps;
    }

    @Override
    public Point next() {
        // now based on which delta is greater we can draw the line
        if (dx > dy) {
            // adjust the error term
            error += dy;

            // test if error has overflowed
            if (error >= dx) {
                error -= dx;

                // move to next line
                y += yStep;
            }

            // move to the next pixel
            x += xStep;
        } else {
            // adjust the error term
            error += dx;

            // test if error overflowed
            if (error >= dy) {
                error -= dy;

                // move to next line
                x += xStep;
            }

            // move to the next pixel
            y += yStep;
        }
        ++step;
        return new Point(x, y);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
