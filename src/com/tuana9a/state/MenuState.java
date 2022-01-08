// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.state;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.Toolkit;
import com.tuana9a.utility.Utility;
import com.tuana9a.ui.transition.UiButton;
import com.tuana9a.ui.ActionListener;
import com.tuana9a.graphic.animation.UiAnimation;
import com.tuana9a.graphic.Assets;
import com.tuana9a.App;

public class MenuState extends AppState
{
    public MenuState(final App app) {
        super(app);
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
        final int buttonSize = 100;
        final int iconSize = 64;
        final int barSize = 30;
        final int minSpace = 15;
        final UiButton startGame = new UiButton(this, halfW - buttonSize / 2.0, halfH - buttonSize / 2.0, buttonSize, buttonSize, new UiAnimation(Assets.newGameButton), new ActionListener() {
            @Override
            public void performAction() {
                synchronized (MenuState.this.app) {
                    MenuState.this.app.switchToState(MenuState.this.app.gameState);
                    MenuState.this.app.gameState.startGame();
                }
            }
        });
        final UiButton exit = new UiButton(this, screenW - minSpace - buttonSize, minSpace, buttonSize, buttonSize, new UiAnimation(Assets.exitButton), new ActionListener() {
            @Override
            public void performAction() {
                synchronized (MenuState.this.app) {
                    System.exit(0);
                }
            }
        });
        final UiButton music = new UiButton(this, minSpace, minSpace, buttonSize, buttonSize, new UiAnimation(Assets.musicButton), new ActionListener() {
            @Override
            public void performAction() {
            }
        });
        final UiButton sound = new UiButton(this, minSpace, minSpace + buttonSize, buttonSize, buttonSize, new UiAnimation(Assets.soundButton), new ActionListener() {
            @Override
            public void performAction() {
            }
        });
        final UiButton fullScreen = new UiButton(this, minSpace, minSpace + 2 * buttonSize, buttonSize, buttonSize, new UiAnimation(Assets.largerScreenButton), new ActionListener() {
            @Override
            public void performAction() {
                MenuState.this.app.getDisplay().fullScreen();
            }
        });
        final UiButton smallScreen = new UiButton(this, minSpace, minSpace + 3 * buttonSize, buttonSize, buttonSize, new UiAnimation(Assets.smallerScreenButton), new ActionListener() {
            @Override
            public void performAction() {
                MenuState.this.app.getDisplay().exitFullScreen();
            }
        });
        this.uiManager.addAllUiComponent(startGame, exit, music, sound, fullScreen, smallScreen);
    }
    
    @Override
    public void changeCursor() {
        final BufferedImage img = Utility.loadImg("resources/ui/cursor/ui_pointer.png");
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension d = toolkit.getBestCursorSize(32, 32);
        final Cursor cursor = toolkit.createCustomCursor(img, new Point(d.width / 2, d.height / 2), "img");
        this.display.getCanvas().setCursor(cursor);
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
        this.refreshTimer.reset();
        this.clearOldFrameAndRenewIt();
        this.uiManager.renderAll(this.g);
        this.showFrameToScreen();
    }
}
