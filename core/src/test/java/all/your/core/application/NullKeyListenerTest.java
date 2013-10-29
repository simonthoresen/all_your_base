package all.your.core.application;

import org.junit.Test;

import java.awt.event.KeyListener;

import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class NullKeyListenerTest {

    @Test
    public void requireThatInstanceIsAKeyListener() {
        assertTrue(NullKeyListener.INSTANCE instanceof KeyListener);
    }
}
