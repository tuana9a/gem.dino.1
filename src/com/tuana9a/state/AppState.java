// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.state;

import com.tuana9a.Display;
import com.tuana9a.ui.UiManager;
import com.tuana9a.utils.Timer;

import java.awt.*;

public abstract class AppState {
    public static final long DEFAULT_REFRESH_TIME = 10L;
    protected Graphics graphics;
    protected Timer refreshTimer;
    protected UiManager uiManager;

    public AppState() {
        this.refreshTimer = new Timer(DEFAULT_REFRESH_TIME);
        this.uiManager = new UiManager();
        Display.getInstance().getCanvas().createBufferStrategy(3);
        this.initUi();
    }

    public abstract void initUi();

    public void changeCursor() {
    }

    public void changeFont() {
    }

    public abstract void update();

    public abstract void render();

    public UiManager getUiManager() {
        return this.uiManager;
    }

    protected void resetFrame() {
        Display display = Display.getInstance();
        this.graphics = display.getCanvas().getBufferStrategy().getDrawGraphics();
        this.graphics.clearRect(0, 0, display.getWidth(), display.getHeight());
    }

    protected void showFrame() {
        Display display = Display.getInstance();
        display.getCanvas().getBufferStrategy().show();
        this.graphics.dispose();
    }

    public int getDisplayWidth() {
        return Display.getInstance().getWidth();
    }

    public int getDisplayHeight() {
        return Display.getInstance().getHeight();
    }

}
