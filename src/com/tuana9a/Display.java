// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a;

import com.tuana9a.state.ErrorState;
import com.tuana9a.state.GameState;
import com.tuana9a.state.LoadState;
import com.tuana9a.state.MenuState;

import java.awt.Toolkit;
import java.awt.Canvas;
import javax.swing.*;
import java.awt.Dimension;

public class Display {
    private static final Display instance = new Display("GemDino", 1280, 720);

    private final int MAX_WIDTH;
    private final int MAX_HEIGHT;
    private final int PRE_WIDTH;
    private final int PRE_HEIGHT;

    private boolean fullScreen;
    private final JFrame jFrame;
    private final Canvas canvas;
    private String tittle;
    private int width;
    private int height;

    public Display(final String tittle, final int width, final int height) {
        this.tittle = tittle;
        this.width = width;
        this.height = height;
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.MAX_WIDTH = screenSize.width;
        this.MAX_HEIGHT = screenSize.height;
        this.PRE_WIDTH = width;
        this.PRE_HEIGHT = height;
        this.jFrame = new JFrame(tittle);
        this.jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.jFrame.setResizable(false);
        this.canvas = new Canvas();
        this.canvas.setFocusable(false);
        this.resize(width, height);
        this.jFrame.add(this.canvas);
        this.jFrame.setVisible(true);
    }

    public static Display getInstance() {
        return instance;
    }

    private void resize(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.canvas.setSize(width, height);
        this.jFrame.getContentPane().setPreferredSize(new Dimension(width, height));
        this.jFrame.pack();
        this.jFrame.setLocationRelativeTo(null);
    }

    private void updateUiPositionWhenResize() {
        MenuState.getInstance().getUiManager().updateAllWhenScreenResize();
        GameState.getInstance().getUiManager().updateAllWhenScreenResize();
        ErrorState.getInstance().getUiManager().updateAllWhenScreenResize();
        LoadState.getInstance().getUiManager().updateAllWhenScreenResize();
    }

    public void fullScreen() {
        if (this.fullScreen) {
            return;
        }
        this.fullScreen = true;
        this.jFrame.dispose();
        this.jFrame.setUndecorated(true);
        this.jFrame.setExtendedState(6);
        this.resize(this.MAX_WIDTH, this.MAX_HEIGHT);
        this.jFrame.setVisible(true);
        this.updateUiPositionWhenResize();
    }

    public void exitFullScreen() {
        if (!this.fullScreen) {
            return;
        }
        this.fullScreen = false;
        this.jFrame.dispose();
        this.jFrame.setUndecorated(false);
        this.jFrame.setExtendedState(0);
        this.resize(this.PRE_WIDTH, this.PRE_HEIGHT);
        this.jFrame.setVisible(true);
        this.updateUiPositionWhenResize();
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public JFrame getFrame() {
        return this.jFrame;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }
}
