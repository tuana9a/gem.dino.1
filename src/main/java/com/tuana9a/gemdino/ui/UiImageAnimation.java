package com.tuana9a.gemdino.ui;

import java.awt.Graphics2D;
import java.awt.Graphics;
import com.tuana9a.gemdino.screen.Screen;
import com.tuana9a.gemdino.animation.UiAnimation;

public class UiImageAnimation extends UiComponent
{
    UiAnimation uiAnimation;
    
    public UiImageAnimation(final Screen currentScreen, final double x, final double y, final int width, final int height, final UiAnimation a) {
        super(currentScreen, x, y, width, height);
        this.uiAnimation = a;
    }
    
    @Override
    public void update() {
        this.uiAnimation.update();
    }
    
    @Override
    public void render(final Graphics g) {
        this.uiAnimation.render((Graphics2D)g.create(), this.status, this.x, this.y, this.width, this.height);
    }
}
