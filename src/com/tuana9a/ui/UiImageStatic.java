// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.ui;

import java.awt.Graphics;

import com.tuana9a.state.AppState;

import java.awt.image.BufferedImage;

import com.tuana9a.ui.UiComponent;

public class UiImageStatic extends UiComponent {
    private final BufferedImage image;

    public UiImageStatic(final AppState state, final double x, final double y, final int width, final int height, final BufferedImage image) {
        super(state, x, y, width, height);
        this.image = image;
    }

    @Override
    public void render(final Graphics g) {
        g.drawImage(this.image, (int) this.x, (int) this.y, this.width, this.height, null);
    }
}
