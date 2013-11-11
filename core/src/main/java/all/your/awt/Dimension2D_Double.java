package all.your.awt;

import java.awt.geom.Dimension2D;

/**
 * @author <a href="mailto:simon@hult-thoresen.com">Simon Thoresen Hult</a>
 */
public class Dimension2D_Double extends Dimension2D {

    private double width;
    private double height;

    public Dimension2D_Double(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
    }
}
