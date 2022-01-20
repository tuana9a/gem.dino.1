package com.tuana9a.OLD.weapon;

import com.tuana9a.OLD.Entity;
import com.tuana9a.OLD.nature.Animal;
import com.tuana9a.entity.nature.nature.transform.TriggerObject;
import com.tuana9a.screen.GameScreen;
import com.tuana9a.utils.Algebra;

public class ShieldOut extends WeaponOut {

    public ShieldOut(GameScreen gameScreen, int weaponOutId, Weapon fromWeapon, Animal owner) {
        super(gameScreen, weaponOutId, fromWeapon, owner);
    }

    @Override
    public void updateAbstract() {

        Shield shield = (Shield) weapon;
        direct = shield.direct;
        updateRotate(shield.rotate_R_MAIN);
        updateBoundRotate();

        double meleeX, meleeY;
        double deltaX = 0, deltaY = 0;// dùng cho việc thu gọn code đoạn rotate do chỉ khác giá trị rotate trả về

        double weaponRecoilX = shield.recoilX;
        double meleeRecoilX = 0, meleeRecoilY = 0;

        double space = 10;
        double xRelTemp = shield.bounds_ORIGIN.w() / 2 + space;
        double yRelTemp = shield.bounds_ORIGIN.y() + shield.bounds_ORIGIN.h() / 2 - shield.rotateRels[shield.direct][Y_INDEX];

        if (shield.direct == RIGHT_INDEX) {
            deltaX = Algebra.rotateX(xRelTemp, yRelTemp, shield.rotate_R_MAIN);
            deltaY = Algebra.rotateY(xRelTemp, yRelTemp, shield.rotate_R_MAIN);
            meleeRecoilX = weaponRecoilX * Math.cos(shield.rotate_R_MAIN);
            meleeRecoilY = weaponRecoilX * Math.sin(shield.rotate_R_MAIN);

        } else if (shield.direct == LEFT_INDEX) {
            deltaX = Algebra.rotateX(xRelTemp, -yRelTemp, shield.rotate_R_MAIN);
            deltaY = Algebra.rotateY(xRelTemp, -yRelTemp, shield.rotate_R_MAIN);
            meleeRecoilX = -weaponRecoilX * Math.cos(shield.rotate_R_MAIN);
            meleeRecoilY = -weaponRecoilX * Math.sin(shield.rotate_R_MAIN);
        }
        meleeX = shield.x + shield.rotateRels[shield.direct][X_INDEX] + deltaX - width / 2;
        meleeY = shield.y + shield.rotateRels[shield.direct][Y_INDEX] + deltaY - height / 2;

        updatePosition(meleeX + meleeRecoilX, meleeY + meleeRecoilY);
    }

    @Override
    public void collideWith(Entity e) {
        if(! (e instanceof ShootOut) && ! (e instanceof TriggerObject)) return;
        e.beCollidedBy(this);
    }
}
