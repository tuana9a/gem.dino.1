// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.graphic.animation;

import com.tuana9a.graphic.Assets;
import java.awt.image.BufferedImage;

public class UiAnimation extends AnimationSingle
{
    public static final int ONE_STATE_ANI = 1;
    public static final int TWO_STATE_ANI = 2;
    public static final int THREE_STATE_ANI = 3;
    public static final int FOUR_STATE_ANI = 4;
    public static final int ONE_IMAGE = 0;
    public static final int ONE_STATE = 0;
    
    public UiAnimation(final BufferedImage[][] images) {
        super(images);
    }
    
    public UiAnimation(final BufferedImage[][] images, final int updateSpeed) {
        super(images, updateSpeed);
    }
    
    @Override
    public BufferedImage getImage(final int state) {
        BufferedImage returnImage = null;
        switch (this.animationKind) {
            case 1: {
                returnImage = this.oneStateOneImages[0][this.currentIndex];
                break;
            }
            case 4: {
                returnImage = this.oneStateOneImages[state][0];
                break;
            }
        }
        if (returnImage == null) {
            return Assets.nullImage;
        }
        return returnImage;
    }
}
