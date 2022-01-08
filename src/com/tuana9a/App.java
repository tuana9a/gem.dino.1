// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a;

import com.tuana9a.graphic.Assets;
import com.tuana9a.input.MouseManager;
import com.tuana9a.input.KeyboardManager;
import com.tuana9a.state.LoadState;
import com.tuana9a.state.ErrorState;
import com.tuana9a.state.MenuState;
import com.tuana9a.state.GameState;
import com.tuana9a.state.AppState;

public class App implements Runnable
{
    private Thread thread;
    private boolean running;
    private Display display;
    public String tittle;
    private volatile AppState currentState;
    private AppState lastState;
    public GameState gameState;
    public MenuState menuState;
    public ErrorState errorState;
    public LoadState loadState;
    private KeyboardManager keyboardManager;
    private MouseManager mouseManager;
    
    public App(final String tittle) {
        this.running = false;
        this.tittle = tittle;
        this.display = new Display(this, tittle, 900, 600);
        Assets.firstLoad();
        this.loadState = new LoadState(this);
        this.gameState = new GameState(this);
        this.menuState = new MenuState(this);
        this.errorState = new ErrorState(this);
        this.switchToState(this.menuState);
        this.switchToState(this.loadState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Assets.loadEveryThingLeft();
            }
        }).start();
        this.loadState.initLoadState(Assets.loading);
        this.mouseManager = new MouseManager(this);
        this.keyboardManager = new KeyboardManager(this);
        this.display.getFrame().addKeyListener(this.keyboardManager);
        this.display.getCanvas().addMouseListener(this.mouseManager);
        this.display.getCanvas().addMouseMotionListener(this.mouseManager);
    }
    
    public synchronized void start() {
        if (this.running) {
            return;
        }
        this.running = true;
        (this.thread = new Thread(this)).start();
    }
    
    @Override
    public void run() {
        while (this.running) {
            if (this.currentState instanceof GameState) {
                if (!this.display.getFrame().isFocused()) {
                    ((GameState)this.currentState).setFps(10);
                }
                else {
                    ((GameState)this.currentState).setFps(100);
                }
            }
            synchronized (this) {
                this.update();
                this.render();
            }
            try {
                Thread.sleep(1L);
            }
            catch (Exception ex) {}
        }
        this.stop();
    }
    
    private void update() {
        if (this.currentState != null) {
            this.currentState.update();
        }
    }
    
    private void render() {
        if (this.currentState != null) {
            this.currentState.render();
        }
    }
    
    public synchronized void stop() {
        if (!this.running) {
            return;
        }
        this.running = false;
        try {
            this.thread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public MouseManager getMouseManager() {
        return this.mouseManager;
    }
    
    public KeyboardManager getKeyboardManager() {
        return this.keyboardManager;
    }
    
    public AppState getCurrentState() {
        return this.currentState;
    }
    
    public void switchToLastState() {
        this.switchToState(this.lastState);
    }
    
    public void switchToState(final AppState whichState) {
        if (this.currentState != whichState) {
            this.lastState = this.currentState;
            (this.currentState = whichState).changeCursor();
            this.currentState.changeFont();
        }
    }
    
    public Display getDisplay() {
        return this.display;
    }
}
