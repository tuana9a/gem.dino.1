package gemdino.entity;

import gemdino.app.App;
import gemdino.entity.renderer.InnerBoundRenderer;
import gemdino.entity.renderer.OuterBoundRenderer;

public abstract class StaticEntity extends Entity {
    public StaticEntity(App app, final int id, final double x, final double y) {
        super(app, id, x, y);
        this.innerBoundRenderer = new InnerBoundRenderer(this);
        this.outerBoundRenderer = new OuterBoundRenderer(this);
    }
}
