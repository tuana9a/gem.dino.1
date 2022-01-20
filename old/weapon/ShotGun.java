package com.tuana9a.OLD.weapon;

import com.tuana9a.OLD.Entity;
import com.tuana9a.OLD.nature.Animal;
import com.tuana9a.screen.GameScreen;
import com.tuana9a.utils.Algebra;

import java.util.ArrayList;
import java.util.Arrays;

public class ShotGun extends Shoot {

    private ShotGun(GameScreen gameScreen, int weaponId, double x, double y) {
        super(gameScreen, weaponId, x, y);
    }

    private ShotGun(GameScreen gameScreen, int weaponId, Animal owner) {
        super(gameScreen, weaponId, owner);
    }


    public static ShotGun construct(GameScreen gameScreen, int weaponId, double x, double y) {
        return new ShotGun(gameScreen,weaponId,x,y);
    }

    public static ShotGun construct(GameScreen gameScreen, int weaponId, Animal owner) {
        return new ShotGun(gameScreen, weaponId, owner);
    }


    @Override
    protected ArrayList<Entity> abstractAttack() {
        ShootOut bulletMain = new ShootOut(gameState, id, this, owner);

        double bulletStartX, bulletStartY;
        double xRelMain, yRelMain;//thu gọn code đoạn rotate do chỉ khác giá trị rotate trả về

        double xRelTemp = bounds_ORIGIN.w();
//        double xRelTemp = actualSize.width;

        double yRelTemp = 0;

        xRelMain = Algebra.rotateX(xRelTemp, yRelTemp, rotate_R_MAIN);
        yRelMain = Algebra.rotateY(xRelTemp, yRelTemp, rotate_R_MAIN);

        bulletStartX = x + rotateRels[direct][X_INDEX] + xRelMain - bulletMain.rotateRels[bulletMain.direct][X_INDEX];
        bulletStartY = y + rotateRels[direct][Y_INDEX] + yRelMain - bulletMain.rotateRels[bulletMain.direct][Y_INDEX];

        double radianMain = rotate_R_MAIN + Math.random() * accuracy - accuracy / 2;
        bulletMain.xMove = bulletMain.speed * Math.cos(radianMain);
        bulletMain.yMove = bulletMain.speed * Math.sin(radianMain);

        bulletMain.updatePosition(bulletStartX, bulletStartY);
        bulletMain.updateRotate(radianMain);
        bulletMain.updateBoundRotate();

        ArrayList<Entity> bullets = new ArrayList<>();
        bullets.add(bulletMain);

        double tempRatio = 1;
        int size = 2;
        int magazine = 2 * size;
        ShootOut[] bullet = new ShootOut[magazine];
        for (int i = 0; i < size; i++) {
            double radianTop = radianMain + (i + 1) * accuracy;
            double radianBot = radianMain - (i + 1) * accuracy;
            bullet[i] = new ShootOut(gameState, id, this, owner);
            bullet[i].xMove = bulletMain.speed * Math.cos(radianTop) * tempRatio;
            bullet[i].yMove = bulletMain.speed * Math.sin(radianTop) * tempRatio;
            bullet[i].updatePosition(bulletStartX, bulletStartY);
            bullet[i].updateRotate(radianTop);
            bullet[i].updateBoundRotate();

            bullet[i + size] = new ShootOut(gameState, id, this, owner);
            bullet[i + size].xMove = bulletMain.speed * Math.cos(radianBot) * tempRatio;
            bullet[i + size].yMove = bulletMain.speed * Math.sin(radianBot) * tempRatio;
            bullet[i + size].updatePosition(bulletStartX, bulletStartY);
            bullet[i + size].updateRotate(radianBot);
            bullet[i + size].updateBoundRotate();

        }
        bullets.addAll(Arrays.asList(bullet));

        return bullets;
    }
}
