// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.ui;

import java.awt.Color;
import java.awt.Graphics;

import com.tuana9a.App;
import com.tuana9a.utils.Timer;

import java.awt.Rectangle;

import com.tuana9a.screen.BaseScreen;

public abstract class UiComponent {
    public static final int HIDDEN = -2;
    public static final int IN_ACTIVE = -1;
    public static final int NORMAL = 0;
    public static final int HOVER = 1;
    public static final int PRESS = 2;
    public static final int RELEASE = 3;
    private final BaseScreen currentScreen;
    protected double x;
    protected double y;
    protected double xRatio;
    protected double yRatio;
    protected int width;
    protected int height;
    protected Rectangle bounds;
    protected volatile boolean hovering;
    protected volatile boolean pressing;
    protected int status;
    public Timer typicalTime;

    public UiComponent(final BaseScreen currentScreen, final double x, final double y, final int width, final int height) {
        this.currentScreen = currentScreen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.typicalTime = new Timer();
        this.setBounds(0, 0, width, height);
        this.xRatio = (x + width / 2.0) / currentScreen.getDisplayWidth();
        this.yRatio = (y + height / 2.0) / currentScreen.getDisplayHeight();
        this.status = 0;
    }

    public void update() {
    }

    public void render(final Graphics g) {
        App app = App.getInstance();
        if (app.getKeyboardManager().innerBoundMode) {
            this.renderInnerBound(g);
        }
    }

    public void renderInnerBound(final Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect((int) this.x + this.bounds.x, (int) this.y + this.bounds.y, this.bounds.width, this.bounds.height);
        g.setColor(Color.RED);
        g.drawRect((int) this.x + this.bounds.x, (int) this.y + this.bounds.y, this.bounds.width, this.bounds.height);
    }

    public void updateWhenScreenResize() {
        this.x = this.xRatio * this.currentScreen.getDisplayWidth() - this.width / 2.0;
        this.y = this.yRatio * this.currentScreen.getDisplayHeight() - this.height / 2.0;
    }

    public void checkMouseHover() {
        App app = App.getInstance();
        final int mouseX = app.getMouseManager().getX();
        final int mouseY = app.getMouseManager().getY();
        if (new Rectangle((int) (this.x + this.bounds.x), (int) (this.y + this.bounds.y), this.bounds.width, this.bounds.height).contains(mouseX, mouseY)) {
            this.hovering = true;
            if (!this.pressing) {
                this.status = 1;
            }
            this.onHover();
        } else {
            this.hovering = false;
            this.pressing = false;
            this.status = 0;
        }
    }

    public void checkMousePress() {
        if (this.hovering) {
            this.pressing = true;
            this.status = 2;
            this.onPress();
        }
    }

    public void checkMouseRelease() {
        if (this.pressing) {
            this.status = 3;
            this.onRelease();
            this.pressing = false;
        }
    }

    public void onPress() {
    }

    public void onRelease() {
    }

    public void onHover() {
    }

    public void hide() {
        this.status = -2;
        this.hovering = false;
        this.pressing = false;
    }

    public void show() {
        this.status = 0;
    }

    public boolean isHidden() {
        return this.status == -2;
    }

    public boolean isInActive() {
        return this.status == -1 || this.status == -2;
    }

    public void setBounds(final int bX, final int bY, final int bW, final int bH) {
        this.bounds = new Rectangle(bX, bY, bW, bH);
    }

    public void setX(final double x) {
        this.x = x;
    }

    public void setY(final double y) {
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public int getW() {
        return this.width;
    }

    public int getH() {
        return this.height;
    }
}
