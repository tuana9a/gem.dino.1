package com.tuana9a.OLD.weapon;

import com.tuana9a.OLD.Entity;
import com.tuana9a.OLD.nature.Animal;
import com.tuana9a.screen.GameScreen;

import java.util.ArrayList;

public class Shield extends Weapon {

    private Shield(GameScreen gameScreen, int weaponId, double x, double y) {
        super(gameScreen, weaponId, x, y);
    }

    private Shield(GameScreen gameScreen, int weaponId, Animal owner) {
        super(gameScreen, weaponId, owner);
    }


    public static Shield construct(GameScreen gameScreen, int weaponId, double x, double y) {
        return new Shield(gameScreen, weaponId, x, y);
    }

    public static Shield construct(GameScreen gameScreen, int weaponId, Animal owner) {
        return new Shield(gameScreen, weaponId, owner);
    }


    @Override
    public void updateAbstract() {

    }

    @Override
    protected ArrayList<Entity> abstractAttack() {
        ShieldOut cover = new ShieldOut(gameState, id, this, owner);
        return new ArrayList<Entity>() {{
            add(cover);
        }};
    }

}
