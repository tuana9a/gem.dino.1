package gemdino.screen;

import gemdino.app.Display;
import gemdino.animation.UiAnimation;
import gemdino.app.App;
import gemdino.engine.GameCamera;
import gemdino.engine.GameMap;
import gemdino.engine.GameWorld;
import gemdino.graphic.Assets;
import gemdino.input.KeyboardManager;
import gemdino.interfaces.EventHandler;
import gemdino.ui.UiButton;
import gemdino.ui.UiImageStatic;
import gemdino.ui.UiNumber;
import gemdino.ui.UiProgressBar;
import gemdino.utils.Utils;
import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameScreen extends Screen {
    private final App app;
    private boolean pauseGame;
    private UiNumber uiPayerHpNumber;
    private UiProgressBar uiBossHpBar;
    private UiImageStatic uiGameOver;
    private UiButton uiPause;
    private UiButton uiResume;
    @Getter
    private final GameCamera gameCamera;
    @Getter
    private final GameMap gameMap;
    @Getter
    private final GameWorld gameWorld;

    public GameScreen(App app) {
        this.app = app;
        this.gameCamera = new GameCamera(app);
        this.gameMap = new GameMap(app);
        this.gameWorld = new GameWorld(app);
        this.initUi();
    }

    @Override
    public void changeCursor() {
        final BufferedImage img = Utils.loadImg("resources/ui/cursor/ui_select.png");
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension d = toolkit.getBestCursorSize(32, 32);
        final Cursor cursor = toolkit.createCustomCursor(img, new Point(d.width / 2, d.height / 2), "img");
        app.getDisplay().getCanvas().setCursor(cursor);
    }

    @Override
    public void initUi() {
        final int screenW = app.getDisplayWidth();
        final int screenH = app.getDisplayHeight();
        final int halfW = screenW / 2;
        final int halfH = screenH / 2;
        final int quarterW = screenW / 4;
        final int quarterH = screenH / 4;
        final int threeQuarterW = halfW + quarterW;
        final int threeQuarterH = halfH + quarterH;
        final int buttonSize = 50;
        final int iconSize = 64;
        final int barSize = 30;
        final int minSpace = 15;

        final Display display = app.getDisplay();

        final UiImageStatic uiPlayerHpIcon = new UiImageStatic(app, this, minSpace, minSpace, iconSize, iconSize, Assets.playerHpIcon);
        this.uiPayerHpNumber = new UiNumber(app, this, iconSize + minSpace, minSpace, iconSize, Assets.imagesNumber);
        this.uiBossHpBar = new UiProgressBar(app, this, quarterW, minSpace + 10, halfW, barSize, Assets.bossHpBarLeft, Assets.bossHpBarMid, Assets.bossHpBarRight);
        this.uiPause = new UiButton(app, this, screenW - 3 * buttonSize - minSpace, minSpace, buttonSize, buttonSize, new UiAnimation(Assets.pauseButton), new EventHandler() {
            @Override
            public void perform() {
                GameScreen.this.pauseGame = true;
                GameScreen.this.uiPause.hide();
                GameScreen.this.uiResume.show();
            }
        });
        this.uiResume = new UiButton(app, this, screenW - 3 * buttonSize - minSpace, minSpace, buttonSize, buttonSize, new UiAnimation(Assets.resumeButton), new EventHandler() {
            @Override
            public void perform() {
                GameScreen.this.pauseGame = false;
                GameScreen.this.uiResume.hide();
                GameScreen.this.uiPause.show();
            }
        });
        final UiButton uiReplay = new UiButton(app, this, screenW - 2 * buttonSize - minSpace, minSpace, buttonSize, buttonSize, new UiAnimation(Assets.replayButton), new EventHandler() {
            @Override
            public void perform() {
                synchronized (app) {
                    replayGame();
                }
            }
        });
        final UiButton uiReturnToMenu = new UiButton(app, this, screenW - buttonSize - minSpace, minSpace, buttonSize, buttonSize, new UiAnimation(Assets.returnMenuButton), new EventHandler() {
            @Override
            public void perform() {
                app.switchScreen(app.getMenuScreen());
            }
        });
        this.uiGameOver = new UiImageStatic(app, this, 0.0, 0.0, display.getWidth(), display.getHeight(), Assets.gameOverImage);
        this.uiResume.hide();
        this.uiGameOver.hide();
        this.uiManager.addAllUiComponent(uiPlayerHpIcon, this.uiPayerHpNumber, this.uiBossHpBar, this.uiPause, this.uiResume, this.uiGameOver, uiReturnToMenu, uiReplay);
    }

    @Override
    public void update() {
        KeyboardManager keyboardManager = app.getKeyboardManager();
        if (!this.refreshTimer.isTime()) {
            return;
        }
        if (keyboardManager.freeCamMode) {
            this.gameCamera.move();
        }
        gameWorld.updateEveryRelCamAll();
        this.uiManager.updateAll();
        if (this.pauseGame) {
            return;
        }
        gameWorld.update();
    }

    @Override
    public void render() {
        if (!this.refreshTimer.isTime()) {
            return;
        }
        this.refreshTimer.reset();
        app.getDisplay().resetFrame();
        Graphics g = app.getDisplay().getGraphics();
        gameWorld.render(g);
        this.uiManager.renderAll(g);
        app.getDisplay().showFrame();
    }

    public void startGame() {
        this.uiResume.onRelease();
        gameWorld.newGame();
        this.uiGameOver.hide();
    }

    public void replayGame() {
        this.uiResume.onRelease();
        gameWorld.replay();
        this.uiGameOver.hide();
    }

    public void updateUiPayerHp(final int playerHp) {
        this.uiPayerHpNumber.setNumber(playerHp);
        if (playerHp <= 0) {
            this.uiGameOver.show();
            this.uiResume.hide();
            this.uiPause.hide();
            this.pauseGame = true;
        }
    }

    public void updateUiBossHp(final int bossHp) {
        this.uiBossHpBar.updateCurrentProgress(bossHp);
    }

    public void isBossStage(final int bossHp) {
        this.uiBossHpBar.initProcess(bossHp, bossHp);
        this.uiBossHpBar.show();
    }

    public void notBossStage() {
        this.uiBossHpBar.hide();
    }

}
