// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.gemdino.entities.weapon;

import com.tuana9a.gemdino.entities.Entity;

import java.util.ArrayList;

import com.tuana9a.gemdino.entities.Animal;
import com.tuana9a.gemdino.screen.GameScreen;

public class Sword extends Weapon {
    public Sword(final int weaponId, final double x, final double y) {
        super(weaponId, x, y);
    }

    public Sword(final int weaponId, final Animal owner) {
        super(weaponId, owner);
    }

    @Override
    protected void typicalUpdate() {
    }

    public ArrayList<Entity> typicalAttack() {
        final GameScreen gameScreen = GameScreen.getInstance();
        final SwordOut melee = new SwordOut(this.id, this, this.owner);
        return new ArrayList<Entity>() {
            {
                this.add(melee);
            }
        };
    }
}
