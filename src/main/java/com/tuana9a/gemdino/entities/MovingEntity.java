// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.gemdino.entities;

public abstract class MovingEntity extends Entity {
    public double speed;
    public double xMove;
    public double yMove;

    public MovingEntity(final int id, final double x, final double y) {
        super(id, x, y);
    }

    public abstract void move();
}
