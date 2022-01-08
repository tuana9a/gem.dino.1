// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.state;

import com.tuana9a.input.KeyboardManager;
import com.tuana9a.input.MouseManager;
import com.tuana9a.ui.UiManager;
import com.tuana9a.utils.TimeSystem;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import com.tuana9a.Display;
import com.tuana9a.App;

public abstract class AppState
{
    public static final long FIXED_REFRESH_TIME = 10L;
    protected final App app;
    protected Display display;
    protected BufferStrategy bs;
    protected Graphics g;
    Font font;
    protected TimeSystem refreshTimer;
    protected UiManager uiManager;
    
    public AppState(final App app) {
        this.refreshTimer = new TimeSystem(10L);
        this.app = app;
        this.display = app.getDisplay();
        this.display.getCanvas().createBufferStrategy(3);
        this.uiManager = new UiManager();
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
    
    protected void clearOldFrameAndRenewIt() {
        if (this.bs == null) {
            this.bs = this.display.getCanvas().getBufferStrategy();
        }
        (this.g = this.bs.getDrawGraphics()).clearRect(0, 0, this.display.getWidth(), this.display.getHeight());
    }
    
    protected void showFrameToScreen() {
        this.bs.show();
        this.g.dispose();
    }
    
    public MouseManager getMouseManager() {
        return this.app.getMouseManager();
    }
    
    public KeyboardManager getKeyboardManager() {
        return this.app.getKeyboardManager();
    }
    
    public int getDisplayWidth() {
        return this.display.getWidth();
    }
    
    public int getDisplayHeight() {
        return this.display.getHeight();
    }
    
    public App getApp() {
        return this.app;
    }
}
