package com.tuana9a.gemdino.screen;

import java.awt.*;

import com.tuana9a.gemdino.app.App;
import com.tuana9a.gemdino.graphic.Assets;
import com.tuana9a.gemdino.ui.UiImageStatic;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class ErrorScreen extends Screen {
    private final App app;
    public List<String> errorMessages;

    public ErrorScreen(App app) {
        this.app = app;
        this.errorMessages = new ArrayList<>();
    }

    public void addErrors(final String... messages) {
        this.errorMessages.addAll(Arrays.asList(messages));
    }

    public void addErrors(final List<String> messages) {
        this.errorMessages.addAll(messages);
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
        final int buttonSize = 50;
        final int iconSize = 64;
        final int barSize = 30;
        final int minSpace = 15;
        final UiImageStatic errorIcon = new UiImageStatic(this, halfW - iconSize / 2.0, minSpace, iconSize, iconSize, Assets.error);
        this.uiManager.addAllUiComponent(errorIcon);
    }

    @Override
    public void render() {
        if (!this.refreshTimer.isTime()) {
            return;
        }
        app.getDisplay().resetFrame();
        Graphics g = app.getDisplay().getGraphics();
        this.uiManager.renderAll(g);
        this.writeErrors(g);
        app.getDisplay().showFrame();
    }

    public void writeErrors(Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;
        final int screenW = app.getDisplayWidth();
        final int screenH = app.getDisplayHeight();
        final int halfW = screenW / 2;
        final int halfH = screenH / 2;
        final int quarterW = screenW / 4;
        final int quarterH = screenH / 4;
        final int threeQuarterW = halfW + quarterW;
        final int threeQuarterH = halfH + quarterH;
        final int height = 20;
        int currentY = 200;
        for (final String s : this.errorMessages) {
            final int width = g2d.getFontMetrics().stringWidth(s);
            g2d.drawString(s, halfW - width / 2, currentY);
            currentY += height;
        }
    }
}
