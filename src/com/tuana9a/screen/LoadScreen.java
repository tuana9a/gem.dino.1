// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.screen;

import java.awt.*;
import java.util.ArrayList;

import com.tuana9a.animation.UiAnimation;
import com.tuana9a.graphic.Assets;
import com.tuana9a.App;
import com.tuana9a.ui.UiImageAnimation;
import com.tuana9a.ui.UiProgressBar;
import com.tuana9a.utils.Timer;
import com.tuana9a.utils.Loading;

public class LoadScreen extends BaseScreen {
    private static final LoadScreen instance = new LoadScreen();

    public static final long MAX_TIME_OUT = 5000L;
    public Loading loading;
    public Timer loadTimeOut;
    public double transitionWidth;
    public double destinationWidth;
    private UiProgressBar uiProgressBar;
    private UiImageAnimation uiProgressAnimation;

    private LoadScreen() {
    }

    public static LoadScreen getInstance() {
        return instance;
    }

    public void initLoadState(final Loading loading) {
        this.loading = loading;
        this.transitionWidth = 0.0;
        this.destinationWidth = 0.0;
        this.uiProgressBar.initProcess(loading.fullProcess, loading.currentProgress);
        this.loadTimeOut = new Timer(5000L);
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
        this.uiProgressBar = new UiProgressBar(this, quarterW, threeQuarterH, halfW, (int) (barSize * 1.25), Assets.progressBarLeft, Assets.progressBarMid, Assets.progressBarRight);
        this.uiProgressAnimation = new UiImageAnimation(this, quarterW, threeQuarterH - 70, 120, 60, new UiAnimation(Assets.loadProgressAnimation, 50));
        this.uiManager.addAllUiComponent(this.uiProgressBar, this.uiProgressAnimation);
    }

    @Override
    public void update() {
        if (!this.refreshTimer.isTime()) {
            return;
        }
        this.uiManager.updateAll();
        final int tempWidth = this.uiProgressAnimation.getW();
        final int tempHeight = this.uiProgressAnimation.getH();
        final int current = this.loading.currentProgress;
        final int full = this.loading.fullProcess;
        this.destinationWidth = current / (double) full * this.uiProgressBar.getW();
        if (this.destinationWidth >= this.transitionWidth) {
            this.transitionWidth += 2.0;
        }
        this.uiProgressAnimation.setX((int) (this.uiProgressBar.getX() + this.uiProgressBar.getSideBarWidth() + this.transitionWidth - tempWidth / 2));
        this.uiProgressBar.updateCurrentProgress(current);
        if (current >= full && this.transitionWidth >= this.destinationWidth) {
            if (this.loading.loadSuccess) {
                this.onComplete();
            } else {
                this.onError(this.loading.errorMessages);
            }
            this.loadTimeOut.reset();
        }
        if (this.loadTimeOut.isTime() && !this.loading.loadSuccess) {
            this.onError(this.loading.errorMessages);
        }
    }

    @Override
    public void render() {
        if (!this.refreshTimer.isTime()) {
            return;
        }
        this.refreshTimer.reset();
        resetFrame();
        Graphics g = getGraphics();
        this.uiManager.renderAll(g);
        showFrame();
    }

    public void onError(final ArrayList<String> messages) {
        App app = App.getInstance();
        ErrorScreen errorScreen = ErrorScreen.getInstance();
        app.switchToState(errorScreen);
        errorScreen.addErrors(messages);
    }

    public void onComplete() {
        App app = App.getInstance();
        app.switchToLastState();
    }
}
