package gemdinothegame.weapon;

import java.util.Arrays;

import gemdinothegame.entity.Animal;
import gemdinothegame.entity.Entity;
import gemdinothegame.utils.Algebra;

import java.util.ArrayList;

import gemdinothegame.configs.ConfigWeapon;
import gemdinothegame.animation.StateAnimation;
import gemdinothegame.graphic.Assets;

public class Shoot extends Weapon {
    public Shoot(final int weaponId, final double x, final double y) {
        super(weaponId, x, y);
    }

    public Shoot(final int weaponId, final Animal owner) {
        super(weaponId, owner);
    }

    @Override
    protected void initStateAnimation() {
        super.initStateAnimation();
        switch (this.id) {
            case 0: {
                this.allStateAnimations[4] = new StateAnimation(Assets.shotGunSplash, new double[][]{{-this.width / 2.0, -4.0}, {this.width / 2.0, -4.0}}, this.width, this.height);
                break;
            }
            case 2: {
                final double temp = ConfigWeapon.accuracies[2];
                this.allStateAnimations[3] = new StateAnimation(Assets.bowReload, new double[][]{{this.width / 4.0, 0.0}, {-this.width / 4.0, 0.0}}, new double[][]{{-2.0 * temp, -temp, 0.0, temp, 2.0 * temp}, {-2.0 * temp, -temp, 0.0, temp, 2.0 * temp}}, this.width, this.height);
                break;
            }
            case 4: {
                this.allStateAnimations[4] = new StateAnimation(Assets.gatlingSplash, new double[][]{{-this.width / 4.0, 0.0}, {this.width / 4.0, 0.0}}, this.width, this.height);
                break;
            }
        }
    }

    @Override
    protected void typicalUpdate() {
        if (this.moveDirect == 1) {
            if (this.isAttacking()) {
                this.recoilX = ((this.recoilX > 0.0) ? (-this.recoilX) : this.recoilX) - this.recoilAmount;
            } else if (this.recoilX != 0.0) {
                this.recoilX = ((this.recoilX > 0.0) ? (-this.recoilX) : this.recoilX) + this.recoilRegen;
            }
        } else if (this.moveDirect == 0) {
            if (this.isAttacking()) {
                this.recoilX = ((this.recoilX < 0.0) ? (-this.recoilX) : this.recoilX) + this.recoilAmount;
            } else if (this.recoilX != 0.0) {
                this.recoilX = ((this.recoilX < 0.0) ? (-this.recoilX) : this.recoilX) - this.recoilRegen;
            }
        }
    }

    public ArrayList<Entity> typicalAttack() {
        final ShootOut bulletMain = new ShootOut(this.id, this, this.owner);
        final double xRelTemp = this.actualSizeOriginW;
        final double yRelTemp = 0.0;
        final double xRelMain = Algebra.rotateX(xRelTemp, yRelTemp, this.radianRotateMain);
        final double yRelMain = Algebra.rotateY(xRelTemp, yRelTemp, this.radianRotateMain);
        final double bulletStartX = this.x + this.xRotateRelX + xRelMain - bulletMain.xRotateRelX;
        final double bulletStartY = this.y + this.yRotateRelY + yRelMain - bulletMain.yRotateRelY;
        final double radianMain = this.radianRotateMain + Math.random() * this.accuracy - this.accuracy / 2.0;
        bulletMain.xMove = bulletMain.speed * Math.cos(radianMain);
        bulletMain.yMove = bulletMain.speed * Math.sin(radianMain);
        bulletMain.updatePosition(bulletStartX, bulletStartY);
        bulletMain.updateRotate(radianMain);
        final ArrayList<Entity> bullets = new ArrayList<Entity>();
        bullets.add(bulletMain);
        if (isBow(this.id)) {
            final double tempRatio = 0.9;
            final int size = 2;
            final int magazine = 2 * size;
            final ShootOut[] bullet = new ShootOut[magazine];
            for (int i = 0; i < size; ++i) {
                final double radianTop = radianMain + (i + 1) * this.accuracy;
                final double radianBot = radianMain - (i + 1) * this.accuracy;
                final double xRelTop = Algebra.rotateX(xRelTemp, yRelTemp, radianTop);
                final double yRelTop = Algebra.rotateY(xRelTemp, yRelTemp, radianTop);
                final double xRelBot = Algebra.rotateX(xRelTemp, yRelTemp, radianBot);
                final double yRelBot = Algebra.rotateY(xRelTemp, yRelTemp, radianBot);
                final double bulletStartTopX = this.x + this.xRotateRelX + xRelTop - bulletMain.xRotateRelX;
                final double bulletStartTopY = this.y + this.yRotateRelY + yRelTop - bulletMain.yRotateRelY;
                final double bulletStartBotX = this.x + this.xRotateRelX + xRelBot - bulletMain.xRotateRelX;
                final double bulletStartBotY = this.y + this.yRotateRelY + yRelBot - bulletMain.yRotateRelY;
                bullet[i] = new ShootOut(this.id, this, this.owner);
                bullet[i].xMove = bulletMain.speed * Math.cos(radianTop) * tempRatio;
                bullet[i].yMove = bulletMain.speed * Math.sin(radianTop) * tempRatio;
                bullet[i].updatePosition(bulletStartTopX, bulletStartTopY);
                bullet[i].updateRotate(radianTop);
                bullet[i + size] = new ShootOut(this.id, this, this.owner);
                bullet[i + size].xMove = bulletMain.speed * Math.cos(radianBot) * tempRatio;
                bullet[i + size].yMove = bulletMain.speed * Math.sin(radianBot) * tempRatio;
                bullet[i + size].updatePosition(bulletStartBotX, bulletStartBotY);
                bullet[i + size].updateRotate(radianBot);
            }
            bullets.addAll(Arrays.asList(bullet));
        } else if (isShotGun(this.id)) {
            final double tempRatio = 1.0;
            final int size = 2;
            final int magazine = 2 * size;
            final ShootOut[] bullet = new ShootOut[magazine];
            for (int i = 0; i < size; ++i) {
                final double radianTop = radianMain + (i + 1) * this.accuracy;
                final double radianBot = radianMain - (i + 1) * this.accuracy;
                bullet[i] = new ShootOut(this.id, this, this.owner);
                bullet[i].xMove = bulletMain.speed * Math.cos(radianTop) * tempRatio;
                bullet[i].yMove = bulletMain.speed * Math.sin(radianTop) * tempRatio;
                bullet[i].updatePosition(bulletStartX, bulletStartY);
                bullet[i].updateRotate(radianTop);
                bullet[i + size] = new ShootOut(this.id, this, this.owner);
                bullet[i + size].xMove = bulletMain.speed * Math.cos(radianBot) * tempRatio;
                bullet[i + size].yMove = bulletMain.speed * Math.sin(radianBot) * tempRatio;
                bullet[i + size].updatePosition(bulletStartX, bulletStartY);
                bullet[i + size].updateRotate(radianBot);
            }
            bullets.addAll(Arrays.asList(bullet));
        } else if (isGatling(this.id)) {
            final double tempRatio = 0.9;
            final int size = 1;
            final int magazine = 2 * size;
            final ShootOut[] bullet = new ShootOut[magazine];
            for (int i = 0; i < size; ++i) {
                final double radianTop = radianMain + (i + 1) * this.accuracy;
                final double radianBot = radianMain - (i + 1) * this.accuracy;
                final double xRelTop = Algebra.rotateX(xRelTemp, yRelTemp, radianTop);
                final double yRelTop = Algebra.rotateY(xRelTemp, yRelTemp, radianTop);
                final double xRelBot = Algebra.rotateX(xRelTemp, yRelTemp, radianBot);
                final double yRelBot = Algebra.rotateY(xRelTemp, yRelTemp, radianBot);
                final double bulletStartTopX = this.x + this.xRotateRelX + xRelTop - bulletMain.xRotateRelX;
                final double bulletStartTopY = this.y + this.yRotateRelY + yRelTop - bulletMain.yRotateRelY;
                final double bulletStartBotX = this.x + this.xRotateRelX + xRelBot - bulletMain.xRotateRelX;
                final double bulletStartBotY = this.y + this.yRotateRelY + yRelBot - bulletMain.yRotateRelY;
                bullet[i] = new ShootOut(this.id, this, this.owner);
                bullet[i].xMove = bulletMain.speed * Math.cos(radianTop) * tempRatio;
                bullet[i].yMove = bulletMain.speed * Math.sin(radianTop) * tempRatio;
                bullet[i].updatePosition(bulletStartTopX, bulletStartTopY);
                bullet[i].updateRotate(radianTop);
                bullet[i + size] = new ShootOut(this.id, this, this.owner);
                bullet[i + size].xMove = bulletMain.speed * Math.cos(radianBot) * tempRatio;
                bullet[i + size].yMove = bulletMain.speed * Math.sin(radianBot) * tempRatio;
                bullet[i + size].updatePosition(bulletStartBotX, bulletStartBotY);
                bullet[i + size].updateRotate(radianBot);
            }
            bullets.addAll(Arrays.asList(bullet));
        }
        return bullets;
    }
}
