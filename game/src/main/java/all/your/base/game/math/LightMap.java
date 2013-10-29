package all.your.base.game.math;

import all.your.base.game.geometry.Point;
import all.your.base.game.geometry.Rectangle;
import com.google.common.base.Preconditions;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class LightMap extends Rectangle {

    private final static int FLAG_VISIBLE = 0x01 << 24;
    private final static int MASK_RGB = 0xFFFFFF;
    private final int[] lights;

    private LightMap(Rectangle rect, int[] lights) {
        super(rect);
        this.lights = lights;
    }

    public boolean canSee(Point p) {
        return canSee(p.x(), p.y());
    }

    public boolean canSee(int x, int y) {
        return (lights[(x - x()) + (y - y()) * width()] & FLAG_VISIBLE) != 0;
    }

    public int getColor(Point p) {
        return getColor(p.x(), p.y());
    }

    public int getColor(int x, int y) {
        return lights[(x - x()) + (y - y()) * width()] & MASK_RGB;
    }

    public static LightMap newInstance(Board board, Rectangle mapRect, Light light, Point pos) {
        Preconditions.checkArgument(mapRect.contains(pos), "pos not in rect");
        LightBoard lightBoard = new LightBoard(board, mapRect, light, pos);
        PrecisePermissive.calculateFov(lightBoard, pos, mapRect);
        return new LightMap(mapRect, lightBoard.lights);
    }

    private static class LightBoard extends Rectangle implements FovBoard {

        final Board board;
        final Light light;
        final Point pos;
        final int[] lights;

        LightBoard(Board board, Rectangle rect, Light light, Point pos) {
            super(rect);
            this.board = board;
            this.light = light;
            this.pos = pos;
            this.lights = new int[rect.width() * rect.height()];
        }

        @Override
        public boolean blocksVision(Point p) {
            return board.getTileAt(p.y(), p.x()).isOpaque();
        }

        @Override
        public void notifyVisible(Point p) {
            int i = (p.x() - x()) + (p.y() - y()) * width();
            if (i < 0 || i > lights.length) {
                return;
            }
            double d = Math.max(0, (light.range() - pos.distanceTo(p)) / light.range());
            lights[i] =
                    FLAG_VISIBLE |
                    ((int)(light.color().getRed() * d) << 16) |
                    ((int)(light.color().getGreen() * d) << 8) |
                    ((int)(light.color().getBlue() * d));
        }
    }
}
