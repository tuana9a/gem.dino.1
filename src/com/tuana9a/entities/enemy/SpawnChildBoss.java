// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.entities.enemy;

import com.tuana9a.abilities.BaseSkill;
import com.tuana9a.animation.StateAnimation;
import com.tuana9a.app.ActionQueue;
import com.tuana9a.configs.ConfigEnemy;
import com.tuana9a.engine.EntityManager;
import com.tuana9a.entities.weapon.*;
import com.tuana9a.graphic.Assets;
import com.tuana9a.screen.GameScreen;
import com.tuana9a.utils.Timer;

public class SpawnChildBoss extends Enemy {
    public SpawnChildBoss(final int enemyId, final double x, final double y) {
        super(enemyId, x, y);
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
        final GameScreen gameScreen = GameScreen.getInstance();
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
                    if (Enemy.isHard(randomId)) {
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
                ActionQueue actionQueue = ActionQueue.getInstance();
                actionQueue.push(() -> {
                    EntityManager entityManager = EntityManager.getInstance();
                    entityManager.addAllEntities(children);
                    entityManager.addAllEntities(enemyWeapons);
                });
            }
        };
    }

    @Override
    public void hitBy(final WeaponOut wo) {
        super.hitBy(wo);
        GameScreen gameScreen = GameScreen.getInstance();
        gameScreen.updateUiBossHp(this.health);
    }

    @Override
    public void onDead() {
        this.abilities[SpawnChildBoss.DEAD].perform();
    }
}
