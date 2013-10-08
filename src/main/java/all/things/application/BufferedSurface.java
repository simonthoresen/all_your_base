package all.things.application;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
class BufferedSurface {

    private final Deque<Buffer> buffers = new ConcurrentLinkedDeque<>();
    private volatile int size;

    private BufferedSurface(Buffer... buffers) {
        this.buffers.addAll(Arrays.asList(buffers));

        BufferedImage image = buffers[0].image;
        this.size = encodeSize(image.getWidth(), image.getHeight());
    }

    public void resize(int width, int height) {
        // called by EDT
        size = encodeSize(width, height);
    }

    public void render(Graphics g) {
        // called by EDT
        Buffer buf = ensureCorrectSize(buffers.removeFirst());
        try {
            g.drawImage(buf.image, 0, 0, null);
        } finally {
            buffers.addLast(buf);
        }
    }

    public Buffer retainBuffer() {
        return ensureCorrectSize(buffers.removeLast());
    }

    public void releaseBuffer(Buffer buf) {
        buffers.addFirst(ensureCorrectSize(buf));
    }

    private Buffer ensureCorrectSize(Buffer in) {
        int expectedSize = this.size;
        int actualSize = encodeSize(in.image.getWidth(), in.image.getHeight());
        if (actualSize == expectedSize) {
            return in;
        }
        int width = decodeWidth(expectedSize);
        int height = decodeHeight(expectedSize);
        Buffer out = new Buffer(width, height, in.image.getType());
        out.graphics.drawImage(in.image, 0, 0, width, height, null);
        return out;
    }

    private static int encodeSize(int width, int height) {
        return (width << 16) | (height & 0xFFFF);
    }

    private static int decodeWidth(int size) {
        return size >> 16;
    }

    private static int decodeHeight(int size) {
        return size & 0xFFFF;
    }

    public static class Buffer {

        private final BufferedImage image;
        private final Graphics2D graphics;

        private Buffer(int width, int height, int type) {
            this.image = new BufferedImage(width, height, type);
            this.graphics = image.createGraphics();
        }

        public Graphics2D getGraphics() {
            return graphics;
        }
    }

    public static BufferedSurface newDoubleBuffered(int width, int height, int type) {
        return new BufferedSurface(new Buffer(width, height, type),
                                   new Buffer(width, height, type));
    }

    public static BufferedSurface newTripleBuffered(int width, int height, int type) {
        return new BufferedSurface(new Buffer(width, height, type),
                                   new Buffer(width, height, type),
                                   new Buffer(width, height, type));
    }
}
