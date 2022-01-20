package com.tuana9a.OLD.weapon;

import com.tuana9a.OLD.Entity;
import com.tuana9a.OLD.nature.Animal;
import com.tuana9a.entity.nature.nature.transform.TriggerObject;
import com.tuana9a.screen.GameScreen;
import com.tuana9a.utils.Algebra;

public class SpearOut extends WeaponOut {

    public SpearOut(GameScreen gameScreen, int weaponOutId, Weapon fromWeapon, Animal owner) {
        super(gameScreen, weaponOutId, fromWeapon, owner);
    }

    @Override
    public void updateAbstract() {

        Spear spear = (Spear) weapon;
        direct = spear.direct;
        updateRotate(spear.rotate_R_MAIN);
        updateBoundRotate();

        double meleeX, meleeY;
        double deltaX = 0, deltaY = 0;// dùng cho việc thu gọn code đoạn rotate do chỉ khác giá trị rotate trả về

        double weaponRecoilX = spear.recoilX;
        double meleeRecoilX = 0, meleeRecoilY = 0;

        double space = -width / 4;
        double xRelTemp = spear.bounds_ORIGIN.w() + width / 2 + space;
        double yRelTemp = spear.bounds_ORIGIN.y() + spear.bounds.h() / 2 - spear.rotateRels[spear.direct][Y_INDEX];

        if (spear.direct == RIGHT_INDEX) {
            deltaX = Algebra.rotateX(xRelTemp, yRelTemp, spear.rotate_R_MAIN);
            deltaY = Algebra.rotateY(xRelTemp, yRelTemp, spear.rotate_R_MAIN);
            meleeRecoilX = weaponRecoilX * Math.cos(spear.rotate_R_MAIN);
            meleeRecoilY = weaponRecoilX * Math.sin(spear.rotate_R_MAIN);

        } else if (spear.direct == LEFT_INDEX) {
            deltaX = Algebra.rotateX(xRelTemp, -yRelTemp, spear.rotate_R_MAIN);
            deltaY = Algebra.rotateY(xRelTemp, -yRelTemp, spear.rotate_R_MAIN);
            meleeRecoilX = -weaponRecoilX * Math.cos(spear.rotate_R_MAIN);
            meleeRecoilY = -weaponRecoilX * Math.sin(spear.rotate_R_MAIN);
        }
        meleeX = spear.x + spear.rotateRels[spear.direct][X_INDEX] + deltaX - width / 2;
        meleeY = spear.y + spear.rotateRels[spear.direct][Y_INDEX] + deltaY - height / 2;

        updatePosition(meleeX + meleeRecoilX, meleeY + meleeRecoilY);
    }

    @Override
    public void collideWith(Entity e) {
        if (!(e instanceof Animal) && !(e instanceof TriggerObject)) return;
        if (e == owner) return;
        if (collidedEntities.contains(e)) return;
        collidedEntities.add(e);
        e.beCollidedBy(this);
    }
}
