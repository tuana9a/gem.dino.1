// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a;

import com.tuana9a.graphic.Assets;
import com.tuana9a.input.MouseManager;
import com.tuana9a.input.KeyboardManager;
import com.tuana9a.state.LoadState;
import com.tuana9a.state.MenuState;
import com.tuana9a.state.AppState;

public class App implements Runnable {
    private static final App instance = new App("GemDino", 1280, 720);

    private boolean running;
    public String tittle;
    private AppState currentState;
    private AppState lastState;

    public static App getInstance() {
        return instance;
    }

    private App(final String tittle, int width, int height) {
        this.running = false;
        this.tittle = tittle;
        Assets.firstLoad();
        Assets.loadEveryThingLeft();
        Display display = Display.getInstance();
        KeyboardManager keyboardManager = KeyboardManager.getInstance();
        MouseManager mouseManager = MouseManager.getInstance();
        display.getFrame().addKeyListener(keyboardManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
    }

    @Override
    public void run() {
        this.running = true;
        LoadState loadState = LoadState.getInstance();
        this.switchToState(MenuState.getInstance());
        this.switchToState(LoadState.getInstance());
        loadState.initLoadState(Assets.loading);
        while (this.running) {
            synchronized (this) {
                this.currentState.update();
                this.currentState.render();
            }
            try {
                // TODO: tobe clean
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        this.running = false;
    }

    // TODO: clean up
    public MouseManager getMouseManager() {
        return MouseManager.getInstance();
    }

    // TODO: clean up
    public KeyboardManager getKeyboardManager() {
        return KeyboardManager.getInstance();
    }

    public AppState getCurrentState() {
        return this.currentState;
    }

    public void switchToLastState() {
        this.switchToState(this.lastState);
    }

    public void switchToState(final AppState whichState) {
        synchronized (this) {
            if (this.currentState != whichState) {
                this.lastState = this.currentState;
                (this.currentState = whichState).changeCursor();
                this.currentState.changeFont();
            }
        }
    }

}
