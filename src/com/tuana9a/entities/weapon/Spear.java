// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.entities.weapon;

import com.tuana9a.entities.Entity;

import java.util.ArrayList;

import com.tuana9a.entities.Animal;
import com.tuana9a.screen.GameScreen;

public class Spear extends Weapon {
    public Spear(final GameScreen gameScreen, final int weaponId, final double x, final double y) {
        super(gameScreen, weaponId, x, y);
    }

    public Spear(final GameScreen gameScreen, final int weaponId, final Animal owner) {
        super(gameScreen, weaponId, owner);
    }

    @Override
    protected void typicalUpdate() {
        if (this.moveDirect == 1) {
            if (this.isAttacking()) {
                this.recoilX = ((this.recoilX < 0.0) ? (-this.recoilX) : this.recoilX) - this.recoilAmount;
            } else if (this.recoilX != 0.0) {
                this.recoilX = ((this.recoilX < 0.0) ? (-this.recoilX) : this.recoilX) - this.recoilRegen;
            }
        } else if (this.moveDirect == 0) {
            if (this.isAttacking()) {
                this.recoilX = ((this.recoilX > 0.0) ? (-this.recoilX) : this.recoilX) + this.recoilAmount;
            } else if (this.recoilX != 0.0) {
                this.recoilX = ((this.recoilX > 0.0) ? (-this.recoilX) : this.recoilX) + this.recoilRegen;
            }
        }
    }

    public ArrayList<Entity> typicalAttack() {
        final SpearOut melee = new SpearOut(this.gameScreen, this.id, this, this.owner);
        return new ArrayList<Entity>() {
            {
                add(melee);
            }
        };
    }
}
