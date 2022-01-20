package com.tuana9a.OLD.weapon;

import com.tuana9a.OLD.Entity;
import com.tuana9a.OLD.nature.Animal;
import com.tuana9a.OLD.nature.NatureObject;
import com.tuana9a.entity.abilities.CanMove;
import com.tuana9a.screen.GameScreen;
import com.tuana9a.utils.Algebra;

public class ShootOut extends WeaponOut implements CanMove {

    protected Entity hit;
    protected double hitDeltaX, hitDeltaY, hitDeltaXRelX, hitDeltaYRelY;
    protected double hitDeltaRadianRotate;
    protected int hitDirect, thisDirect;


    public ShootOut(GameScreen gameScreen, int weaponOutId, Weapon fromWeapon, Animal owner) {
        super(gameScreen, weaponOutId, fromWeapon, owner);
    }

    @Override
    public void updateAbstract() {
        if (!isHit()) {
            move();
            return;
        }

        //EXPLAIN: check hitEntity is disappear or not
        if (!entityManager.isManage(hit)) {
            updateState(TIME_OUT);
            return;
        }

        stickToHit();
    }

    private void stickToHit() {
        if (!hit.isRotated()) {
            updatePosition(hit.x + hitDeltaX, hit.y + hitDeltaY);
            return;
        }

        int sign = 1;
        direct = thisDirect;

        if (hit.direct != hitDirect) {
            //EXPLAIN: nếu ngược chiều lúc hit thì phải sửa lại 1 vài thông số
            direct = (thisDirect + 1) % 2;
            sign = -1;
        }

        double newRelX = Algebra.rotateX(hitDeltaXRelX, sign * hitDeltaYRelY, hit.rotate_R_MAIN);
        double newRelY = Algebra.rotateY(hitDeltaXRelX, sign * hitDeltaYRelY, hit.rotate_R_MAIN);

        updatePosition(
                newRelX + hit.x + hit.rotateRels[hit.direct][X_INDEX] - rotateRels[direct][X_INDEX],
                newRelY + hit.y + hit.rotateRels[hit.direct][Y_INDEX] - rotateRels[direct][Y_INDEX]
        );

        updateRotate(hit.rotate_R_MAIN + sign * hitDeltaRadianRotate);
        updateBoundRotate();
    }

    @Override
    public void move() {
        if (isHit()) return;

        x += xMove;
        y += yMove;
    }


    //SECTION: collide
    @Override
    public void collideWith(Entity e) {
        if (hit != null) return;
        if (e == owner) return;
        if ((e instanceof NatureObject && ((NatureObject) e).penAble)) return;
        if (e instanceof Weapon) {
            if (!(e instanceof Shield)) return;
            if (((Shield) e).owner == owner) return;
        }
        if (e instanceof WeaponOut && !(e instanceof ShieldOut)) return;

        if (e instanceof ShieldOut) {
            if (((ShieldOut) e).owner == owner) return;

            double newRotate = rotate_R_MAIN + Math.PI + Math.random() * Math.PI / 4 - Math.PI / 8;
            double ratio = Math.random() * 0.2 + 0.3;
            xMove = speed * ratio * Math.cos(newRotate);
            yMove = speed * ratio * Math.sin(newRotate);
            updateRotate(newRotate);
            updateBoundRotate();
            return;
        }

        e.beCollidedBy(this);
        updateState(HIT);
        hit = e;
        xMove = yMove = 0;

        hitDeltaRadianRotate = rotate_R_MAIN - e.rotate_R_MAIN;
        hitDirect = e.direct;
        thisDirect = direct;

        hitDeltaX = x - e.x;//EXPLAIN: dùng cho các vật thể k xoay
        hitDeltaY = y - e.y;//EXPLAIN: dùng cho các vật thể k xoay

        double tempX = x + rotateRels[direct][X_INDEX] - e.x - e.rotateRels[e.direct][X_INDEX];
        double tempY = y + rotateRels[direct][Y_INDEX] - e.y - e.rotateRels[e.direct][Y_INDEX];

        hitDeltaXRelX = Algebra.rotateX(tempX, tempY, -hit.rotate_R_MAIN);
        hitDeltaYRelY = Algebra.rotateY(tempX, tempY, -hit.rotate_R_MAIN);
    }

    //SECTION: check
    public boolean isHit() {
        return state == HIT;
    }
}
