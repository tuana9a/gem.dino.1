// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.graphic.animation;

import com.tuana9a.utility.TimeSystem;

public abstract class Animation
{
    public static final int DEFAULT_ANIMATION_KIND = 1;
    public static final int DEFAULT_MAX_INDEX = 0;
    public static final int DEFAULT_SPEED = 100;
    protected int animationKind;
    protected int currentIndex;
    protected int maxIndex;
    protected TimeSystem updateImageTimer;
    
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
        this.updateImageTimer = new TimeSystem(100L);
        this.animationKind = 1;
        this.maxIndex = 0;
    }
    
    public Animation(final int updateSpeed) {
        this.updateImageTimer = new TimeSystem(updateSpeed);
        this.animationKind = 1;
        this.maxIndex = 0;
    }
}
