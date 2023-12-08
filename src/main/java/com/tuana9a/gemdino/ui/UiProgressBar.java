package com.tuana9a.gemdino.ui;

import java.awt.Graphics;

import com.tuana9a.gemdino.utils.Utils;
import com.tuana9a.gemdino.screen.Screen;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class UiProgressBar extends UiComponent
{
    private BufferedImage leftBarImg;
    private BufferedImage midBarImg;
    private BufferedImage rightBarImg;
    private Color barColor;
    private final int midBarFullWidth;
    private final int sideBarWidth;
    private int fullProgress;
    private int currentProgress;
    
    public UiProgressBar(final Screen state, final double x, final double y, final int width, final int height, final BufferedImage leftBarImg, final BufferedImage midBarImg, final BufferedImage rightBarImg) {
        super(state, x, y, width, height);
        this.sideBarWidth = height / 4;
        this.midBarFullWidth = width - 2 * this.sideBarWidth;
        this.initImage(leftBarImg, midBarImg, rightBarImg);
    }
    
    public void initImage(final BufferedImage leftBar, final BufferedImage midBar, final BufferedImage rightBar) {
        this.leftBarImg = leftBar;
        this.midBarImg = midBar;
        this.rightBarImg = rightBar;
        this.barColor = Utils.getColor(midBar);
    }
    
    public void initProcess(final int fullProcess, final int currentProcess) {
        this.fullProgress = fullProcess;
        this.currentProgress = currentProcess;
    }
    
    public void updateCurrentProgress(final int progress) {
        this.currentProgress = Math.min(progress, this.fullProgress);
    }
    
    @Override
    public void render(final Graphics g) {
        g.setColor(this.barColor);
        g.drawImage(this.leftBarImg, (int)this.x, (int)this.y, this.sideBarWidth, this.height, null);
        g.drawImage(this.rightBarImg, (int)(this.x + this.width - this.sideBarWidth), (int)this.y, this.sideBarWidth, this.height, null);
        this.renderProgressBar(g, this.currentProgress / (double)this.fullProgress);
    }
    
    private void renderProgressBar(final Graphics g, final double ratioPercent) {
        g.drawLine((int)(this.x + this.sideBarWidth), (int)this.y, (int)(this.x + this.sideBarWidth + this.midBarFullWidth), (int)this.y);
        g.drawLine((int)(this.x + this.sideBarWidth), (int)this.y + this.height, (int)(this.x + this.sideBarWidth + this.midBarFullWidth), (int)this.y + this.height);
        g.drawImage(this.midBarImg, (int)(this.x + this.sideBarWidth), (int)this.y, (int)(this.midBarFullWidth * ratioPercent), this.height, null);
    }
    
    public int getSideBarWidth() {
        return this.sideBarWidth;
    }
}
