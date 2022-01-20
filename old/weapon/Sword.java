package com.tuana9a.OLD.weapon;

import com.tuana9a.OLD.Entity;
import com.tuana9a.OLD.nature.Animal;
import com.tuana9a.screen.GameScreen;

import java.util.ArrayList;

public class Sword extends Weapon {

    private Sword(GameScreen gameScreen, int weaponId, double x, double y) {
        super(gameScreen, weaponId, x, y);
    }

    private Sword(GameScreen gameScreen, int weaponId, Animal owner) {
        super(gameScreen, weaponId, owner);
    }

    public static Sword construct(GameScreen gameScreen, int weaponId, double x, double y) {
        return new Sword(gameScreen, weaponId, x, y);
    }

    public static Sword construct(GameScreen gameScreen, int weaponId, Animal owner) {
        return new Sword(gameScreen, weaponId, owner);
    }


    @Override
    public void updateAbstract() {

    }

    @Override
    protected ArrayList<Entity> abstractAttack() {
        SwordOut melee = new SwordOut(gameState, id, this, owner);
        return new ArrayList<Entity>() {{
            add(melee);
        }};
    }
}
