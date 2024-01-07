package gemdino.engine;

import gemdino.app.App;
import gemdino.entity.Entity;
import gemdino.input.KeyboardManager;
import lombok.Getter;
import lombok.Setter;

public class GameCamera {
    private final App app;
    @Setter
    @Getter
    private double xOffset;
    @Setter
    @Getter
    private double yOffset;
    private final double speed;

    public GameCamera(App app) {
        this.app = app;
        this.speed = 4.0;
    }

    public void move() {
        KeyboardManager keyboardManager = app.getKeyboardManager();
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
        this.xOffset = e.x - app.getDisplayWidth() / 2.0 + e.width / 2.0;
        this.yOffset = e.y - app.getDisplayHeight() / 2.0 + e.height / 2.0;
        this.checkBlankSpace();
    }

    public void checkBlankSpace() {
        GameMap gameMap = app.getGameMap();
        final int mapWidth = gameMap.getMapPixelWidth();
        final int mapHeight = gameMap.getMapPixelHeight();
        final int screenWidth = app.getDisplayWidth();
        final int screenHeight = app.getDisplayHeight();
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
        return (int) (x - this.getXOffset());
    }

    public int getCamRenderY(final double y) {
        return (int) (y - this.getYOffset());
    }

}
