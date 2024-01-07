package gemdino.entity.renderer;

import gemdino.app.App;
import gemdino.enemy.Enemy;
import gemdino.engine.GameCamera;
import gemdino.interfaces.Renderer;

import java.awt.*;

public class WorkingAreaRenderer implements Renderer {
    private final App app;
    private final Enemy enemy;

    public WorkingAreaRenderer(App app, Enemy enemy) {
        this.app = app;
        this.enemy = enemy;
    }

    @Override
    public void render(Graphics g) {
        final Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.RED);
        final GameCamera gameCamera = app.getGameCamera();
        final double x = gameCamera.getCamRenderX(enemy.workingArea.x);
        final double y = gameCamera.getCamRenderY(enemy.workingArea.y);
        g2d.drawRect((int) x, (int) y, enemy.workingArea.width, enemy.workingArea.height);
    }
}
