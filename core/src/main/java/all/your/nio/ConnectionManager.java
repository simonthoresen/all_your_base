package all.your.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class ConnectionManager {

    public static void main(String[] args) throws Throwable {
        AsynchronousChannelGroup allChannels = AsynchronousChannelGroup.withFixedThreadPool(
                Runtime.getRuntime().availableProcessors() * 2,
                Executors.defaultThreadFactory());
        AsynchronousServerSocketChannel serverChannel =
                AsynchronousServerSocketChannel.open(allChannels).bind(new InetSocketAddress(4080));
        serverChannel.accept(serverChannel, AcceptCompletionHandler.INSTANCE);
        Thread.sleep(TimeUnit.DAYS.toMillis(1));
    }

    private static enum AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel> {

        INSTANCE;

        @Override
        public void completed(AsynchronousSocketChannel channel, AsynchronousServerSocketChannel serverChannel) {
            System.out.println("accepted " + channel);

            // accept the next connection
            serverChannel.accept(serverChannel, this);

            // handle this connection
            ByteBuffer buf = ByteBuffer.allocate(1024);
            channel.read(buf, buf, ReadCompletionHandler.INSTANCE);
            channel.write(ByteBuffer.wrap(("HTTP/1.0 200 OK\r\n" +
                                           "Content-Length: 3\r\n" +
                                           "\r\n" +
                                           "foo").getBytes(StandardCharsets.UTF_8)), null,
                          WriteCompletionHandler.INSTANCE);
        }

        @Override
        public void failed(Throwable t, AsynchronousServerSocketChannel serverChannel) {
            t.printStackTrace();
        }
    }

    private static enum ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

        INSTANCE;

        @Override
        public void completed(Integer result, ByteBuffer buf) {
            int numBytes = result;
            if (numBytes < 0) {
                System.out.println("done");
                return;
            }
            buf.flip();
            byte[] arr = new byte[buf.remaining()];
            buf.get(arr);
            System.out.println(new String(arr, StandardCharsets.UTF_8));
        }

        @Override
        public void failed(Throwable t, ByteBuffer buf) {
            t.printStackTrace();
        }
    }

    private static enum WriteCompletionHandler implements CompletionHandler<Integer, Void> {

        INSTANCE;

        @Override
        public void completed(Integer result, Void v) {
            System.out.println("wrote " + result + " bytes");
        }

        @Override
        public void failed(Throwable t, Void v) {
            t.printStackTrace();
        }
    }
}
