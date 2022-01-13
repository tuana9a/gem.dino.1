// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.screen;

import com.tuana9a.app.Display;
import com.tuana9a.input.KeyboardManager;
import com.tuana9a.interfaces.ActionEvent;
import com.tuana9a.animation.UiAnimation;
import com.tuana9a.graphic.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

import com.tuana9a.utils.Utils;
import com.tuana9a.app.App;
import com.tuana9a.ui.UiButton;
import com.tuana9a.ui.UiImageStatic;
import com.tuana9a.ui.UiProgressBar;
import com.tuana9a.ui.UiNumber;
import com.tuana9a.engine.Camera;
import com.tuana9a.engine.Stage;

public class GameScreen extends BaseScreen {
    private static final GameScreen instance = new GameScreen();

    private boolean pauseGame;
    private final Stage stage;
    private UiNumber uiPayerHpNumber;
    private UiProgressBar uiBossHpBar;
    private UiImageStatic uiGameOver;
    private UiButton uiPause;
    private UiButton uiResume;

    public static GameScreen getInstance() {
        return instance;
    }

    private GameScreen() {
        this.stage = new Stage(this);
    }

    @Override
    public void changeCursor() {
        final BufferedImage img = Utils.loadImg("resources/ui/cursor/ui_select.png");
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension d = toolkit.getBestCursorSize(32, 32);
        final Cursor cursor = toolkit.createCustomCursor(img, new Point(d.width / 2, d.height / 2), "img");
        Display.getInstance().getCanvas().setCursor(cursor);
    }

    @Override
    public void initUi() {
        final int screenW = this.getDisplayWidth();
        final int screenH = this.getDisplayHeight();
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

        final App app = App.getInstance();
        final Display display = Display.getInstance();

        final UiImageStatic uiPlayerHpIcon = new UiImageStatic(this, minSpace, minSpace, iconSize, iconSize, Assets.playerHpIcon);
        this.uiPayerHpNumber = new UiNumber(this, iconSize + minSpace, minSpace, iconSize, Assets.imagesNumber);
        this.uiBossHpBar = new UiProgressBar(this, quarterW, minSpace + 10, halfW, barSize, Assets.bossHpBarLeft, Assets.bossHpBarMid, Assets.bossHpBarRight);
        this.uiPause = new UiButton(this, screenW - 3 * buttonSize - minSpace, minSpace, buttonSize, buttonSize, new UiAnimation(Assets.pauseButton), new ActionEvent() {
            @Override
            public void perform() {
                GameScreen.this.pauseGame = true;
                GameScreen.this.uiPause.hide();
                GameScreen.this.uiResume.show();
            }
        });
        this.uiResume = new UiButton(this, screenW - 3 * buttonSize - minSpace, minSpace, buttonSize, buttonSize, new UiAnimation(Assets.resumeButton), new ActionEvent() {
            @Override
            public void perform() {
                GameScreen.this.pauseGame = false;
                GameScreen.this.uiResume.hide();
                GameScreen.this.uiPause.show();
            }
        });
        final UiButton uiReplay = new UiButton(this, screenW - 2 * buttonSize - minSpace, minSpace, buttonSize, buttonSize, new UiAnimation(Assets.replayButton), new ActionEvent() {
            @Override
            public void perform() {
                synchronized (app) {
                    replayGame();
                }
            }
        });
        final UiButton uiReturnToMenu = new UiButton(this, screenW - buttonSize - minSpace, minSpace, buttonSize, buttonSize, new UiAnimation(Assets.returnMenuButton), new ActionEvent() {
            @Override
            public void perform() {
                app.switchToState(MenuScreen.getInstance());
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
            Camera.getInstance().move();
        }
        this.stage.updateEveryRelCamAll();
        this.uiManager.updateAll();
        if (this.pauseGame) {
            return;
        }
        this.stage.update();
    }

    @Override
    public void render() {
        if (!this.refreshTimer.isTime()) {
            return;
        }
        this.refreshTimer.reset();
        resetFrame();
        Graphics g = getGraphics();
        this.stage.render(g);
        this.uiManager.renderAll(g);
        showFrame();
    }

    public void startGame() {
        this.uiResume.onRelease();
        this.stage.newGame();
        this.uiGameOver.hide();
    }

    public void replayGame() {
        this.uiResume.onRelease();
        this.stage.replay();
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

    public Stage getStage() {
        return this.stage;
    }
}
