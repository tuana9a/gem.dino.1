package gemdino.screen;

import gemdino.animation.UiAnimation;
import gemdino.app.App;
import gemdino.graphic.Assets;
import gemdino.interfaces.EventHandler;
import gemdino.ui.UiButton;
import gemdino.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuScreen extends Screen {
    private final App app;

    public MenuScreen(App app) {
        this.app = app;
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
        final int buttonSize = 100;
        final int iconSize = 64;
        final int barSize = 30;
        final int minSpace = 15;
        final UiButton startGame = new UiButton(app, this, halfW - buttonSize / 2.0, halfH - buttonSize / 2.0, buttonSize, buttonSize, new UiAnimation(Assets.newGameButton), new EventHandler() {
            @Override
            public void perform() {
                synchronized (app) {
                    GameScreen gameScreen = app.getGameScreen();
                    app.switchScreen(gameScreen);
                    gameScreen.startGame();
                }
            }
        });
        final UiButton exit = new UiButton(app, this, screenW - minSpace - buttonSize, minSpace, buttonSize, buttonSize, new UiAnimation(Assets.exitButton), new EventHandler() {
            @Override
            public void perform() {
                synchronized (app) {
                    System.exit(0);
                }
            }
        });
        final UiButton music = new UiButton(app, this, minSpace, minSpace, buttonSize, buttonSize, new UiAnimation(Assets.musicButton), new EventHandler() {
            @Override
            public void perform() {
            }
        });
        final UiButton sound = new UiButton(app, this, minSpace, minSpace + buttonSize, buttonSize, buttonSize, new UiAnimation(Assets.soundButton), new EventHandler() {
            @Override
            public void perform() {
            }
        });
        final UiButton fullScreen = new UiButton(app, this, minSpace, minSpace + 2 * buttonSize, buttonSize, buttonSize, new UiAnimation(Assets.largerScreenButton), new EventHandler() {
            @Override
            public void perform() {
                app.getDisplay().fullScreen();
            }
        });
        final UiButton smallScreen = new UiButton(app, this, minSpace, minSpace + 3 * buttonSize, buttonSize, buttonSize, new UiAnimation(Assets.smallerScreenButton), new EventHandler() {
            @Override
            public void perform() {
                app.getDisplay().exitFullScreen();
            }
        });
        this.uiManager.addAllUiComponent(startGame, exit, music, sound, fullScreen, smallScreen);
    }

    @Override
    public void changeCursor() {
        final BufferedImage img = Utils.loadImg("resources/ui/cursor/ui_pointer.png");
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension d = toolkit.getBestCursorSize(32, 32);
        final Cursor cursor = toolkit.createCustomCursor(img, new Point(d.width / 2, d.height / 2), "img");
        app.getDisplay().getCanvas().setCursor(cursor);
    }

    @Override
    public void update() {
        if (!this.refreshTimer.isTime()) {
            return;
        }
        this.uiManager.updateAll();
    }

    @Override
    public void render() {
        if (!this.refreshTimer.isTime()) {
            return;
        }
        app.getDisplay().resetFrame();
        this.refreshTimer.reset();
        this.uiManager.renderAll(app.getDisplay().getGraphics());
        app.getDisplay().showFrame();
    }
}
