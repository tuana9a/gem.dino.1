// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.game.entity.move.enemy;

import com.tuana9a.utility.Algebra;
import com.tuana9a.game.entity.weapon.WeaponOut;
import com.tuana9a.game.entity.weapon.Spear;
import com.tuana9a.game.entity.weapon.Sword;
import com.tuana9a.game.entity.move.player.Player;
import com.tuana9a.game.entity.weapon.Weapon;
import com.tuana9a.game.entity.Entity;
import com.tuana9a.state.GameState;
import com.tuana9a.graphic.animation.MoveAnimation;
import com.tuana9a.graphic.Assets;
import com.tuana9a.config.ConfigEnemy;
import com.tuana9a.utility.TimeSystem;
import java.awt.Rectangle;
import com.tuana9a.game.entity.move.Animal;

public abstract class Enemy extends Animal
{
    public Rectangle workingArea;
    public TimeSystem reactionTimer;
    
    @Override
    protected void initCoreInfo(final int enemyId) {
        this.setSize(ConfigEnemy.widths[enemyId], ConfigEnemy.heights[enemyId]);
        this.setActualSize(ConfigEnemy.boundX[enemyId], ConfigEnemy.boundY[enemyId], ConfigEnemy.boundWidth[enemyId], ConfigEnemy.boundHeight[enemyId]);
        this.initActualSizeOrigin();
    }
    
    @Override
    protected void initOtherInfo(final int enemyId) {
        this.speed = ConfigEnemy.speeds[enemyId];
        this.health = ConfigEnemy.healths[enemyId];
        this.holdingHand = ConfigEnemy.holdingHands[enemyId];
        this.shoulder = ConfigEnemy.shoulders[enemyId];
        this.moveAnimation = new MoveAnimation(Assets.enemies[enemyId]);
        this.reactionTimer = new TimeSystem();
        this.reactionTimer.deltaTime = ConfigEnemy.reactionTimers[enemyId];
        this.typicalTimer.deltaTime = ConfigEnemy.typicalTimers[enemyId];
        this.workingArea = new Rectangle((int)this.x, (int)this.y, ConfigEnemy.workDistances[enemyId], ConfigEnemy.workDistances[enemyId]);
    }
    
    @Override
    protected void initStateAnimation() {
        super.initStateAnimation();
    }
    
    public Enemy(final GameState gameState, final int enemyId, final double x, final double y) {
        super(gameState, enemyId, x, y);
        this.vision.setMaxDistance(ConfigEnemy.eyeDistances[enemyId]);
    }
    
    @Override
    protected void updateInteract() {
        for (final Entity e : this.intersectEntities) {
            if (e instanceof Weapon) {
                final Weapon w = (Weapon)e;
                if (w.owner == null) {
                    this.stealWeapon(w);
                }
                else {
                    if (!(w.owner instanceof Player)) {
                        continue;
                    }
                    this.stealWeapon(w);
                }
            }
        }
        this.intersectEntities.clear();
    }
    
    @Override
    public void intersectWith(final Entity e) {
        if (e instanceof Weapon) {
            final Weapon w = (Weapon)e;
            if (w.owner == this) {
                return;
            }
            if (this.hand.isFull()) {
                return;
            }
            if ((w instanceof Sword || w instanceof Spear) && w.owner != null) {
                return;
            }
        }
        this.intersectEntities.add(e);
    }
    
    @Override
    public void hitBy(final WeaponOut wo) {
        super.hitBy(wo);
        if (this.health == 0) {
            this.deadTime.reset();
            this.deadTime.deltaTime = ConfigEnemy.deadTimeOuts[this.id];
        }
    }
    
    @Override
    protected void typicalUpdate() {
        if (this.reactionTimer.isTime()) {
            this.reactionTimer.reset();
        }
        if (!this.typicalTimer.isTime()) {
            return;
        }
        this.typicalTimer.reset();
        final Player player = this.gameState.getStage().getPlayer();
        if (this.canSee(player)) {
            this.updateMoveScripted(player);
            this.updateWorkingArea((int)(this.x - this.workingArea.width / 2), (int)(this.y - this.workingArea.height / 2), this.workingArea.width, this.workingArea.height);
        }
        else {
            this.updateMoveRandom();
        }
    }
    
    private void updateMoveRandom() {
        final double currentX = this.x;
        final double currentY = this.y;
        final double nextX = Math.random() * this.workingArea.width + this.workingArea.x;
        final double nextY = Math.random() * this.workingArea.width + this.workingArea.y;
        final double radianToNext = Algebra.getRotate(currentX, currentY, nextX, nextY);
        this.xMove = this.speed * Math.cos(radianToNext);
        this.yMove = this.speed * Math.sin(radianToNext);
    }
    
    private void updateMoveKeyboard() {
        this.xMove = 0.0;
        this.yMove = 0.0;
        final boolean up = this.gameState.getKeyboardManager().up;
        final boolean down = this.gameState.getKeyboardManager().down;
        final boolean left = this.gameState.getKeyboardManager().left;
        final boolean right = this.gameState.getKeyboardManager().right;
        if (!up || !down) {
            if (up) {
                this.yMove = -this.speed;
            }
            if (down) {
                this.yMove = this.speed;
            }
        }
        if (!left || !right) {
            if (left) {
                this.xMove = -this.speed;
            }
            if (right) {
                this.xMove = this.speed;
            }
        }
    }
    
    private void updateMoveScripted(final Entity entity) {
        final double startX = this.x + this.width / 2.0;
        final double startY = this.y + this.actualSize.y + this.actualSize.height;
        final double desX = entity.x + entity.width / 2.0;
        final double desY = entity.y + entity.actualSize.y + entity.actualSize.height;
        final double minDis = 100.0;
        if (Math.abs(desX - startX) < minDis && Math.abs(desY - startY) < minDis) {
            final double n = 0.0;
            this.yMove = n;
            this.xMove = n;
            return;
        }
        final double radianToEntity = Algebra.getRotate(startX, startY, desX, desY);
        this.xMove = this.speed * Math.cos(radianToEntity);
        this.yMove = this.speed * Math.sin(radianToEntity);
    }
    
    private void updateWorkingArea(final int x, final int y, final int w, final int h) {
        if (this.workingArea == null) {
            this.workingArea = new Rectangle(x, y, w, h);
        }
        else {
            this.workingArea.x = x;
            this.workingArea.y = y;
            this.workingArea.width = w;
            this.workingArea.height = h;
        }
    }
    
    public static boolean isBoss(final int enemyId) {
        for (final int i : ConfigEnemy.bossEnemies) {
            if (enemyId == i) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isHard(final int enemyId) {
        for (final int i : ConfigEnemy.aggressiveEnemies) {
            if (enemyId == i) {
                return true;
            }
        }
        return false;
    }
}
