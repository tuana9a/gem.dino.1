package gemdino.weapon;

import gemdino.entity.Entity;

import java.util.ArrayList;

import gemdino.entity.Animal;

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
        final SwordOut melee = new SwordOut(this.id, this, this.owner);
        return new ArrayList<Entity>() {
            {
                this.add(melee);
            }
        };
    }
}
