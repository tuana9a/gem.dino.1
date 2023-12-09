package gemdino.weapon;

import gemdino.app.App;
import gemdino.entity.Entity;

import java.util.ArrayList;

import gemdino.entity.Animal;

public class Sword extends Weapon {
    public Sword(App app, final int weaponId, final double x, final double y) {
        super(app, weaponId, x, y);
    }

    public Sword(App app, final int weaponId, final Animal owner) {
        super(app, weaponId, owner);
    }

    @Override
    protected void typicalUpdate() {
    }

    public ArrayList<Entity> typicalAttack() {
        final SwordOut melee = new SwordOut(app, this.id, this, this.owner);
        return new ArrayList<Entity>() {
            {
                this.add(melee);
            }
        };
    }
}
