// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.screen;

import com.tuana9a.app.Display;
import com.tuana9a.ui.UiManager;
import com.tuana9a.utils.Timer;

import java.awt.*;

public class BaseScreen {
    public static final long DEFAULT_REFRESH_TIME = 10L;
    protected Timer refreshTimer;
    protected UiManager uiManager;

    public BaseScreen() {
        this.refreshTimer = new Timer(DEFAULT_REFRESH_TIME);
        this.uiManager = new UiManager();
        this.initUi();
    }

    public void initUi() {

    }

    public void changeCursor() {
    }

    public void changeFont() {
    }

    public void update() {

    }

    public void render() {

    }

    protected void resetFrame() {
        Display display = Display.getInstance();
        display.resetFrame();
    }

    protected void showFrame() {
        Display display = Display.getInstance();
        display.showFrame();
    }

    protected Graphics getGraphics() {
        Display display = Display.getInstance();
        return display.getGraphics();
    }

    // getter setter
    public UiManager getUiManager() {
        return this.uiManager;
    }

    public int getDisplayWidth() {
        return Display.getInstance().getWidth();
    }

    public int getDisplayHeight() {
        return Display.getInstance().getHeight();
    }

}
