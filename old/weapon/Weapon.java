package com.tuana9a.OLD.weapon;

import com.tuana9a.OLD.Entity;
import com.tuana9a.OLD.nature.Animal;
import com.tuana9a.OLD.nature.Enemy;
import com.tuana9a.OLD.nature.Player;
import com.tuana9a.ani.TwoDirectAnimation;
import com.tuana9a.app.KeyboardManager;
import com.tuana9a.app.MouseManager;
import com.tuana9a.entity.abilities.AbstractEntity;
import com.tuana9a.entity.config.ConfigEnemy;
import com.tuana9a.entity.config.ConfigWeapon;
import com.tuana9a.screen.GameScreen;
import com.tuana9a.utils.Algebra;
import com.tuana9a.utils.CommonUtils;

import java.util.ArrayList;

public abstract class Weapon extends StayEntity implements AbstractEntity {

    public static final int STATE_NUMBER = 5;
    public static final int
            NO_OWNER = 0,
            READY = 1,
            ATTACKING = 2,
            NOT_USE = 3,
            IN_BAG = 4;

    public Animal owner;
    public int damage;
    protected double accuracy;
    protected double attackSpeed;

    protected double rotateSpeed;
    protected double randomRotateDirect;

    public double attackRatio;//EXPLAIN! phần thời gian attack trong một lần tấn công
    public double recoilX, recoilAmountX, recoilRegenX;//giật, độ giật, độ hồi giật của vũ khí
    public double recoilR, recoilAmountR, recoilRegenR;// giật theo góc

    @Override
    protected void init(int weaponId) {
        setSize(ConfigWeapon.widths[weaponId], ConfigWeapon.heights[weaponId]);
        setBounds(
                ConfigWeapon.boundX[weaponId], ConfigWeapon.boundY[weaponId],
                ConfigWeapon.boundWidth[weaponId], ConfigWeapon.boundHeight[weaponId]
        );
        initBoundsOrigin();
    }

    @Override
    protected void initAbstract(int weaponId) {
        rotateRels = ConfigWeapon.rotateRels[weaponId];

        attackRatio = ConfigWeapon.attackRatios[weaponId];
        recoilAmountX = ConfigWeapon.recoilAmountXs[weaponId];
        recoilRegenX = ConfigWeapon.recoilRegenXs[weaponId];
        recoilAmountR = ConfigWeapon.recoilAmountRs[weaponId];
        recoilRegenR = ConfigWeapon.recoilRegenRs[weaponId];

        damage = ConfigWeapon.damages[weaponId];
        accuracy = ConfigWeapon.accuracies[weaponId];
        rotateSpeed = ConfigWeapon.rotateSpeeds[weaponId];
        attackSpeed = ConfigWeapon.attackSpeeds[weaponId];
        abstractTimer.delta = (long) (1000 / attackSpeed);
        randomRotateDirect = Math.random() * 2 - 1;

        moveAnimation = new TwoDirectAnimation();
    }


    protected Weapon(GameScreen gameScreen, int weaponId, double x, double y) {
        super(gameScreen, weaponId, x, y);
        updateRotate(0);
        updateBoundRotate();
        updateState(NO_OWNER);
    }

    protected Weapon(GameScreen gameScreen, int weaponId, Animal owner) {
        this(gameScreen, weaponId, owner.x + owner.width / 2, owner.y + owner.height / 2);
        owner.takeWeapon(this);
        direct = owner.direct;
    }


    public static Weapon construct(GameScreen gameScreen, int id, double x, double y) {
        if (isShoot(id)) {
            return Shoot.construct(gameScreen, id, x, y);
        }
        if (isSword(id)) {
            return Sword.construct(gameScreen, id, x, y);
        }
        if (isShield(id)) {
            return Shield.construct(gameScreen, id, x, y);
        }
        if (isSpear(id)) {
            return Spear.construct(gameScreen, id, x, y);
        }
        return null;
    }

    public static Weapon construct(GameScreen gameScreen, int id, Animal owner) {
        if (isShoot(id)) {
            return Shoot.construct(gameScreen, id, owner);
        }
        if (isSword(id)) {
            return Sword.construct(gameScreen, id, owner);
        }
        if (isShield(id)) {
            return Shield.construct(gameScreen, id, owner);
        }
        if (isSpear(id)) {
            return Spear.construct(gameScreen, id, owner);
        }
        return null;
    }


    //SECTION: update
    @Override
    public void update() {
        updateAbstract();//EXPLAIN! để ngoài vì có recoil mà vứt súng là nó sẽ không reset
        moveAnimation.update();
    }

    public void updateOwner() {
        update();
        direct = owner.direct;//CAUTION: bắt buộc phải có
        //EXPLAIN reset về using, reload chuẩn bị tấn công tiếp
        if (attacking() && abstractTimer.timeup(attackRatio)) {
            updateState(READY);
        }

        //EXPLAIN update góc quay và tấn công, trên vai, ...
        if (ready() || attacking()) {
            //update x,y rồi mới update tâm xoay đúng

            //EXPLAIN: one way to update
            //            updateRotate();  //update xoay rồi mới update tấn công đúng
            //            updateAttack();  //update tấn công sau khi xoay

            //EXPLAIN: other way to update
            if (owner instanceof Player) {
                updatePlayer();
            } else if (owner instanceof Enemy) {
                updateEnemy();
            }


        }
    }

    //EXPLAIN: trả về list các weapon out để add vào entity manager
    protected abstract ArrayList<Entity> abstractAttack();


    //SECTION: 2 loại player - enemy
    private boolean aim(Entity entity) {
        double weaponX = x + rotateRels[direct][X_INDEX];
        double weaponY = y + rotateRels[direct][Y_INDEX];

        double entityX = entity.x + entity.width / 2;
        double entityY = entity.y + entity.height / 2;

        double radianToEntity = Algebra.getRotate(weaponX, weaponY, entityX, entityY);
        double deltaRadian, minDelta = Math.PI / 100, approximate = 5;

        deltaRadian = radianToEntity - rotate_R_MAIN;
        // nếu đủ chuẩn tùy ý sẽ aim true ngược lại là false
        return Math.abs(deltaRadian / minDelta) < approximate;
    }

    //EXPLAIN: one way to update
    //    private void updateAttack() {
    //        if (!abstractTimer.isTimeUp()) return;
    //        Player player = entityManager.getPlayer();
    //
    //        if (owner instanceof Player) {
    //            if (gameState.getKeyboardManager().fire) {
    //                updateState(ATTACKING);
    //                entityManager.addAll(abstractAttack());
    //                abstractTimer.restart();
    //            }
    //
    //        } else if (owner instanceof Enemy) {
    //            Enemy enemy = (Enemy) owner;
    //            if (enemy.reactionTimer.isTimeUp(ConfigEnemy.REACT) && enemy.canSee(player) && aim(player)) {
    //                updateState(ATTACKING);
    //                entityManager.addAll(abstractAttack());
    //                abstractTimer.restart();
    //            }
    //        }
    //    }
    //
    //    private void updateRotate() {
    //        Player player = entityManager.getPlayer();
    //
    //        if (owner instanceof Player) {
    //            updateRotateMouse();
    //
    //        } else if (owner instanceof Enemy) {
    //            Enemy enemy = (Enemy) owner;
    //            if (enemy.reactionTimer.isTimeUp(ConfigEnemy.REACT)) {
    //                if (enemy.canSee(player)) {
    //                    updateRotateEntity(player);
    //                } else {
    //                    updateRotateRandom();
    //                }
    //            }
    //        }
    //    }

    //EXPLAIN: other way to update
    private void updatePlayer() {
        //SECTION: update rotate
        updateRotateMouse();
        updateBoundRotate();

        //SECTION: update attack
        if (!abstractTimer.timeup()) {
            return;
        }
        KeyboardManager keyboardManager = KeyboardManager.getInstance();
        if (keyboardManager.isPressing(KeyboardManager.ATTACK_KEY)) {
            updateState(ATTACKING);
            entityManager.add(abstractAttack());
            abstractTimer.reset();
        }
    }

    private void updateEnemy() {
        Player player = entityManager.getPlayer();
        Enemy enemy = (Enemy) owner;

        //SECTION: update rotate
        if (enemy.reactionTimer.timeup(ConfigEnemy.REACT)) {
            if (enemy.canSee(player)) {
                updateRotateEntity(player);
            } else {
                updateRotateRandom();
            }
        }
        updateBoundRotate();

        //SECTION: update attack
        if (!abstractTimer.timeup()) {
            return;
        }
        if (enemy.reactionTimer.timeup(ConfigEnemy.REACT) && enemy.canSee(player) && aim(player)) {
            updateState(ATTACKING);
            entityManager.add(abstractAttack());
            abstractTimer.reset();
        }
    }


    //SECTION: update rotate
    private void updateRotateMouse() {
        double cenX = xRotateCam;
        double cenY = yRotateCam;
        MouseManager mouseManager = MouseManager.getInstance();
        double desX = mouseManager.getX();
        double desY = mouseManager.getY();

        double radianToDes = Algebra.getRotate(cenX, cenY, desX, desY);
        double deltaRadian, minDelta = Math.PI / 100, approximate = 2;

        //        deltaRadian = radianToDes - rotateMain;
        deltaRadian = CommonUtils.minDeltaRotate(radianToDes - rotate_R_MAIN);
        if (Math.abs(deltaRadian / minDelta) < approximate) {
            return;
        }

        rotate_R_MAIN += (deltaRadian >= 0 ? 1 : -1) * rotateSpeed;
        updateRotate(rotate_R_MAIN);
    }

    private void updateRotateEntity(Entity e) {
        double cenX = x + rotateRels[direct][X_INDEX];
        double cenY = y + rotateRels[direct][Y_INDEX];
        double desX = e.x + e.width / 2;
        double desY = e.y + e.height / 2;

        double radianToDes = Algebra.getRotate(cenX, cenY, desX, desY);
        double deltaRadian, minDelta = Math.PI / 100, approximate = 5;

        //        deltaRadian = radianToDes - rotateMain;
        deltaRadian = CommonUtils.minDeltaRotate(radianToDes - rotate_R_MAIN);
        if (Math.abs(deltaRadian / minDelta) < approximate) {
            return;
        }

        rotate_R_MAIN += (deltaRadian >= 0 ? 1 : -1) * rotateSpeed;
        updateRotate(rotate_R_MAIN);
    }

    private void updateRotateRandom() {
        if (Math.random() > 0.99) {
            randomRotateDirect = Math.random() * 2 - 1;
        }

        rotate_R_MAIN += randomRotateDirect * rotateSpeed;
        updateRotate(rotate_R_MAIN);
    }


    //SECTION: check state
    public boolean noOwner() {
        return state == NO_OWNER;
    }

    public boolean ready() {
        return state == READY;
    }

    public boolean attacking() {
        return state == ATTACKING;
    }

    public boolean notUse() {
        return state == NOT_USE;
    }

    public boolean inBag() {
        return state == IN_BAG;
    }


    //SECTION: check kind of weapon
    public static boolean isShoot(int id) {
        for (int check : ConfigWeapon.shootIds) {
            if (id == check) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSword(int id) {
        for (int check : ConfigWeapon.swordIds) {
            if (id == check) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSpear(int id) {
        for (int check : ConfigWeapon.spearIds) {
            if (id == check) {
                return true;
            }
        }
        return false;
    }

    public static boolean isShield(int id) {
        for (int check : ConfigWeapon.shieldIds) {
            if (id == check) {
                return true;
            }
        }
        return false;
    }


    //SECTION: getter setter
    public void setAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
        abstractTimer.delta = (long) (1000 / attackSpeed);
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public void setOwner(Animal owner) {
        this.owner = owner;
    }
}
