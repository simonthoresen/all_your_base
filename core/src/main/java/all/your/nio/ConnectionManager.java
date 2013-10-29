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
        final AsynchronousChannelGroup allChannels = AsynchronousChannelGroup.withFixedThreadPool(
                Runtime.getRuntime().availableProcessors() * 2,
                Executors.defaultThreadFactory());
        final AsynchronousServerSocketChannel serverChannel =
                AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(4080));
        serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {

            @Override
            public void completed(AsynchronousSocketChannel channel, Void attachment) {
                // accept the next connection
                serverChannel.accept(null, this);

                // handle this connection
                final ByteBuffer buf = ByteBuffer.allocate(1024);
                channel.read(buf, 100, TimeUnit.MILLISECONDS, null,
                             new CompletionHandler<Integer, Void>() {

                                 @Override
                                 public void completed(Integer result, Void attachment) {
                                     System.out.println("read completed; " + result);
                                 }

                                 @Override
                                 public void failed(Throwable t, Void attachment) {
                                     t.printStackTrace();
                                 }
                             });
                channel.write(ByteBuffer.wrap("Hello, world".getBytes(StandardCharsets.UTF_8)),
                              100, TimeUnit.MILLISECONDS, null,
                              new CompletionHandler<Integer, Void>() {

                                  @Override
                                  public void completed(Integer result, Void attachment) {
                                      System.out.println("write completed; " + result);
                                  }

                                  @Override
                                  public void failed(Throwable t, Void attachment) {
                                      t.printStackTrace();
                                  }
                              });
            }

            @Override
            public void failed(Throwable t, Void attachment) {
                t.printStackTrace();
            }
        });


        Thread.sleep(TimeUnit.DAYS.toMillis(1));
    }
}
