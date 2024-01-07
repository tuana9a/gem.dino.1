package gemdino.weapon;

import gemdino.app.App;
import gemdino.entity.Animal;
import gemdino.entity.MovingEntity;
import gemdino.animation.StateAnimation;
import gemdino.animation.MoveAnimation;
import gemdino.graphic.Assets;
import gemdino.configs.ConfigWeaponOut;
import gemdino.entity.renderer.InnerBoundRenderer;

import java.awt.*;

public abstract class WeaponOut extends MovingEntity {
    public static final int STATE_NUMBER = 3;
    public static final int MOVING = 0;
    public static final int HIT = 1;
    public static final int TIME_OUT = 2;
    protected Weapon fromWeapon;
    protected Animal owner;
    public int damage;

    @Override
    protected void initCoreInfo(final int weaponOutId) {
        this.setSize(ConfigWeaponOut.widths[weaponOutId], ConfigWeaponOut.heights[weaponOutId]);
        this.setActualSize(ConfigWeaponOut.boundX[weaponOutId], ConfigWeaponOut.boundY[weaponOutId], ConfigWeaponOut.boundWidth[weaponOutId], ConfigWeaponOut.boundHeight[weaponOutId]);
        this.initActualSizeOrigin();
    }

    @Override
    protected void initOtherInfo(final int weaponOutId) {
        this.rotateRel = ConfigWeaponOut.rotateRels[weaponOutId];
        this.speed = ConfigWeaponOut.speeds[weaponOutId];
        this.damage = ConfigWeaponOut.damages[weaponOutId];
        this.typicalTimer.deltaTime = ConfigWeaponOut.lifeTimeLimit[weaponOutId];
        this.moveAnimation = new MoveAnimation(Assets.weaponOuts[weaponOutId]);
    }

    @Override
    protected void initStateAnimation() {
        this.allStateAnimations = new StateAnimation[3];
    }

    public WeaponOut(App app, final int weaponOutId, final Weapon fromWeapon, final Animal owner) {
        super(app, weaponOutId, fromWeapon.x, fromWeapon.y);
        this.state = 0;
        this.owner = owner;
        this.fromWeapon = fromWeapon;
        this.moveDirect = fromWeapon.moveDirect;
        this.innerBoundRenderer = new InnerBoundRenderer(this, Color.GREEN, Color.CYAN);
        this.updateRotateRelative();
        this.updateRotate(fromWeapon.radianRotateMain);
    }

    @Override
    public void update() {
        this.updateInteract();
        this.typicalUpdate();
        this.moveAnimation.update();
        if (this.typicalTimer.deltaTime != -1L && this.typicalTimer.isTime()) {
            this.state = 2;
        }
    }

    protected abstract void typicalUpdate();

    public boolean isMoving() {
        return this.state == 0;
    }

    public boolean isTimeOut() {
        return this.state == 2;
    }
}
