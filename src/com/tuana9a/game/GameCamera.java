// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.game;

import com.tuana9a.game.entity.Entity;
import com.tuana9a.state.GameState;

public class GameCamera
{
    private double xOffset;
    private double yOffset;
    private double speed;
    private GameState gameState;
    
    public GameCamera(final GameState gameState) {
        this.gameState = gameState;
        this.speed = 4.0;
    }
    
    public void move() {
        final boolean up = this.gameState.getKeyboardManager().up;
        final boolean down = this.gameState.getKeyboardManager().down;
        final boolean left = this.gameState.getKeyboardManager().left;
        final boolean right = this.gameState.getKeyboardManager().right;
        if (!up || !down) {
            if (up) {
                this.yOffset -= this.speed;
            }
            if (down) {
                this.yOffset += this.speed;
            }
        }
        if (!left || !right) {
            if (left) {
                this.xOffset -= this.speed;
            }
            if (right) {
                this.xOffset += this.speed;
            }
        }
        this.checkBlankSpace();
    }
    
    public void centerOnEntity(final Entity e) {
        this.xOffset = e.x - this.gameState.getDisplayWidth() / 2.0 + e.width / 2.0;
        this.yOffset = e.y - this.gameState.getDisplayHeight() / 2.0 + e.height / 2.0;
        this.checkBlankSpace();
    }
    
    public void checkBlankSpace() {
        final int mapWidth = this.gameState.getStage().getCurrentMap().getMapPixelWidth();
        final int mapHeight = this.gameState.getStage().getCurrentMap().getMapPixelHeight();
        final int screenWidth = this.gameState.getDisplayWidth();
        final int screenHeight = this.gameState.getDisplayHeight();
        final double maxOffsetX = mapWidth - screenWidth;
        final double maxOffsetY = mapHeight - screenHeight;
        if (this.xOffset < 0.0) {
            this.xOffset = 0.0;
        }
        else if (this.xOffset > maxOffsetX) {
            this.xOffset = maxOffsetX;
        }
        if (this.yOffset < 0.0) {
            this.yOffset = 0.0;
        }
        else if (this.yOffset > maxOffsetY) {
            this.yOffset = maxOffsetY;
        }
        if (mapWidth < screenWidth) {
            this.xOffset = maxOffsetX / 2.0;
        }
        if (mapHeight < screenHeight) {
            this.yOffset = maxOffsetY / 2.0;
        }
    }
    
    public int getCamRenderX(final double x) {
        return (int)(x - this.getxOffset());
    }
    
    public int getCamRenderY(final double y) {
        return (int)(y - this.getyOffset());
    }
    
    public double getxOffset() {
        return this.xOffset;
    }
    
    public void setxOffset(final double xOffset) {
        this.xOffset = xOffset;
    }
    
    public double getyOffset() {
        return this.yOffset;
    }
    
    public void setyOffset(final double yOffset) {
        this.yOffset = yOffset;
    }
}
