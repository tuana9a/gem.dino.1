package com.tuana9a.OLD.weapon;

import com.tuana9a.OLD.Entity;
import com.tuana9a.OLD.nature.Animal;
import com.tuana9a.entity.nature.nature.transform.TriggerObject;
import com.tuana9a.screen.GameScreen;
import com.tuana9a.utils.Algebra;

public class SwordOut extends WeaponOut {

    public SwordOut(GameScreen gameScreen, int weaponOutId, Weapon fromWeapon, Animal owner) {
        super(gameScreen, weaponOutId, fromWeapon, owner);
    }

    @Override
    public void updateAbstract() {

        Sword sword = (Sword) weapon;
        direct = sword.direct;
        updateRotate(sword.rotate_R_MAIN);
        updateBoundRotate();

        double meleeX, meleeY;
        double deltaX = 0, deltaY = 0;// dùng cho việc thu gọn code đoạn rotate do chỉ khác giá trị rotate trả về

        double weaponRecoilX = sword.recoilX;
        double meleeRecoilX = 0, meleeRecoilY = 0;

        double space = 10;
        double xRelTemp = sword.bounds_ORIGIN.w() / 2 + space;
        double yRelTemp = sword.bounds_ORIGIN.y() + sword.bounds_ORIGIN.h() / 2 - sword.rotateRels[sword.direct][Y_INDEX];

        if (sword.direct == RIGHT_INDEX) {
            deltaX = Algebra.rotateX(xRelTemp, yRelTemp, sword.rotate_R_MAIN);
            deltaY = Algebra.rotateY(xRelTemp, yRelTemp, sword.rotate_R_MAIN);
            meleeRecoilX = weaponRecoilX * Math.cos(sword.rotate_R_MAIN);
            meleeRecoilY = weaponRecoilX * Math.sin(sword.rotate_R_MAIN);

        } else if (sword.direct == LEFT_INDEX) {
            deltaX = Algebra.rotateX(xRelTemp, -yRelTemp, sword.rotate_R_MAIN);
            deltaY = Algebra.rotateY(xRelTemp, -yRelTemp, sword.rotate_R_MAIN);
            meleeRecoilX = -weaponRecoilX * Math.cos(sword.rotate_R_MAIN);
            meleeRecoilY = -weaponRecoilX * Math.sin(sword.rotate_R_MAIN);
        }
        meleeX = sword.x + sword.rotateRels[sword.direct][X_INDEX] + deltaX - width / 2;
        meleeY = sword.y + sword.rotateRels[sword.direct][Y_INDEX] + deltaY - height / 2;

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
