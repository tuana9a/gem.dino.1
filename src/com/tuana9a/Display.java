// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a;

import com.tuana9a.screen.ErrorScreen;
import com.tuana9a.screen.GameScreen;
import com.tuana9a.screen.LoadScreen;
import com.tuana9a.screen.MenuScreen;

import java.awt.*;
import javax.swing.*;

public class Display {
    private static final Display instance = new Display("GemDino", 1280, 720);

    private String tittle;
    private int width;
    private int height;

    private final int MAX_WIDTH;
    private final int MAX_HEIGHT;
    private final int PRE_WIDTH;
    private final int PRE_HEIGHT;

    private boolean fullScreen;
    private final JFrame jFrame;
    private final Canvas canvas;
    private Graphics graphics;


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
        this.canvas.createBufferStrategy(3);
    }

    public static Display getInstance() {
        return instance;
    }

    public void resetFrame() {
        Display display = Display.getInstance();
        this.graphics = display.getCanvas().getBufferStrategy().getDrawGraphics();
        this.graphics.clearRect(0, 0, display.getWidth(), display.getHeight());
    }

    public void showFrame() {
        Display display = Display.getInstance();
        display.getCanvas().getBufferStrategy().show();
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
        MenuScreen.getInstance().getUiManager().updateAllWhenScreenResize();
        GameScreen.getInstance().getUiManager().updateAllWhenScreenResize();
        ErrorScreen.getInstance().getUiManager().updateAllWhenScreenResize();
        LoadScreen.getInstance().getUiManager().updateAllWhenScreenResize();
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

    // getter setter
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

    public Graphics getGraphics() {
        return graphics;
    }
}
