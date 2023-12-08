package com.tuana9a.gemdino.app;

import com.tuana9a.gemdino.interfaces.EventHandler;
import com.tuana9a.gemdino.graphic.Assets;
import com.tuana9a.gemdino.input.MouseManager;
import com.tuana9a.gemdino.input.KeyboardManager;
import com.tuana9a.gemdino.screen.*;
import com.tuana9a.gemdino.utils.Timer;
import lombok.Getter;

public class App {
    @Getter
    private Display display;
    private boolean running;
    @Getter
    private MenuScreen menuScreen;
    @Getter
    private GameScreen gameScreen;
    @Getter
    private LoadingScreen loadingScreen;
    @Getter
    private ErrorScreen errorScreen;
    @Getter
    private Screen currentScreen;
    private Screen previousScreen;
    private final Timer refreshTimer;

    public App() {
        this.running = false;
        this.refreshTimer = new Timer(2);
        Assets.loadAll();
        this.display = new Display(this, "GemDino", 1280, 720);
        KeyboardManager keyboardManager = KeyboardManager.getInstance();
        MouseManager mouseManager = MouseManager.getInstance();
        display.getFrame().addKeyListener(keyboardManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
        this.errorScreen = new ErrorScreen();
        this.loadingScreen = new LoadingScreen(this);
        this.menuScreen = new MenuScreen(this);
        this.gameScreen = new GameScreen(this);
    }

    public void run() {
        this.running = true;
        // init switch last screen
        this.switchScreen(menuScreen);
        this.switchScreen(menuScreen);
        while (this.running) {
            try {
                Thread.sleep(2); // TODO: need to optimize
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!refreshTimer.isTime()) {
                continue;
            }
            refreshTimer.reset();
            final EventQueue eventQueue = EventQueue.getInstance();
            synchronized (this) { // TODO: is it possible to non-blocking
                this.currentScreen.update();
                this.currentScreen.render();
                EventHandler event = eventQueue.pop();
                while (event != null) {
                    event.perform();
                    event = eventQueue.pop();
                }
            }
        }
    }

    public void stop() {
        this.running = false;
    }

    public void switchToLastScreen() {
        this.switchScreen(this.previousScreen);
    }

    public void switchScreen(final Screen screen) {
        synchronized (this) {
            if (this.currentScreen != screen) {
                this.previousScreen = this.currentScreen;
                (this.currentScreen = screen).changeCursor();
                this.currentScreen.changeFont();
            }
        }
    }

    public int getDisplayWidth() {
        return this.display.getWidth();
    }

    public int getDisplayHeight() {
        return this.display.getHeight();
    }
}
