// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.game.entity;

import com.tuana9a.state.GameState;

public abstract class MovingEntity extends Entity
{
    public double speed;
    public double xMove;
    public double yMove;
    
    public MovingEntity(final GameState gameState, final int id, final double x, final double y) {
        super(gameState, id, x, y);
    }
    
    public abstract void move();
}
