// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.ui;

import java.awt.Graphics2D;
import java.awt.Graphics;

import com.tuana9a.app.ActionQueue;
import com.tuana9a.interfaces.ActionEvent;
import com.tuana9a.screen.BaseScreen;
import com.tuana9a.animation.UiAnimation;

public class UiButton extends UiComponent {
    private final UiAnimation animation;
    private final ActionEvent actionEvent;

    public UiButton(final BaseScreen state, final double x, final double y, final int width, final int height, final UiAnimation animation, final ActionEvent actionEvent) {
        super(state, x, y, width, height);
        this.animation = animation;
        this.actionEvent = actionEvent;
    }

    @Override
    public void render(final Graphics g) {
        this.animation.render((Graphics2D) g.create(), this.status, this.x, this.y, this.width, this.height);
        super.render(g);
    }

    @Override
    public void onRelease() {
        ActionQueue actionQueue = ActionQueue.getInstance();
        actionQueue.push(this.actionEvent);
    }
}
