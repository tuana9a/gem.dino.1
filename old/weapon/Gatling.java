package com.tuana9a.OLD.weapon;

import com.tuana9a.OLD.Entity;
import com.tuana9a.OLD.nature.Animal;
import com.tuana9a.screen.GameScreen;
import com.tuana9a.utils.Algebra;

import java.util.ArrayList;

public class Gatling extends Shoot {

    private Gatling(GameScreen gameScreen, int weaponId, double x, double y) {
        super(gameScreen, weaponId, x, y);
    }

    private Gatling(GameScreen gameScreen, int weaponId, Animal owner) {
        super(gameScreen, weaponId, owner);
    }


    public static Gatling construct(GameScreen gameScreen, int weaponId, double x, double y) {
        return new Gatling(gameScreen, weaponId, x, y);
    }

    public static Gatling construct(GameScreen gameScreen, int weaponId, Animal owner) {
        return new Gatling(gameScreen, weaponId, owner);
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

        bulletStartX = x + rotateRels[direct][Entity.X_INDEX] + xRelMain - bulletMain.rotateRels[bulletMain.direct][Entity.X_INDEX];
        bulletStartY = y + rotateRels[direct][Entity.Y_INDEX] + yRelMain - bulletMain.rotateRels[bulletMain.direct][Entity.Y_INDEX];

        double radianMain = rotate_R_MAIN + Math.random() * accuracy - accuracy / 2;
        bulletMain.xMove = bulletMain.speed * Math.cos(radianMain);
        bulletMain.yMove = bulletMain.speed * Math.sin(radianMain);

        bulletMain.updatePosition(bulletStartX, bulletStartY);
        bulletMain.updateRotate(radianMain);
        bulletMain.updateBoundRotate();

        ArrayList<Entity> bullets = new ArrayList<>();
        bullets.add(bulletMain);

//        double tempRatio = 0.9;
//        int size = 1;
//        int magazine = 2 * size;
//
//        ShootOut[] bullet = new ShootOut[magazine];
//        for (int i = 0; i < size; i++) {
//            double radianTop = radianMain + (i + 1) * accuracy;
//            double radianBot = radianMain - (i + 1) * accuracy;
//
//            double xRelTop = Algebra.rotateX(xRelTemp, yRelTemp, radianTop);
//            double yRelTop = Algebra.rotateY(xRelTemp, yRelTemp, radianTop);
//
//            double xRelBot = Algebra.rotateX(xRelTemp, yRelTemp, radianBot);
//            double yRelBot = Algebra.rotateY(xRelTemp, yRelTemp, radianBot);
//
//            double bulletStartTopX = x + rotateRels[direct][X] + xRelTop - bulletMain.rotateRels[bulletMain.direct][X];
//            double bulletStartTopY = y + rotateRels[direct][Y] + yRelTop - bulletMain.rotateRels[bulletMain.direct][Y];
//            double bulletStartBotX = x + rotateRels[direct][X] + xRelBot - bulletMain.rotateRels[bulletMain.direct][X];
//            double bulletStartBotY = y + rotateRels[direct][Y] + yRelBot - bulletMain.rotateRels[bulletMain.direct][Y];
//
//            bullet[i] = new ShootOut(gameState, id, this, owner);
////                bullet[i].xMove = bulletMain.xMove * tempRatio;
////                bullet[i].yMove = bulletMain.yMove * tempRatio;
////                bullet[i].updateRotate(radianMain);
//
//            bullet[i].xMove = bulletMain.speed * Math.cos(radianTop) * tempRatio;
//            bullet[i].yMove = bulletMain.speed * Math.sin(radianTop) * tempRatio;
//            bullet[i].updatePosition(bulletStartTopX, bulletStartTopY);
//            bullet[i].updateRotate(radianTop);
//
//            bullet[i + size] = new ShootOut(gameState, id, this, owner);
////                bullet[i + size].xMove = bulletMain.xMove * tempRatio;
////                bullet[i + size].yMove = bulletMain.yMove * tempRatio;
////                bullet[i + size].updateRotate(radianMain);
//
//            bullet[i + size].xMove = bulletMain.speed * Math.cos(radianBot) * tempRatio;
//            bullet[i + size].yMove = bulletMain.speed * Math.sin(radianBot) * tempRatio;
//            bullet[i + size].updatePosition(bulletStartBotX, bulletStartBotY);
//            bullet[i + size].updateRotate(radianBot);
//        }
////            bullets.remove(bulletMain);
//        bullets.addAll(Arrays.asList(bullet));

        return bullets;
    }
}
