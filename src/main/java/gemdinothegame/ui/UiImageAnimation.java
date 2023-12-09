package gemdinothegame.ui;

import java.awt.Graphics2D;
import java.awt.Graphics;

import gemdinothegame.app.App;
import gemdinothegame.screen.Screen;
import gemdinothegame.animation.UiAnimation;

public class UiImageAnimation extends UiComponent {
    UiAnimation uiAnimation;

    public UiImageAnimation(App app, final Screen screen, final double x, final double y, final int width, final int height, final UiAnimation a) {
        super(app, screen, x, y, width, height);
        this.uiAnimation = a;
    }

    @Override
    public void update() {
        this.uiAnimation.update();
    }

    @Override
    public void render(final Graphics g) {
        this.uiAnimation.render((Graphics2D) g.create(), this.status, this.x, this.y, this.width, this.height);
    }
}
