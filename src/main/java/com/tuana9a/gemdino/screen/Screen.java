package com.tuana9a.gemdino.screen;

import com.tuana9a.gemdino.app.Display;
import com.tuana9a.gemdino.ui.UiManager;
import com.tuana9a.gemdino.utils.Timer;
import lombok.Getter;

import java.awt.*;

public class Screen {
    public static final long DEFAULT_REFRESH_TIME = 10L;
    protected Timer refreshTimer;
    // getter setter
    @Getter
    protected UiManager uiManager;

    public Screen() {
        this.refreshTimer = new Timer(DEFAULT_REFRESH_TIME);
        this.uiManager = new UiManager();
        this.initUi();
    }

    // TODO: strategy pattern
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
}
