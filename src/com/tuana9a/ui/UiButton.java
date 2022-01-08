// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.ui;

import java.awt.Graphics2D;
import java.awt.Graphics;

import com.tuana9a.state.AppState;
import com.tuana9a.ui.ActionListener;
import com.tuana9a.animation.UiAnimation;
import com.tuana9a.ui.UiComponent;

public class UiButton extends UiComponent {
    private final UiAnimation animation;
    private final ActionListener actionListener;

    public UiButton(final AppState state, final double x, final double y, final int width, final int height, final UiAnimation animation, final ActionListener actionListener) {
        super(state, x, y, width, height);
        this.animation = animation;
        this.actionListener = actionListener;
    }

    @Override
    public void render(final Graphics g) {
        this.animation.render((Graphics2D) g.create(), this.status, this.x, this.y, this.width, this.height);
        super.render(g);
    }

    @Override
    public void onRelease() {
        this.actionListener.performAction();
    }
}
