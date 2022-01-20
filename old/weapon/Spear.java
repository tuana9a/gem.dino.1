package com.tuana9a.OLD.weapon;

import com.tuana9a.OLD.Entity;
import com.tuana9a.OLD.nature.Animal;
import com.tuana9a.screen.GameScreen;

import java.util.ArrayList;

public class Spear extends Weapon {

    private Spear(GameScreen gameScreen, int weaponId, double x, double y) {
        super(gameScreen, weaponId, x, y);
    }

    private Spear(GameScreen gameScreen, int weaponId, Animal owner) {
        super(gameScreen, weaponId, owner);
    }


    public static Spear construct(GameScreen gameScreen, int weaponId, double x, double y) {
        return new Spear(gameScreen, weaponId, x, y);
    }

    public static Spear construct(GameScreen gameScreen, int weaponId, Animal owner) {
        return new Spear(gameScreen, weaponId, owner);
    }


    @Override
    public void updateAbstract() {
        if (direct == RIGHT_INDEX) {
            if (attacking()) {
                recoilX = (recoilX < 0 ? -recoilX : recoilX) - recoilAmountX;
            } else if (recoilX != 0) {
                recoilX = (recoilX < 0 ? -recoilX : recoilX) - recoilRegenX;
            }

        } else if (direct == LEFT_INDEX) {
            if (attacking()) {
                recoilX = (recoilX > 0 ? -recoilX : recoilX) + recoilAmountX;
            } else if (recoilX != 0) {
                recoilX = (recoilX > 0 ? -recoilX : recoilX) + recoilRegenX;
            }
        }
    }

    @Override
    protected ArrayList<Entity> abstractAttack() {
        SpearOut melee = new SpearOut(gameState, id, this, owner);
        return new ArrayList<Entity>(){{
            add(melee);
        }};
    }
}
