// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.gemdino.engine;

import com.tuana9a.gemdino.entities.Entity;
import com.tuana9a.gemdino.input.KeyboardManager;
import com.tuana9a.gemdino.screen.GameScreen;

public class GameCamera {
    private static final GameCamera instance = new GameCamera();

    private double xOffset;
    private double yOffset;
    private final double speed;

    private GameCamera() {
        this.speed = 4.0;
    }

    public static GameCamera getInstance() {
        return instance;
    }

    public void move() {
        KeyboardManager keyboardManager = KeyboardManager.getInstance();
        final boolean up = keyboardManager.up;
        final boolean down = keyboardManager.down;
        final boolean left = keyboardManager.left;
        final boolean right = keyboardManager.right;
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
        GameWorld gameWorld = GameWorld.getInstance();
        GameMap gameMap = GameMap.getInstance();
        final int mapWidth = gameMap.getMapPixelWidth();
        final int mapHeight = gameMap.getMapPixelHeight();
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
