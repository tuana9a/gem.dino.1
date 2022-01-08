// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.animation;

import com.tuana9a.utils.Timer;

public abstract class Animation
{
    public static final int DEFAULT_ANIMATION_KIND = 1;
    public static final int DEFAULT_MAX_INDEX = 0;
    public static final int DEFAULT_SPEED = 100;
    protected int animationKind;
    protected int currentIndex;
    protected int maxIndex;
    protected Timer updateImageTimer;
    
    public void update() {
        if (this.updateImageTimer.isTime()) {
            this.updateImageTimer.reset();
            ++this.currentIndex;
            if (this.currentIndex >= this.maxIndex) {
                this.currentIndex = 0;
            }
        }
    }
    
    public Animation() {
        this.updateImageTimer = new Timer(100L);
        this.animationKind = 1;
        this.maxIndex = 0;
    }
    
    public Animation(final int updateSpeed) {
        this.updateImageTimer = new Timer(updateSpeed);
        this.animationKind = 1;
        this.maxIndex = 0;
    }
}
