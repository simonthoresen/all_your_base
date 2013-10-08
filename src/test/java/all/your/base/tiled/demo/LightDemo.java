package all.your.base.tiled.demo;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 * @version $Id$
 */
public class LightDemo {
/*
    private final static Light WHITE = new Light(new Color(128, 128, 128), 50);
    private final Writer out;

    public LightDemo(Writer out) {
        this.out = out;
    }

    @Override
    public Boolean call() throws Exception {
        Board board = new Board(new TileSet.Builder()
                                        .add(new Tile.Builder()
                                                     .setColor(Color.WHITE)
                                                     .setCanEnter(true)
                                                     .setIsOpaque(false)
                                                     .setSymbol('.')
                                                     .build())
                                        .add(new Tile.Builder()
                                                     .setColor(Color.MAGENTA)
                                                     .setCanEnter(false)
                                                     .setIsOpaque(true)
                                                     .setSymbol('#')
                                                     .build())
                                        .build(),
                                //       0000000000111111111122222222223333333333444444444455555555556666666666777
                                //       0123456789012345678901234567890123456789012345678901234567890123456789012
                                TileMaps.newMap(
                                        "11111111111111111111111111111111111111111111111111111111111111111111111\n" +
                                        "10000000000000000000000000000000000000000000000000000000000000000000001\n" +
                                        "10000000000000000000000000000000000000000000000000000000000000000000001\n" +
                                        "10000000000000000000000000000000000000000000000000000000000000000000001\n" +
                                        "10000001110000001110000001110000001110000001110000001110000001110000001\n" +
                                        "10000001110000001110000001110000001110000001110000001110000001110000001\n" +
                                        "10000001110000001110000001110000001110000001110000001110000001110000001\n" +
                                        "10000000000000000000000000000000000000000000000000000000000000000000001\n" +
                                        "10000000000000000000000000000000000000000000000000000000000000000000001\n" +
                                        "10000000000000000000000000000000000000000000000000000000000000000000001\n" +
                                        "10000000000000000000000000000000000000000000000000000000000000000000001\n" +
                                        "10000000000000000000000000000000000000000000000000000000000000000000001\n" +
                                        "10000001110000001110000001110000001110000001110000001110000001110000001\n" +
                                        "10000001110000001110000001110000001110000001110000001110000001110000001\n" +
                                        "10000001110000001110000001110000001110000001110000001110000001110000001\n" +
                                        "10000000000000000000000000000000000000000000000000000000000000000000001\n" +
                                        "10000000000000000000000000000000000000000000000000000000000000000000001\n" +
                                        "10000000000000000000000000000000000000000000000000000000000000000000001\n" +
                                        "11111111111111111111111111111111111111111111111111111111111111111111111\n"));
        all.your.base.math.Rectangle rect = new all.your.base.math.Rectangle(0, 0, board.width(), board.height());
        try {
            all.your.base.math.Point eye = new all.your.base.math.Point(35, 9);
            LightMap eyeMap = LightMap.newInstance(board, rect, new Light(Color.WHITE, 5), eye);
            all.your.base.math.Point lightPos = new all.your.base.math.Point(1, 1);
            all.your.base.math.Point lightAdd = new all.your.base.math.Point(1, 0);
            for (int i = 0; i < 10000; ++i) {
                lightPos = lightPos.add(lightAdd);
                if (lightPos.equals(new all.your.base.math.Point(board.width() - 2, 1))) {
                    lightAdd = new all.your.base.math.Point(0, 1);
                } else if (lightPos.equals(new all.your.base.math.Point(board.width() - 2, board.height() - 2))) {
                    lightAdd = new all.your.base.math.Point(-1, 0);
                } else if (lightPos.equals(new all.your.base.math.Point(1, board.height() - 2))) {
                    lightAdd = new all.your.base.math.Point(0, -1);
                } else if (lightPos.equals(new all.your.base.math.Point(1, 1))) {
                    lightAdd = new all.your.base.math.Point(1, 0);
                }
                LightMap lightMap = LightMap.newInstance(board, rect, WHITE, lightPos);

                StringBuilder out = new StringBuilder();
                out.append(EscapeCodes.CLS);
                for (int y = 0; y < board.height(); ++y) {
                    AnsiColor prev = null;
                    for (int x = 0; x < board.width(); ++x) {
                        Color seenColor = new Color(eyeMap.getColor(x, y) & 0xFFFFFF);
                        if (eyeMap.canSee(x, y)) {
                            Color light = new Color(lightMap.getColor(x, y) & 0xFFFFFF);
                            seenColor = new Color(Math.min(255, seenColor.getRed() + light.getRed()),
                                                  Math.min(255, seenColor.getGreen() + light.getGreen()),
                                                  Math.min(255, seenColor.getBlue() + light.getBlue()));
                        }
                        Color tile = board.getTileAt(y, x).color();
                        Color tile2 = new Color(Math.min(tile.getRed(), seenColor.getRed()),
                                                Math.min(tile.getGreen(), seenColor.getGreen()),
                                                Math.min(tile.getBlue(), seenColor.getBlue()));
                        AnsiColor next = new AnsiColor(tile2, tile2);
                        if (!next.equals(prev)) {
                            next.appendTo(out);
                            prev = next;
                        }
                        if (x == eye.x() && y == eye.y()) {
                            out.append('@');
//                        } else if (eyeMap.canSee(x, y)) {
//                            out.append(board.getSymbolAt(y, x));
                        } else {
                            out.append(' ');
                        }
                    }
                    out.append("\r\n");
                }
                this.out.write(out.toString());
                this.out.flush();
                Thread.sleep(50);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }
*/
}
