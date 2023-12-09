package gemdinothegame.weapon;

import gemdinothegame.engine.EntityManager;
import gemdinothegame.enemy.Enemy;
import gemdinothegame.player.Player;

import java.util.ArrayList;

import gemdinothegame.entity.Animal;
import gemdinothegame.entity.Entity;
import gemdinothegame.entity.StaticEntity;
import gemdinothegame.input.KeyboardManager;
import gemdinothegame.input.MouseManager;
import gemdinothegame.utils.Algebra;
import gemdinothegame.animation.StateAnimation;
import gemdinothegame.animation.MoveAnimation;
import gemdinothegame.graphic.Assets;
import gemdinothegame.configs.ConfigWeapon;
import gemdinothegame.utils.Sound;

public abstract class Weapon extends StaticEntity {
    public static final int STATE_NUMBER = 5;
    public static final int HOMELESS = 0;
    public static final int IN_BAG = 1;
    public static final int SHOULDER = 2;
    public static final int USING = 3;
    public static final int ATTACK = 4;
    public Animal owner;
    public int damage;
    protected double accuracy;
    protected double attackSpeed;
    protected double rotateSpeed;
    protected double rotateDirect;
    public double reloadRatio;
    public double recoilX;
    public double recoilAmount;
    public double recoilRegen;
    public double radianRecoil;
    public double radianRecoilAmount;
    public double radianRecoilRegen;
    protected Sound attackSound;

    @Override
    protected void initCoreInfo(final int weaponId) {
        this.setSize(ConfigWeapon.widths[weaponId], ConfigWeapon.heights[weaponId]);
        this.setActualSize(ConfigWeapon.boundX[weaponId], ConfigWeapon.boundY[weaponId], ConfigWeapon.boundWidth[weaponId], ConfigWeapon.boundHeight[weaponId]);
        this.initActualSizeOrigin();
    }

    @Override
    protected void initOtherInfo(final int weaponId) {
        this.rotateRel = ConfigWeapon.rotateRels[weaponId];
        this.reloadRatio = ConfigWeapon.reloadRatios[weaponId];
        this.recoilAmount = ConfigWeapon.recoilAmounts[weaponId];
        this.recoilRegen = ConfigWeapon.recoilRegens[weaponId];
        this.radianRecoilAmount = ConfigWeapon.recoilRadianAmounts[weaponId];
        this.radianRecoilRegen = ConfigWeapon.recoilRadianRegens[weaponId];
        this.damage = ConfigWeapon.damages[weaponId];
        this.accuracy = ConfigWeapon.accuracies[weaponId];
        this.rotateSpeed = ConfigWeapon.rotateSpeeds[weaponId];
        this.attackSpeed = ConfigWeapon.attackSpeeds[weaponId];
        this.typicalTimer.deltaTime = (long) (1000.0 / this.attackSpeed);
        this.moveAnimation = new MoveAnimation(Assets.weapons[weaponId]);
    }

    @Override
    protected void initStateAnimation() {
        this.allStateAnimations = new StateAnimation[5];
    }

    public Weapon(final int weaponId, final double x, final double y) {
        super(weaponId, x, y);
        if (isBow(weaponId)) {
            this.updateRotate(1.5707963267948966);
        } else {
            this.updateRotate(0.0);
        }
        this.state = 0;
    }

    public Weapon(final int weaponId, final Animal owner) {
        super(weaponId, owner.x + owner.width / 2.0, owner.y + owner.height / 2.0);
        this.updateRotate(0.0);
        owner.takeWeapon(this);
        this.moveDirect = owner.moveDirect;
    }

    @Override
    public void update() {
        if (this.owner != null) {
            this.moveDirect = this.owner.moveDirect;
            if (this.isAttacking() && this.typicalTimer.isTime(this.reloadRatio)) {
                this.state = 3;
            }
            this.updateRotateRelative();
            if (this.isUsing() || this.isAttacking()) {
                if (this.moveDirect == 1) {
                    this.updatePosition(this.owner.rightHandX() - this.xRotateRelX, this.owner.rightHandY() - this.yRotateRelY);
                } else if (this.moveDirect == 0) {
                    this.updatePosition(this.owner.leftHandX() - this.xRotateRelX, this.owner.leftHandY() - this.yRotateRelY);
                }
                this.updateTypicalRotate();
                this.updateBoundWhenRotate();
                this.updateTypicalAttack();
            } else if (this.isOnShoulder()) {
                double spaceX = 0.0;
                final double spaceY = 0.0;
                double rightRadian;
                if (this instanceof Sword) {
                    rightRadian = 1.0995574287564276;
                    spaceX = 20.0;
                } else if (this instanceof Spear) {
                    rightRadian = -2.199114857512855;
                    spaceX = -20.0;
                } else if (this instanceof Shoot) {
                    rightRadian = -2.199114857512855;
                    spaceX = -20.0;
                } else {
                    rightRadian = 0.7853981633974483;
                }
                if (this.moveDirect == 1) {
                    this.updateRotate(rightRadian);
                    final double yStickOwnerBottom = -this.actualSize.y - this.actualSize.height / 2.0;
                    this.updatePosition(this.owner.rightShoulderX() - this.xRotateRelX - spaceX, this.owner.rightShoulderY() + yStickOwnerBottom + spaceY);
                } else if (this.moveDirect == 0) {
                    this.updateRotate(-rightRadian - 3.141592653589793);
                    final double yStickOwnerBottom = -this.actualSize.y - this.actualSize.height / 2.0;
                    this.updatePosition(this.owner.getLeftShoulderX() - this.xRotateRelX + spaceX, this.owner.getLeftShoulderY() + yStickOwnerBottom + spaceY);
                }
            }
            this.moveAnimation.update();
        }
        this.updatePositionCam();
        this.typicalUpdate();
    }

    protected abstract void typicalUpdate();

    public void attack() {
        EntityManager entityManager = app.getEntityManager();
        this.state = 4;
        entityManager.addAllEntities(this.typicalAttack());
    }

    private boolean aim(final Entity entity) {
        final double weaponX = this.x + this.xRotateRelX;
        final double weaponY = this.y + this.yRotateRelY;
        final double entityX = entity.x + entity.width / 2.0;
        final double entityY = entity.y + entity.height / 2.0;
        final double radianToEntity = Algebra.getRotate(weaponX, weaponY, entityX, entityY);
        final double minDelta = 0.031415926535897934;
        final double approximate = 5.0;
        final double deltaRadian = radianToEntity - this.radianRotateMain;
        return Math.abs(deltaRadian / minDelta) < approximate;
    }

    protected abstract ArrayList<Entity> typicalAttack();

    private void updateTypicalAttack() {
        KeyboardManager keyboardManager = app.getKeyboardManager();
        if (!this.typicalTimer.isTime()) {
            return;
        }
        if (this.owner instanceof Player && keyboardManager.fire) {
            this.attack();
            this.typicalTimer.reset();
        } else if (this.owner instanceof Enemy) {
            EntityManager entityManager = app.getEntityManager();
            final Player player = entityManager.getPlayer();
            if (((Enemy) this.owner).reactionTimer.isTime(-500L) && this.owner.canSee(player) && this.aim(player)) {
                this.attack();
                this.typicalTimer.reset();
            }
        }
    }

    private void updateTypicalRotate() {
        if (this.owner instanceof Player) {
            this.updateRadianRotateMouse();
        } else if (this.owner instanceof Enemy) {
            final EntityManager entityManager = app.getEntityManager();
            final Player player = entityManager.getPlayer();
            if (((Enemy) this.owner).reactionTimer.isTime(-500L)) {
                if (this.owner.canSee(player)) {
                    this.updateRadianRotateScripted(player);
                } else {
                    this.updateRadianRotateRandom();
                }
            } else if (this.radianRotateMain > 3.141592653589793) {
                this.rotateDirect = -Math.random();
            } else if (this.radianRotateMain < -3.141592653589793) {
                this.rotateDirect = Math.random();
            } else {
                this.rotateDirect = Math.random() * 2.0 - 1.0;
            }
        }
    }

    private void updateRadianRotateMouse() {
        MouseManager mouseManager = app.getMouseManager();
        final double mouseX = mouseManager.getX();
        final double mouseY = mouseManager.getY();
        final double radianToMouse = Algebra.getRotate(this.xRotateCam, this.yRotateCam, mouseX, mouseY);
        final double minDelta = 0.031415926535897934;
        final double approximate = 2.0;
        this.correctRadianRotate();
        final double deltaRadian = radianToMouse - this.radianRotateMain;
        if (Math.abs(deltaRadian / minDelta) < approximate) {
            this.updateRotate(this.radianRotateMain);
            return;
        }
        if (deltaRadian > 3.141592653589793) {
            this.radianRotateMain -= this.rotateSpeed;
        } else if (deltaRadian < -3.141592653589793) {
            this.radianRotateMain += this.rotateSpeed;
        } else {
            this.radianRotateMain += ((deltaRadian >= 0.0) ? 1 : -1) * this.rotateSpeed;
        }
        this.updateRotate(this.radianRotateMain);
    }

    private void updateRadianRotateScripted(final Entity e) {
        final double weaponX = this.x + this.xRotateRelX;
        final double weaponY = this.y + this.yRotateRelY;
        final double entityX = e.x + e.width / 2.0;
        final double entityY = e.y + e.height / 2.0;
        final double radianToEntity = Algebra.getRotate(weaponX, weaponY, entityX, entityY);
        final double minDelta = 0.031415926535897934;
        final double approximate = 5.0;
        this.correctRadianRotate();
        final double deltaRadian = radianToEntity - this.radianRotateMain;
        if (Math.abs(deltaRadian / minDelta) < approximate) {
            return;
        }
        if (deltaRadian > 3.141592653589793) {
            this.radianRotateMain -= this.rotateSpeed;
        } else if (deltaRadian < -3.141592653589793) {
            this.radianRotateMain += this.rotateSpeed;
        } else {
            this.radianRotateMain += ((deltaRadian >= 0.0) ? 1 : -1) * this.rotateSpeed;
        }
        this.updateRotate(this.radianRotateMain);
    }

    private void updateRadianRotateRandom() {
        this.updateRotate(this.radianRotateMain += this.rotateDirect * this.rotateSpeed);
    }

    public static boolean isShootWeapon(final int id) {
        for (final int shootId : ConfigWeapon.shootWeapons) {
            if (id == shootId) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSword(final int id) {
        for (final int swordId : ConfigWeapon.swordWeapons) {
            if (id == swordId) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSpear(final int id) {
        for (final int spearId : ConfigWeapon.spearWeapons) {
            if (id == spearId) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBow(final int id) {
        for (final int bowId : ConfigWeapon.bowWeapons) {
            if (id == bowId) {
                return true;
            }
        }
        return false;
    }

    public static boolean isShotGun(final int id) {
        for (final int shotGunId : ConfigWeapon.shotGunWeapons) {
            if (id == shotGunId) {
                return true;
            }
        }
        return false;
    }

    public static boolean isGatling(final int id) {
        for (final int gatlingId : ConfigWeapon.gatlingWeapons) {
            if (id == gatlingId) {
                return true;
            }
        }
        return false;
    }

    public boolean isHomeless() {
        return this.state == 0;
    }

    public boolean isUsing() {
        return this.state == 3;
    }

    public boolean isInBag() {
        return this.state == 1;
    }

    public boolean isOnShoulder() {
        return this.state == 2;
    }

    public boolean isAttacking() {
        return this.state == 4;
    }

    public void setAttackSpeed(final double attackSpeed) {
        this.attackSpeed = attackSpeed;
        this.typicalTimer.deltaTime = (long) (1000.0 / attackSpeed);
    }

    public double getAttackSpeed() {
        return this.attackSpeed;
    }

    public void setOwner(final Animal owner) {
        this.owner = owner;
    }

    public void setAttackSound(final Sound attackSound) {
        this.attackSound = attackSound;
    }
}
