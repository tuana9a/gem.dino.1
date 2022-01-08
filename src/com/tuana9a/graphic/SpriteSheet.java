// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.graphic;

import java.awt.image.BufferedImage;

public class SpriteSheet
{
    private BufferedImage bufferedImage;
    
    public SpriteSheet(final BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
    
    public BufferedImage crop(final int x, final int y, final int width, final int height) {
        return this.bufferedImage.getSubimage(x, y, width, height);
    }
}
