package all.your.base.math;

import java.util.BitSet;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class FovMap extends Rectangle {

    private final BitSet bits;

    private FovMap(Rectangle rect, BitSet bits) {
        super(rect);
        this.bits = bits;
    }

    public boolean canSee(Point p) {
        return canSee(p.x(), p.y());
    }

    public boolean canSee(int x, int y) {
        int tx = x - x();
        if (tx < 0 || tx >= width()) {
            return false;
        }
        int ty = y - y();
        if (ty < 0 || ty >= height()) {
            return false;
        }
        return bits.get(tx + ty * width());
    }

    public static FovMap newInstance(Board board, Rectangle mapRect, Point eyePos) {
        if (!mapRect.contains(eyePos)) {
            throw new IllegalArgumentException("pos not in rect");
        }
        BitBoard bitBoard = new BitBoard(board, mapRect);
        PrecisePermissive.calculateFov(bitBoard, eyePos, mapRect);
        return new FovMap(mapRect, bitBoard.bits);
    }

    private static class BitBoard extends Rectangle implements FovBoard {

        final Board board;
        final BitSet bits;

        public BitBoard(Board board, Rectangle rect) {
            super(rect);
            this.board = board;
            this.bits = new BitSet(rect.width() * rect.height());
        }

        @Override
        public boolean blocksVision(Point p) {
            return board.getTileAt(p.y(), p.x()).isOpaque();
        }

        @Override
        public void notifyVisible(Point p) {
            bits.set((p.x() - x()) + (p.y() - y()) * width());
        }
    }
}
