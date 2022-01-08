// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.game.entity.weapon;

import com.tuana9a.game.entity.Entity;

import java.util.ArrayList;

import com.tuana9a.game.entity.move.Animal;
import com.tuana9a.state.GameState;

public class Sword extends Weapon {
    public Sword(final GameState gameState, final int weaponId, final double x, final double y) {
        super(gameState, weaponId, x, y);
    }

    public Sword(final GameState gameState, final int weaponId, final Animal owner) {
        super(gameState, weaponId, owner);
    }

    @Override
    protected void typicalUpdate() {
    }

    public ArrayList<Entity> typicalAttack() {
        final SwordOut melee = new SwordOut(this.gameState, this.id, this, this.owner);
        return new ArrayList<Entity>() {
            {
                this.add(melee);
            }
        };
    }
}
