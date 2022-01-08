// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.entities.weapon;

import com.tuana9a.entities.Entity;

import java.util.ArrayList;

import com.tuana9a.entities.Animal;
import com.tuana9a.screen.GameScreen;

public class Sword extends Weapon {
    public Sword(final GameScreen gameScreen, final int weaponId, final double x, final double y) {
        super(gameScreen, weaponId, x, y);
    }

    public Sword(final GameScreen gameScreen, final int weaponId, final Animal owner) {
        super(gameScreen, weaponId, owner);
    }

    @Override
    protected void typicalUpdate() {
    }

    public ArrayList<Entity> typicalAttack() {
        final SwordOut melee = new SwordOut(this.gameScreen, this.id, this, this.owner);
        return new ArrayList<Entity>() {
            {
                this.add(melee);
            }
        };
    }
}
