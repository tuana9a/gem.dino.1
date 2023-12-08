package com.tuana9a.gemdino.entities;

import com.tuana9a.gemdino.app.App;

public abstract class MovingEntity extends Entity {
    public double speed;
    public double xMove;
    public double yMove;

    public MovingEntity(App app, final int id, final double x, final double y) {
        super(app, id, x, y);
    }

    public abstract void move();
}