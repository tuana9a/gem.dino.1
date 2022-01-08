// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.game.entity.move.player;

import com.tuana9a.game.entity.weapon.WeaponOut;
import com.tuana9a.game.entity.stay.TeleGate;
import com.tuana9a.game.entity.weapon.Weapon;
import com.tuana9a.game.entity.Entity;
import com.tuana9a.state.GameState;
import com.tuana9a.graphic.animation.StateAnimation;
import com.tuana9a.graphic.animation.MoveAnimation;
import com.tuana9a.graphic.Assets;
import com.tuana9a.config.ConfigPlayer;
import com.tuana9a.game.entity.move.Animal;

public class Player extends Animal
{
    @Override
    protected void initCoreInfo(final int playerId) {
        this.setSize(ConfigPlayer.widths[playerId], ConfigPlayer.heights[playerId]);
        this.setActualSize(ConfigPlayer.boundX[playerId], ConfigPlayer.boundY[playerId], ConfigPlayer.boundWidth[playerId], ConfigPlayer.boundHeight[playerId]);
        this.initActualSizeOrigin();
    }
    
    @Override
    protected void initOtherInfo(final int playerId) {
        this.speed = ConfigPlayer.speeds[playerId];
        this.health = ConfigPlayer.healths[playerId];
        this.holdingHand = ConfigPlayer.holdingHands[playerId];
        this.shoulder = ConfigPlayer.shoulders[playerId];
        this.moveAnimation = new MoveAnimation(Assets.players[playerId]);
    }
    
    @Override
    protected void initStateAnimation() {
        super.initStateAnimation();
        this.allStateAnimations[Player.NORMAL] = new StateAnimation(Assets.emoteFaceHappy, new double[][] { { (this.width - 32) / 2.0f, -32.0 }, { (this.width - 32) / 2.0f, -32.0 } }, 32.0, 32.0);
        this.allStateAnimations[Player.DEAD] = new StateAnimation(Assets.deadState, new double[][] { { 0.0, 0.0 }, { 0.0, 0.0 } }, this.width, this.height);
        this.allStateAnimations[Player.HIT] = new StateAnimation(Assets.emoteFaceSad, new double[][] { { (this.width - 32) / 2.0f, -32.0 }, { (this.width - 32) / 2.0f, -32.0 } }, 32.0, 32.0);
    }
    
    public Player(final GameState gameState, final int playerId, final double x, final double y) {
        super(gameState, playerId, x, y);
        gameState.updateUiPayerHp(this.health);
    }
    
    private void updateMoveKeyboard() {
        final double n = 0.0;
        this.yMove = n;
        this.xMove = n;
        final boolean up = this.gameState.getKeyboardManager().w;
        final boolean down = this.gameState.getKeyboardManager().s;
        final boolean left = this.gameState.getKeyboardManager().a;
        final boolean right = this.gameState.getKeyboardManager().d;
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
    
    @Override
    public void intersectWith(final Entity e) {
        if (e instanceof Weapon && ((Weapon)e).owner != null) {
            return;
        }
        this.intersectEntities.add(e);
    }
    
    @Override
    protected void updateInteract() {
        if (this.gameState.getKeyboardManager().dropWeapon()) {
            this.dropWeapon();
        }
        if (this.gameState.getKeyboardManager().switchWeapon()) {
            this.switchWeapon();
        }
        for (int i = 0; i < this.intersectEntities.size(); ++i) {
            final Entity e = this.intersectEntities.get(i);
            if (e instanceof Weapon && this.gameState.getKeyboardManager().takeWeapon()) {
                this.takeWeapon((Weapon)e);
            }
            else if (e instanceof TeleGate) {
                ((TeleGate)e).teleToNewMap();
            }
        }
        this.intersectEntities.clear();
    }
    
    @Override
    public void hitBy(final WeaponOut wo) {
        super.hitBy(wo);
        this.gameState.updateUiPayerHp(this.health);
    }
    
    @Override
    protected void typicalUpdate() {
        this.updateMoveKeyboard();
    }
    
    public void reborn() {
        super.reborn();
        this.health = ConfigPlayer.healths[this.id];
    }
}
