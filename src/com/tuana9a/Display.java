// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a;

import java.awt.Toolkit;
import java.awt.Canvas;
import javax.swing.JFrame;
import java.awt.Dimension;

public class Display
{
    private static int MAX_WIDTH;
    private static int MAX_HEIGHT;
    private static int PRE_WIDTH;
    private static int PRE_HEIGHT;
    private static Dimension[] screenResolution;
    private int currentRes;
    private boolean fullScreen;
    private App app;
    private JFrame jFrame;
    private Canvas canvas;
    private String tittle;
    private int width;
    private int height;
    
    public Display(final App app, final String tittle, final int width, final int height) {
        this.app = app;
        this.tittle = tittle;
        this.width = width;
        this.height = height;
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Display.MAX_WIDTH = screenSize.width;
        Display.MAX_HEIGHT = screenSize.height;
        Display.PRE_WIDTH = width;
        Display.PRE_HEIGHT = height;
        (this.jFrame = new JFrame(tittle)).setDefaultCloseOperation(3);
        this.jFrame.setResizable(false);
        (this.canvas = new Canvas()).setFocusable(false);
        this.resize(width, height);
        this.jFrame.add(this.canvas);
        this.jFrame.setVisible(true);
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
        this.app.menuState.getUiManager().updateAllWhenScreenResize();
        this.app.gameState.getUiManager().updateAllWhenScreenResize();
        this.app.errorState.getUiManager().updateAllWhenScreenResize();
        this.app.loadState.getUiManager().updateAllWhenScreenResize();
    }
    
    public void toggleScreenSize() {
        final Dimension d = Display.screenResolution[this.currentRes++];
        this.resize(d.width, d.height);
        ++this.currentRes;
        if (this.currentRes > Display.screenResolution.length) {
            this.currentRes = 0;
        }
    }
    
    public void fullScreen() {
        if (this.fullScreen) {
            return;
        }
        this.fullScreen = true;
        this.jFrame.dispose();
        this.jFrame.setUndecorated(true);
        this.jFrame.setExtendedState(6);
        this.resize(Display.MAX_WIDTH, Display.MAX_HEIGHT);
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
        this.resize(Display.PRE_WIDTH, Display.PRE_HEIGHT);
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
    
    static {
        Display.screenResolution = new Dimension[] { new Dimension(900, 600), new Dimension(1280, 720), new Dimension(1920, 1080) };
    }
}
