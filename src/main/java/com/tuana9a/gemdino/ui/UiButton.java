package com.tuana9a.gemdino.ui;

import java.awt.Graphics2D;
import java.awt.Graphics;

import com.tuana9a.gemdino.app.EventQueue;
import com.tuana9a.gemdino.interfaces.EventHandler;
import com.tuana9a.gemdino.screen.Screen;
import com.tuana9a.gemdino.animation.UiAnimation;

public class UiButton extends UiComponent {
    private final UiAnimation animation;
    private final EventHandler eventHandler;

    public UiButton(final Screen state, final double x, final double y, final int width, final int height, final UiAnimation animation, final EventHandler eventHandler) {
        super(state, x, y, width, height);
        this.animation = animation;
        this.eventHandler = eventHandler;
    }

    @Override
    public void render(final Graphics g) {
        this.animation.render((Graphics2D) g.create(), this.status, this.x, this.y, this.width, this.height);
        super.render(g);
    }

    @Override
    public void onRelease() {
        EventQueue eventQueue = EventQueue.getInstance();
        eventQueue.push(this.eventHandler);
    }
}
