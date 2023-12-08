package com.tuana9a.gemdino.screen;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tuana9a.gemdino.animation.UiAnimation;
import com.tuana9a.gemdino.app.App;
import com.tuana9a.gemdino.graphic.Assets;
import com.tuana9a.gemdino.ui.UiImageAnimation;
import com.tuana9a.gemdino.ui.UiProgressBar;
import com.tuana9a.gemdino.utils.Loading;
import com.tuana9a.gemdino.utils.Timer;
import lombok.Getter;

public class LoadingScreen extends Screen {
    private App app;
    public static final long MAX_TIME_OUT = 5000L;
    public Loading loading;
    public Timer loadTimeOut;
    public double transitionWidth;
    public double destinationWidth;
    private UiProgressBar uiProgressBar;
    private UiImageAnimation uiProgressAnimation;
    @Getter
    private ExecutorService executorService;

    public LoadingScreen(App app) {
        this.app = app;
        this.executorService = Executors.newFixedThreadPool(4);
    }

    public void create(final Loading loading) {
        this.loading = loading;
        this.transitionWidth = 0.0;
        this.destinationWidth = 0.0;
        this.uiProgressBar.initProcess(loading.fullProcess, loading.currentProgress);
        this.loadTimeOut = new Timer(5000L);
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
        app.getDisplay().resetFrame();
        Graphics g = app.getDisplay().getGraphics();
        this.uiManager.renderAll(g);
        app.getDisplay().showFrame();
    }

    public void onError(final ArrayList<String> messages /* TODO: LinkedList instead */) {
        ErrorScreen errorScreen = app.getErrorScreen();
        app.switchScreen(errorScreen);
        errorScreen.addErrors(messages);
    }

    public void onComplete() {
        app.switchToLastScreen();
    }
}
