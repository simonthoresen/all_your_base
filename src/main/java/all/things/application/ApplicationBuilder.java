package all.things.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen Hult</a>
 */
public class ApplicationBuilder {

    private final List<ApplicationListener> listeners = new ArrayList<>();
    private ApplicationState initialState = NullApplicationState.INSTANCE;
    private String windowTitle = "Application";
    private int windowWidth = 640;
    private int windowHeight = 480;
    private int framesPerSecond = 60;

    public ApplicationBuilder setInitialState(ApplicationState initialState) {
        Objects.requireNonNull(initialState, "initialState");
        this.initialState = initialState;
        return this;
    }

    public ApplicationState getInitialState() {
        return initialState;
    }

    public ApplicationBuilder addApplicationListener(ApplicationListener listener) {
        this.listeners.add(listener);
        return this;
    }

    public List<ApplicationListener> getApplicationListeners() {
        return listeners;
    }

    public ApplicationBuilder setWindowTitle(String windowTitle) {
        Objects.requireNonNull(windowTitle, "windowTitle");
        this.windowTitle = windowTitle;
        return this;
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public ApplicationBuilder setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
        return this;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public ApplicationBuilder setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
        return this;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public ApplicationBuilder setFramesPerSecond(int framesPerSecond) {
        this.framesPerSecond = framesPerSecond;
        return this;
    }

    public int getFramesPerSecond() {
        return framesPerSecond;
    }

    public Application build() {
        return new ApplicationImpl(this);
    }
}
