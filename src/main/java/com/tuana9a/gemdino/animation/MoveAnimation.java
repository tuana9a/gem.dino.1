package com.tuana9a.gemdino.animation;

import com.tuana9a.gemdino.graphic.Assets;

import java.awt.image.BufferedImage;

public class MoveAnimation extends AnimationSingle
{
    public static final int STATIC_ANI = 1;
    public static final int LEFT_RIGHT_ANI = 2;
    
    public MoveAnimation(final BufferedImage[][] images) {
        super(images);
    }
    
    public MoveAnimation(final BufferedImage[][] images, final int updateSpeed) {
        super(images, updateSpeed);
    }
    
    @Override
    public BufferedImage getImage(final int moveDirect) {
        BufferedImage returnImage = null;
        switch (this.animationKind) {
            case 1: {
                returnImage = this.oneStateOneImages[0][this.currentIndex];
                break;
            }
            case 2: {
                returnImage = this.oneStateOneImages[moveDirect][this.currentIndex];
                break;
            }
        }
        if (returnImage == null) {
            return Assets.nullImage;
        }
        return returnImage;
    }
}
