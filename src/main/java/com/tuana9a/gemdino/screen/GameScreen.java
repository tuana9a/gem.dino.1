package com.tuana9a.gemdino.screen;

import com.tuana9a.gemdino.app.Display;
import com.tuana9a.gemdino.animation.UiAnimation;
import com.tuana9a.gemdino.app.App;
import com.tuana9a.gemdino.engine.GameCamera;
import com.tuana9a.gemdino.engine.GameWorld;
import com.tuana9a.gemdino.graphic.Assets;
import com.tuana9a.gemdino.input.KeyboardManager;
import com.tuana9a.gemdino.interfaces.EventHandler;
import com.tuana9a.gemdino.ui.UiButton;
import com.tuana9a.gemdino.ui.UiImageStatic;
import com.tuana9a.gemdino.ui.UiNumber;
import com.tuana9a.gemdino.ui.UiProgressBar;
import com.tuana9a.gemdino.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameScreen extends Screen {
    private App app;
    private boolean pauseGame;
    private UiNumber uiPayerHpNumber;
    private UiProgressBar uiBossHpBar;
    private UiImageStatic uiGameOver;
    private UiButton uiPause;
    private UiButton uiResume;

    public GameScreen(App app) {
        this.app = app;
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

        final UiImageStatic uiPlayerHpIcon = new UiImageStatic(this, minSpace, minSpace, iconSize, iconSize, Assets.playerHpIcon);
        this.uiPayerHpNumber = new UiNumber(this, iconSize + minSpace, minSpace, iconSize, Assets.imagesNumber);
        this.uiBossHpBar = new UiProgressBar(this, quarterW, minSpace + 10, halfW, barSize, Assets.bossHpBarLeft, Assets.bossHpBarMid, Assets.bossHpBarRight);
        this.uiPause = new UiButton(this, screenW - 3 * buttonSize - minSpace, minSpace, buttonSize, buttonSize, new UiAnimation(Assets.pauseButton), new EventHandler() {
            @Override
            public void perform() {
                GameScreen.this.pauseGame = true;
                GameScreen.this.uiPause.hide();
                GameScreen.this.uiResume.show();
            }
        });
        this.uiResume = new UiButton(this, screenW - 3 * buttonSize - minSpace, minSpace, buttonSize, buttonSize, new UiAnimation(Assets.resumeButton), new EventHandler() {
            @Override
            public void perform() {
                GameScreen.this.pauseGame = false;
                GameScreen.this.uiResume.hide();
                GameScreen.this.uiPause.show();
            }
        });
        final UiButton uiReplay = new UiButton(this, screenW - 2 * buttonSize - minSpace, minSpace, buttonSize, buttonSize, new UiAnimation(Assets.replayButton), new EventHandler() {
            @Override
            public void perform() {
                synchronized (app) {
                    replayGame();
                }
            }
        });
        final UiButton uiReturnToMenu = new UiButton(this, screenW - buttonSize - minSpace, minSpace, buttonSize, buttonSize, new UiAnimation(Assets.returnMenuButton), new EventHandler() {
            @Override
            public void perform() {
                app.switchScreen(app.getMenuScreen());
            }
        });
        this.uiGameOver = new UiImageStatic(this, 0.0, 0.0, display.getWidth(), display.getHeight(), Assets.gameOverImage);
        this.uiResume.hide();
        this.uiGameOver.hide();
        this.uiManager.addAllUiComponent(uiPlayerHpIcon, this.uiPayerHpNumber, this.uiBossHpBar, this.uiPause, this.uiResume, this.uiGameOver, uiReturnToMenu, uiReplay);
    }

    @Override
    public void update() {
        KeyboardManager keyboardManager = KeyboardManager.getInstance();
        if (!this.refreshTimer.isTime()) {
            return;
        }
        if (keyboardManager.freeCamMode) {
            GameCamera.getInstance().move();
        }
        GameWorld gameWorld = GameWorld.getInstance();
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
        GameWorld gameWorld = GameWorld.getInstance();
        gameWorld.render(g);
        this.uiManager.renderAll(g);
        app.getDisplay().showFrame();
    }

    public void startGame() {
        this.uiResume.onRelease();
        GameWorld gameWorld = GameWorld.getInstance();
        gameWorld.newGame();
        this.uiGameOver.hide();
    }

    public void replayGame() {
        this.uiResume.onRelease();
        GameWorld gameWorld = GameWorld.getInstance();
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
