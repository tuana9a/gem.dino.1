// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.environment;

import com.tuana9a.App;
import com.tuana9a.entities.Entity;
import com.tuana9a.screen.GameScreen;

public class Camera {
    private static final Camera instance = new Camera();

    private double xOffset;
    private double yOffset;
    private final double speed;

    private Camera() {
        this.speed = 4.0;
    }

    public static Camera getInstance() {
        return instance;
    }

    public void move() {
        App app = App.getInstance();
        final boolean up = app.getKeyboardManager().up;
        final boolean down = app.getKeyboardManager().down;
        final boolean left = app.getKeyboardManager().left;
        final boolean right = app.getKeyboardManager().right;
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
        GameScreen gameScreen = GameScreen.getInstance();
        this.xOffset = e.x - gameScreen.getDisplayWidth() / 2.0 + e.width / 2.0;
        this.yOffset = e.y - gameScreen.getDisplayHeight() / 2.0 + e.height / 2.0;
        this.checkBlankSpace();
    }

    public void checkBlankSpace() {
        GameScreen gameScreen = GameScreen.getInstance();
        final int mapWidth = gameScreen.getStage().getCurrentMap().getMapPixelWidth();
        final int mapHeight = gameScreen.getStage().getCurrentMap().getMapPixelHeight();
        final int screenWidth = gameScreen.getDisplayWidth();
        final int screenHeight = gameScreen.getDisplayHeight();
        final double maxOffsetX = mapWidth - screenWidth;
        final double maxOffsetY = mapHeight - screenHeight;
        if (this.xOffset < 0.0) {
            this.xOffset = 0.0;
        } else if (this.xOffset > maxOffsetX) {
            this.xOffset = maxOffsetX;
        }
        if (this.yOffset < 0.0) {
            this.yOffset = 0.0;
        } else if (this.yOffset > maxOffsetY) {
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
        return (int) (x - this.getxOffset());
    }

    public int getCamRenderY(final double y) {
        return (int) (y - this.getyOffset());
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
