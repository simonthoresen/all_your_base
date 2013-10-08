package all.things.application;

import org.junit.Test;

import java.awt.event.ComponentListener;

import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class NullComponentListenerTest {

    @Test
    public void requireThatInstanceIsAComponentListener() {
        assertTrue(NullComponentListener.INSTANCE instanceof ComponentListener);
    }
}
