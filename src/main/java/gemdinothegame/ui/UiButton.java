package gemdinothegame.ui;

import java.awt.Graphics2D;
import java.awt.Graphics;

import gemdinothegame.app.App;
import gemdinothegame.app.EventQueue;
import gemdinothegame.interfaces.EventHandler;
import gemdinothegame.screen.Screen;
import gemdinothegame.animation.UiAnimation;

public class UiButton extends UiComponent {
    private final UiAnimation animation;
    private final EventHandler eventHandler;

    public UiButton(App app, final Screen screen, final double x, final double y, final int width, final int height, final UiAnimation animation, final EventHandler eventHandler) {
        super(app, screen, x, y, width, height);
        this.animation = animation;
        this.eventHandler = eventHandler;
    }

    @Override
    public void render(final Graphics g) {
        this.animation.render((Graphics2D) g.create(), this.status, this.x, this.y, this.width, this.height);
        super.render(g);
    }

    @Override
    public void onRelease() {
        EventQueue eventQueue = app.getEventQueue();
        eventQueue.push(this.eventHandler);
    }
}