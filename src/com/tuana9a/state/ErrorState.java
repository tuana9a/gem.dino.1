// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.state;

import java.awt.Graphics2D;

import com.tuana9a.ui.UiImageStatic;
import com.tuana9a.graphic.Assets;

import java.util.Arrays;
import java.util.ArrayList;

public class ErrorState extends AppState {
    private static final ErrorState instance = new ErrorState();

    public ArrayList<String> errorMessages;

    private ErrorState() {
        this.errorMessages = new ArrayList<>();
    }

    public static ErrorState getInstance() {
        return instance;
    }

    public void addErrors(final String... messages) {
        this.errorMessages.addAll(Arrays.asList(messages));
    }

    public void addErrors(final ArrayList<String> ls) {
        this.errorMessages.addAll(ls);
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
        final int buttonSize = 50;
        final int iconSize = 64;
        final int barSize = 30;
        final int minSpace = 15;
        final UiImageStatic errorIcon = new UiImageStatic(this, halfW - iconSize / 2.0, minSpace, iconSize, iconSize, Assets.error);
        this.uiManager.addAllUiComponent(errorIcon);
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        if (!this.refreshTimer.isTime()) {
            return;
        }
        this.resetFrame();
        this.uiManager.renderAll(this.graphics);
        this.writeErrors();
        this.showFrame();
    }

    public void writeErrors() {
        final Graphics2D g2d = (Graphics2D) this.graphics;
        final int screenW = this.getDisplayWidth();
        final int screenH = this.getDisplayHeight();
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
