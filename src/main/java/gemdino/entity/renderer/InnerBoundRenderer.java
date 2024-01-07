package gemdino.entity.renderer;

import gemdino.entity.Entity;
import gemdino.interfaces.Renderer;

import java.awt.*;

public class InnerBoundRenderer implements Renderer {
    private final Entity entity;
    private Color borderColor;
    private Color backgroundColor;

    public InnerBoundRenderer(Entity entity) {
        this(entity, Color.BLACK, Color.CYAN);
    }

    public InnerBoundRenderer(Entity entity, Color backgroundColor, Color borderColor) {
        this.entity = entity;
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect((int) entity.xCam + entity.actualSize.x, (int) entity.yCam + entity.actualSize.y, entity.actualSize.width, entity.actualSize.height);
        g.setColor(borderColor);
        g.drawRect((int) entity.xCam + entity.actualSize.x, (int) entity.yCam + entity.actualSize.y, entity.actualSize.width, entity.actualSize.height);
    }
}
