package gemdino.enemy;

import gemdino.ability.BaseSkill;
import gemdino.animation.StateAnimation;
import gemdino.app.App;
import gemdino.app.EventQueue;
import gemdino.configs.ConfigEnemy;
import gemdino.engine.EntityManager;
import com.tuana9a.gemdino.entities.weapon.*;
import gemdino.graphic.Assets;
import gemdino.screen.GameScreen;
import gemdino.utils.Timer;
import gemdino.weapon.*;

public class SpawnChildBoss extends Enemy {
    public SpawnChildBoss(App app, final int enemyId, final double x, final double y) {
        super(app, enemyId, x, y);
    }

    @Override
    protected void initStateAnimation() {
        super.initStateAnimation();
        this.allStateAnimations[SpawnChildBoss.NORMAL] = new StateAnimation(Assets.emoteBoss, new double[][]{{(this.width - 32) / 2.0f, -32.0}, {(this.width - 32) / 2.0f, -32.0}}, 32.0, 32.0);
        this.allStateAnimations[SpawnChildBoss.HIT] = new StateAnimation(Assets.emoteFaceAngry, new double[][]{{(this.width - 32) / 2.0f, -32.0}, {(this.width - 32) / 2.0f, -32.0}}, 32.0, 32.0);
        this.allStateAnimations[SpawnChildBoss.DEAD] = new StateAnimation(Assets.bossDeadState, new double[][]{{0.0, 0.0}, {0.0, 0.0}}, this.width, this.height);
    }

    @Override
    protected void initSkills() {
        super.initSkills();
        this.skillTimers[SpawnChildBoss.DEAD] = new Timer(5000L);
        this.abilities[SpawnChildBoss.DEAD] = new BaseSkill(this) {
            @Override
            public void perform() {
                final int size = 3 + (int) (Math.random() * 7.0);
                final double startX = this.fromAnimal.x + this.fromAnimal.width / 2.0;
                final double startY = this.fromAnimal.y + this.fromAnimal.actualSizeOriginY + 1.0;
                final Enemy[] children = new Enemy[size];
                for (int i = 0; i < size; ++i) {
                    final int randomId = (int) (Math.random() * 3.0 - 1.0);
                    final double temp = ConfigEnemy.widths[randomId] / 2.0;
                    if (isHard(randomId)) {
                        children[i] = new HardEnemy(randomId, startX - temp, startY);
                    } else {
                        children[i] = new NormalEnemy(randomId, startX - temp, startY);
                    }
                }
                final Weapon[] enemyWeapons = new Weapon[size];
                for (int j = 0; j < size; ++j) {
                    final int randomWeaponId = j % 5;
                    if (Weapon.isShootWeapon(randomWeaponId)) {
                        enemyWeapons[j] = new Shoot(randomWeaponId, children[j]);
                    } else if (Weapon.isSword(randomWeaponId)) {
                        enemyWeapons[j] = new Sword(randomWeaponId, children[j]);
                    } else if (Weapon.isSpear(randomWeaponId)) {
                        enemyWeapons[j] = new Spear(randomWeaponId, children[j]);
                    }
                }
                EventQueue eventQueue = app.getEventQueue();
                eventQueue.push(() -> {
                    EntityManager entityManager = app.getEntityManager();
                    entityManager.addAllEntities(children);
                    entityManager.addAllEntities(enemyWeapons);
                });
            }
        };
    }

    @Override
    public void hitBy(final WeaponOut wo) {
        super.hitBy(wo);
        GameScreen gameScreen = app.getGameScreen();
        gameScreen.updateUiBossHp(this.health);
    }

    @Override
    public void onDead() {
        this.abilities[SpawnChildBoss.DEAD].perform();
    }
}
