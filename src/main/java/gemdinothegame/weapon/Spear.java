package gemdinothegame.weapon;

import gemdinothegame.entity.Entity;

import java.util.ArrayList;

import gemdinothegame.entity.Animal;

public class Spear extends Weapon {
    public Spear(final int weaponId, final double x, final double y) {
        super(weaponId, x, y);
    }

    public Spear(final int weaponId, final Animal owner) {
        super(weaponId, owner);
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
        final SpearOut melee = new SpearOut(this.id, this, this.owner);
        return new ArrayList<Entity>() {
            {
                add(melee);
            }
        };
    }
}