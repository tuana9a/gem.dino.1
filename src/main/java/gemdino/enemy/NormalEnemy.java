package gemdino.enemy;

import gemdino.animation.StateAnimation;
import gemdino.app.App;
import gemdino.graphic.Assets;

public class NormalEnemy extends Enemy {
    @Override
    protected void initStateAnimation() {
        super.initStateAnimation();
        this.allStateAnimations[NormalEnemy.NORMAL] = new StateAnimation(Assets.emoteSleep, new double[][]{{(this.width - 32) / 2.0f, -32.0}, {(this.width - 32) / 2.0f, -32.0}}, 32.0, 32.0);
        this.allStateAnimations[NormalEnemy.HIT] = new StateAnimation(Assets.emoteFaceSad, new double[][]{{(this.width - 32) / 2.0f, -32.0}, {(this.width - 32) / 2.0f, -32.0}}, 32.0, 32.0);
        this.allStateAnimations[NormalEnemy.DEAD] = new StateAnimation(Assets.deadState, new double[][]{{0.0, 0.0}, {0.0, 0.0}}, this.width, this.height);
    }

    public NormalEnemy(App app, final int enemyId, final double x, final double y) {
        super(app, enemyId, x, y);
    }
}
