package com.tuana9a.OLD.weapon;

import com.tuana9a.OLD.nature.Animal;
import com.tuana9a.entity.config.ConfigWeapon;
import com.tuana9a.screen.GameScreen;

public abstract class Shoot extends Weapon {

    protected Shoot(GameScreen gameScreen, int weaponId, double x, double y) {
        super(gameScreen, weaponId, x, y);
    }

    protected Shoot(GameScreen gameScreen, int weaponId, Animal owner) {
        super(gameScreen, weaponId, owner);
    }


    //SECTION: construct dont care id
    public static Shoot construct(GameScreen gameScreen, int weaponId, Animal owner) {
        if (isShotGun(weaponId)) return ShotGun.construct(gameScreen, weaponId, owner);
        if (isBow(weaponId)) return Bow.construct(gameScreen, weaponId, owner);
        if (isGatling(weaponId)) return Gatling.construct(gameScreen, weaponId, owner);
        return null;
    }

    public static Shoot construct(GameScreen gameScreen, int weaponId, double x, double y) {
        if (isShotGun(weaponId)) return ShotGun.construct(gameScreen, weaponId, x, y);
        if (isBow(weaponId)) return Bow.construct(gameScreen, weaponId, x, y);
        if (isGatling(weaponId)) return Gatling.construct(gameScreen, weaponId, x, y);
        return null;
    }


    @Override
    public void updateAbstract() {
        if (direct == RIGHT_INDEX) {
            if (attacking()) {
                recoilX = (recoilX > 0 ? -recoilX : recoilX) - recoilAmountX;
                recoilR = (Math.random() * 2 - 1) * recoilAmountR;
                updateRotate(rotate_R_MAIN + recoilR);
            } else if (Double.compare(recoilX, 0) != 0) {
                recoilX = (recoilX > 0 ? -recoilX : recoilX) + recoilRegenX;
            }

        } else if (direct == LEFT_INDEX) {
            if (attacking()) {
                recoilX = (recoilX < 0 ? -recoilX : recoilX) + recoilAmountX;
                recoilR = (Math.random() * 2 - 1) * recoilAmountR;
                updateRotate(rotate_R_MAIN + recoilR);
            } else if (Double.compare(recoilX, 0) != 0) {
                recoilX = (recoilX < 0 ? -recoilX : recoilX) - recoilRegenX;
            }
        }
        updateBoundRotate();

    }


    //SECTION: checker
    public static boolean isBow(int id) {
        for (int check : ConfigWeapon.bowIds) {
            if (id == check) return true;
        }
        return false;
    }

    public static boolean isShotGun(int id) {
        for (int check : ConfigWeapon.shotGunIds) {
            if (id == check) return true;
        }
        return false;
    }

    public static boolean isGatling(int id) {
        for (int check : ConfigWeapon.gatlingIds) {
            if (id == check) return true;
        }
        return false;
    }
}
