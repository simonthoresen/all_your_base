package all.your.base.game.math;

import all.your.base.game.geometry.Line;
import all.your.base.game.geometry.Point;
import all.your.base.game.geometry.Rectangle;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
final class PrecisePermissive {

    private final static Point[] QUADRANTS = {
            new Point(1, 1),
            new Point(-1, 1),
            new Point(-1, -1),
            new Point(1, -1)
    };

    public static void calculateFov(FovBoard board, Point eyePos, Rectangle rect) {
        new PrecisePermissive().visitFieldOfView(board, eyePos, rect);
    }

    public void visitFieldOfView(FovBoard board, Point eyePos, Rectangle viewRect) {
        PermissiveMask mask = new PermissiveMask();
        mask.east = viewRect.x() + viewRect.width() - eyePos.x();
        mask.south = eyePos.y() - viewRect.y();
        mask.north = viewRect.y() + viewRect.height() - eyePos.y();
        mask.west = eyePos.x() - viewRect.x();
        mask.mask = null;
        mask.board = board;
        permissiveFov(eyePos.x(), eyePos.y(), mask);
    }

    private void calculateFovQuadrant(FovState state) {
        LinkedList<Bump> steepBumps = new LinkedList<>();
        LinkedList<Bump> shallowBumps = new LinkedList<>();
        // activeFields is sorted from shallow-to-steep.
        LinkedList<Field> activeFields = new LinkedList<>();
        activeFields.addLast(new Field(new Line(new Point(1, 0), new Point(0, state.extent.y())),
                                       new Line(new Point(0, 1), new Point(state.extent.x(), 0))));
        Point dest = new Point(0, 0);

        // Visit the source square exactly once (in quadrant 1).
        if (state.quadrant.x() == 1 && state.quadrant.y() == 1) {
            actIsBlocked(state, dest);
        }

        MyIterator<Field> currentField = new MyIterator<>(activeFields.listIterator());
        // For each square outline
        for (int i = 1, maxI = state.extent.x() + state.extent.y(); i <= maxI && !activeFields.isEmpty(); ++i) {
            // Visit the nodes in the outline
            for (int j = Math.max(0, i - state.extent.x()), maxJ = Math.min(i, state.extent.y());
                 j <= maxJ && !currentField.isAtEnd(); ++j) {
                dest = new Point(i - j, j);
                visitSquare(state, dest, currentField, steepBumps, shallowBumps);
            }
            currentField = new MyIterator<>(activeFields.listIterator());
        }
    }

    private void visitSquare(FovState state, Point dest,
                             MyIterator<Field> currentField, LinkedList<Bump> steepBumps,
                             LinkedList<Bump> shallowBumps) {
        // The top-left and bottom-right corners of the destination square.
        Point topLeft = dest.add(0, 1);
        Point bottomRight = dest.add(1, 0);

        while (!currentField.isAtEnd() && currentField.getCurrent().steep.isBelowOrContains(bottomRight)) {
            // case ABOVE
            // The square is in case 'above'. This means that it is ignored for the currentField. But the steeper fields
            // might need it.
            currentField.gotoNext();
        }
        if (currentField.isAtEnd()) {
            // The square was in case 'above' for all fields. This means that we no longer care about it or any squares
            // in its diagonal rank.
            return;
        }

        // Now we check for other cases.
        if (currentField.getCurrent().shallow.isAboveOrContains(topLeft)) {
            // case BELOW
            // The shallow line is above the extremity of the square, so that square is ignored.
            return;
        }
        // The square is between the lines in some way. This means that we need to visit it and determine whether it is
        // blocked.
        boolean isBlocked = actIsBlocked(state, dest);
        if (!isBlocked) {
            // We don't care what case might be left, because this square does not obstruct.
            return;
        }

        if (currentField.getCurrent().shallow.isAbove(bottomRight) &&
            currentField.getCurrent().steep.isBelow(topLeft)) {
            // case BLOCKING
            // Both lines intersect the square. This current field has ended.
            currentField.removeCurrent();
        } else if (currentField.getCurrent().shallow.isAbove(bottomRight)) {
            // case SHALLOW BUMP
            // The square intersects only the shallow line.
            addShallowBump(topLeft, currentField.getCurrent(), shallowBumps);
            checkField(currentField);
        } else if (currentField.getCurrent().steep.isBelow(topLeft)) {
            // case STEEP BUMP
            // The square intersects only the steep line.
            addSteepBump(bottomRight, currentField.getCurrent(), steepBumps);
            checkField(currentField);
        } else {
            // case BETWEEN
            // The square intersects neither line. We need to split into two fields.
            Field steeperField = currentField.getCurrent();
            Field shallowerField = new Field(currentField.getCurrent());
            currentField.insertBeforeCurrent(shallowerField);
            addSteepBump(bottomRight, shallowerField, steepBumps);
            currentField.gotoPrevious();
            if (!checkField(currentField)) {
                // did not remove, point to the original element
                currentField.gotoNext();
            }
            addShallowBump(topLeft, steeperField, shallowBumps);
            checkField(currentField);
        }
    }

    private boolean checkField(MyIterator<Field> currentField) {
        // If the two slopes are colinear, and if they pass through either extremity, remove the field of view.
        Field currFld = currentField.getCurrent();
        boolean ret = false;

        if (currFld.shallow.doesContain(currFld.steep.p1()) &&
            currFld.shallow.doesContain(currFld.steep.p2()) &&
            (currFld.shallow.doesContain(new Point(0, 1)) ||
             currFld.shallow.doesContain(new Point(1, 0)))) {
            currentField.removeCurrent();
            ret = true;
        }
        return ret;
    }

    private void addShallowBump(final Point point, Field currFld, LinkedList<Bump> shallowBumps) {
        // First, the far point of shallow is set to the new point.
        currFld.shallow = new Line(currFld.shallow.p1(), point);
        // Second, we need to add the new bump to the shallow bump list for
        // future steep bump handling.
        shallowBumps.addLast(new Bump());
        shallowBumps.getLast().location = point;
        shallowBumps.getLast().parent = currFld.shallowBump;
        currFld.shallowBump = shallowBumps.getLast();

        // Now we have too look through the list of steep bumps and see if any of them are below the line. If there are,
        // we need to replace near point too.
        Bump currentBump = currFld.steepBump;
        while (currentBump != null) {
            if (currFld.shallow.isAbove(currentBump.location)) {
                currFld.shallow = new Line(currentBump.location, currFld.shallow.p2());
            }
            currentBump = currentBump.parent;
        }
    }

    private void addSteepBump(final Point point, Field currFld, LinkedList<Bump> steepBumps) {
        currFld.steep = new Line(currFld.steep.p1(), point);
        steepBumps.addLast(new Bump());
        steepBumps.getLast().location = point;
        steepBumps.getLast().parent = currFld.steepBump;
        currFld.steepBump = steepBumps.getLast();

        // Now look through the list of shallow bumps and see if any of them are below the line.
        Bump currentBump = currFld.shallowBump;
        while (currentBump != null) {
            if (currFld.steep.isBelow(currentBump.location)) {
                currFld.steep = new Line(currentBump.location, currFld.steep.p2());
            }
            currentBump = currentBump.parent;
        }
    }

    private boolean actIsBlocked(final FovState state, final Point pos) {
        Point adjustedPos = new Point(pos.x() * state.quadrant.x() + state.source.x(),
                                      pos.y() * state.quadrant.y() + state.source.y());

        if (!state.board.contains(adjustedPos)) {
            return false; // we are getting outside the board
        }

        // To ensure all squares are visited before checked (so that we can decide obstacling at visit time, eg walls
        // destroyed by explosion), visit axes 1,2 only in Q1, 3 in Q2, 4 in Q3
        if ((state.isLos) || // In LOS calculation all visits allowed
            (state.quadrantIndex == 0) || // can visit anything from Q1
            (state.quadrantIndex == 1 && pos.x() != 0) || // Q2 : no Y axis
            (state.quadrantIndex == 2 && pos.y() != 0) || // Q3 : no X axis
            (state.quadrantIndex == 3 && pos.x() != 0 && pos.y() != 0)) // Q4 : no X or Y axis
        {
            state.board.notifyVisible(adjustedPos);
        }
        return state.board.blocksVision(adjustedPos);
    }

    private void permissiveFov(int sourceX, int sourceY, PermissiveMask mask) {
        FovState state = new FovState();
        state.source = new Point(sourceX, sourceY);
        state.mask = mask;
        state.board = mask.board;

        Point extents[] = { new Point(mask.east, mask.north),
                            new Point(mask.west, mask.north),
                            new Point(mask.west, mask.south),
                            new Point(mask.east, mask.south) };
        int quadrantIndex = 0;
        for (; quadrantIndex < QUADRANTS.length; ++quadrantIndex) {
            state.quadrant = QUADRANTS[quadrantIndex];
            state.extent = extents[quadrantIndex];
            state.quadrantIndex = quadrantIndex;
            calculateFovQuadrant(state);
        }
    }

    private static class PermissiveMask {

        int north;
        int south;
        int east;
        int west;
        int[] mask;
        FovBoard board;
    }

    private static class FovState {

        Point source;
        PermissiveMask mask;
        Point quadrant;
        Point extent;
        int quadrantIndex;
        FovBoard board;
        boolean isLos = false;
    }

    private static class Bump {

        Point location;
        Bump parent = null;
    }

    private static class Field {

        Line steep;
        Line shallow;
        Bump steepBump;
        Bump shallowBump;

        Field(Field field) {
            steep = field.steep;
            shallow = field.shallow;
            steepBump = field.steepBump;
            shallowBump = field.shallowBump;
        }

        Field(Line steep, Line shallow) {
            this.steep = steep;
            this.shallow = shallow;
        }
    }

    private static class MyIterator<T> {

        private final ListIterator<T> it;
        private T curr;
        private boolean atEnd = false;

        public MyIterator(ListIterator<T> it) {
            super();
            this.it = it;
            if (it.hasNext()) {
                curr = it.next();
            } else {
                curr = null;
                atEnd = true;
            }
        }

        public final T getCurrent() {
            return curr;
        }

        public void gotoNext() {
            if (it.hasNext()) {
                curr = it.next();
            } else {
                atEnd = true;
                curr = null;
            }
        }

        public void gotoPrevious() {
            if (it.hasPrevious()) {
                curr = it.previous();
            }
        }

        public boolean isAtEnd() {
            return atEnd;
        }

        public void removeCurrent() {
            it.remove();
            gotoNext();
        }

        public void insertBeforeCurrent(T t) {
            it.previous();
            it.add(t);
        }
    }
}
