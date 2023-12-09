package gemdino.entity;

import gemdino.app.App;

public abstract class StaticEntity extends Entity {
    public StaticEntity(App app, final int id, final double x, final double y) {
        super(app, id, x, y);
    }
}
