package all.your.core.application;

import org.junit.Test;

import java.awt.event.MouseListener;

import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class NullMouseListenerTest {

    @Test
    public void requireThatInstanceIsAMouseListener() {
        assertTrue(NullMouseListener.INSTANCE instanceof MouseListener);
    }
}
