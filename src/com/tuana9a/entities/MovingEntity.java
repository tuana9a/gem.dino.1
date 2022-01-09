// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.entities;

import com.tuana9a.screen.GameScreen;

public abstract class MovingEntity extends Entity {
    public double speed;
    public double xMove;
    public double yMove;

    public MovingEntity(final GameScreen gameScreen, final int id, final double x, final double y) {
        super(gameScreen, id, x, y);
    }

    public abstract void move();
}
