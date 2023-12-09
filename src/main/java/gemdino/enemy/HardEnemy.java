package gemdino.enemy;

import gemdino.animation.StateAnimation;
import gemdino.graphic.Assets;

public class HardEnemy extends Enemy
{
    public HardEnemy(final int enemyId, final double x, final double y) {
        super(enemyId, x, y);
    }
    
    @Override
    protected void initStateAnimation() {
        super.initStateAnimation();
        this.allStateAnimations[NORMAL] = new StateAnimation(Assets.emoteFaceAngry, new double[][] { { (this.width - 32) / 2.0f, -32.0 }, { (this.width - 32) / 2.0f, -32.0 } }, 32.0, 32.0);
        this.allStateAnimations[HIT] = new StateAnimation(Assets.emoteThreeDots, new double[][] { { (this.width - 32) / 2.0f, -32.0 }, { (this.width - 32) / 2.0f, -32.0 } }, 32.0, 32.0);
        this.allStateAnimations[DEAD] = new StateAnimation(Assets.deadState, new double[][] { { 0.0, 0.0 }, { 0.0, 0.0 } }, this.width, this.height);
    }
}
