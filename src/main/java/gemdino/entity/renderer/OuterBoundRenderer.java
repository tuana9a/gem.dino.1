package gemdino.entity.renderer;

import gemdino.entity.Entity;
import gemdino.interfaces.Renderer;

import java.awt.*;

public class OuterBoundRenderer implements Renderer {
    private final Entity entity;
    private Color borderColor;
    private Color backgroundColor;

    public OuterBoundRenderer(Entity entity) {
        this(entity, Color.BLACK, Color.CYAN);
    }

    public OuterBoundRenderer(Entity entity, Color backgroundColor, Color borderColor) {
        this.entity = entity;
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect((int) entity.xCam, (int) entity.yCam, entity.width, entity.height);
        g.setColor(Color.CYAN);
        g.drawRect((int) entity.xCam, (int) entity.yCam, entity.width, entity.height);
        g.drawLine((int) entity.xCam, (int) entity.yCam + entity.actualSize.y, (int) entity.xCam + entity.actualSize.x, (int) entity.yCam + entity.actualSize.y);
        g.drawLine((int) entity.xCam, (int) entity.yCam, (int) entity.xCam + entity.actualSize.x, (int) entity.yCam);
        g.drawLine((int) entity.xCam + entity.actualSize.x, (int) entity.yCam, (int) entity.xCam + entity.actualSize.x, (int) entity.yCam + entity.actualSize.y);
        g.drawLine((int) entity.xCam, (int) entity.yCam, (int) entity.xCam, (int) entity.yCam + entity.actualSize.y);
    }
}
