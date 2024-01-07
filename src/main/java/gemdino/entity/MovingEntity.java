package gemdino.entity;

import gemdino.app.App;
import gemdino.entity.renderer.OuterBoundRenderer;

public abstract class MovingEntity extends Entity {
    public double speed;
    public double xMove;
    public double yMove;

    public MovingEntity(App app, final int id, final double x, final double y) {
        super(app, id, x, y);
        this.outerBoundRenderer = new OuterBoundRenderer(this);
    }

    public abstract void move();
}
