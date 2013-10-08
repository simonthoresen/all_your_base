package all.things.application;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class BufferedSurfaceTest {

    @Test
    public void requireThatBuffersCanBeRetained() {
        BufferedSurface surface = BufferedSurface.newDoubleBuffered(640, 480, BufferedImage.TYPE_INT_ARGB);
        assertNotNull(surface.retainBuffer());
    }

    @Test
    public void requireThatNoMoreThanAvailableBuffersCanBeRetained() {
        BufferedSurface surface = BufferedSurface.newDoubleBuffered(640, 480, BufferedImage.TYPE_INT_ARGB);
        assertNotNull(surface.retainBuffer());
        assertNotNull(surface.retainBuffer());
        try {
            surface.retainBuffer();
            fail();
        } catch (NoSuchElementException e) {

        }
    }

    @Test
    public void requireThatRenderRetainsABuffer() throws InterruptedException {
        BufferedSurface surface = BufferedSurface.newDoubleBuffered(640, 480, BufferedImage.TYPE_INT_ARGB);
        BlockingGraphics g = new BlockingGraphics();
        executeRender(surface, g.delegate);
        assertTrue(g.hasEntered.await(60, TimeUnit.SECONDS));
        assertNotNull(surface.retainBuffer());
        try {
            surface.retainBuffer();
            fail();
        } catch (NoSuchElementException e) {

        }
        g.canExit.countDown();
    }

    private static void executeRender(final BufferedSurface surface, final Graphics g) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {

            @Override
            public void run() {
                surface.render(g);
            }
        });
    }

    private static class BlockingGraphics {

        final Graphics delegate = Mockito.mock(Graphics.class);
        final CountDownLatch hasEntered = new CountDownLatch(1);
        final CountDownLatch canExit = new CountDownLatch(1);

        BlockingGraphics() {
            Mockito.when(delegate.drawImage(Mockito.any(Image.class), Mockito.anyInt(), Mockito.anyInt(),
                                            Mockito.any(ImageObserver.class))).thenAnswer(new Answer<Object>() {

                @Override
                public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                    hasEntered.countDown();
                    canExit.await(60, TimeUnit.SECONDS);
                    return null;
                }
            });
        }
    }
}
