// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.ui.transition;

import java.awt.Graphics2D;
import java.awt.Graphics;
import com.tuana9a.state.AppState;
import com.tuana9a.graphic.animation.UiAnimation;
import com.tuana9a.ui.UiComponent;

public class UiImageAnimation extends UiComponent
{
    UiAnimation uiAnimation;
    
    public UiImageAnimation(final AppState currentAppState, final double x, final double y, final int width, final int height, final UiAnimation a) {
        super(currentAppState, x, y, width, height);
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
