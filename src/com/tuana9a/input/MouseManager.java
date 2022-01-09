// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.input;

import java.awt.event.MouseEvent;

import com.tuana9a.App;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;

public class MouseManager implements MouseListener, MouseMotionListener {
    private static final MouseManager instance = new MouseManager();

    private int mouseX;
    private int mouseY;

    private MouseManager() {
    }

    public static MouseManager getInstance() {
        return instance;
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        App.getInstance().getCurrentState().getUiManager().checkMousePressAll();
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        App.getInstance().getCurrentState().getUiManager().checkMouseReleaseAll();
    }

    @Override
    public void mouseMoved(final MouseEvent e) {
        this.mouseX = e.getX();
        this.mouseY = e.getY();
    }

    @Override
    public void mouseDragged(final MouseEvent e) {
        this.mouseX = e.getX();
        this.mouseY = e.getY();
    }

    public int getX() {
        return this.mouseX;
    }

    public int getY() {
        return this.mouseY;
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    @Override
    public void mouseExited(final MouseEvent e) {
        KeyboardManager keyboardManager = KeyboardManager.getInstance();
        keyboardManager.update();
    }
}
