// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.entities.enemy;

import com.tuana9a.animation.StateAnimation;
import com.tuana9a.graphic.Assets;

public class HardEnemy extends Enemy
{
    public HardEnemy(final int enemyId, final double x, final double y) {
        super(enemyId, x, y);
    }
    
    @Override
    protected void initStateAnimation() {
        super.initStateAnimation();
        this.allStateAnimations[HardEnemy.NORMAL] = new StateAnimation(Assets.emoteFaceAngry, new double[][] { { (this.width - 32) / 2.0f, -32.0 }, { (this.width - 32) / 2.0f, -32.0 } }, 32.0, 32.0);
        this.allStateAnimations[HardEnemy.HIT] = new StateAnimation(Assets.emoteThreeDots, new double[][] { { (this.width - 32) / 2.0f, -32.0 }, { (this.width - 32) / 2.0f, -32.0 } }, 32.0, 32.0);
        this.allStateAnimations[HardEnemy.DEAD] = new StateAnimation(Assets.deadState, new double[][] { { 0.0, 0.0 }, { 0.0, 0.0 } }, this.width, this.height);
    }
}
