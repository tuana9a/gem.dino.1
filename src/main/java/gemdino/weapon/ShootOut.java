package gemdino.weapon;

import gemdino.app.App;
import gemdino.engine.EntityManager;
import gemdino.entity.StaticObject;
import gemdino.animation.StateAnimation;
import gemdino.entity.Animal;
import gemdino.entity.Entity;
import gemdino.graphic.Assets;

public class ShootOut extends WeaponOut {
    protected Entity hitEntity;
    protected double hitDeltaX;
    protected double hitDeltaY;
    protected double hitRadianRotate;
    protected int hitDirect;

    public ShootOut(App app, final int weaponOutId, final Weapon fromWeapon, final Animal owner) {
        super(app, weaponOutId, fromWeapon, owner);
    }

    @Override
    protected void initStateAnimation() {
        super.initStateAnimation();
        switch (this.id) {
            case 0: {
                this.allStateAnimations[0] = new StateAnimation(Assets.shotGunOutEffect, new double[][]{{0.0, 0.0}, {0.0, 0.0}}, this.width, this.height);
                break;
            }
            case 2: {
                this.allStateAnimations[0] = new StateAnimation(Assets.bowOutEffect, new double[][]{{0.0, 0.0}, {0.0, 0.0}}, this.width, this.height);
                break;
            }
            case 4: {
                this.allStateAnimations[0] = new StateAnimation(Assets.gatlingOutEffect, new double[][]{{0.0, 0.0}, {0.0, 0.0}}, this.width, this.height);
                break;
            }
        }
    }

    @Override
    protected void typicalUpdate() {
        EntityManager entityManager = app.getEntityManager();
        if (this.hitEntity != null) {
            if (!entityManager.isManage(this.hitEntity)) {
                this.state = 2;
            }
            this.updatePosition(this.hitEntity.x + this.hitDeltaX, this.hitEntity.y + this.hitDeltaY);
        } else {
            this.move();
        }
    }

    @Override
    public void move() {
        if (this.hitEntity != null) {
            return;
        }
        this.x += this.xMove;
        this.y += this.yMove;
    }

    @Override
    public void intersectWith(final Entity e) {
        if (this.hitEntity != null || e instanceof WeaponOut || e instanceof Weapon || e.equals(this.owner)) {
            return;
        }
        if (e instanceof StaticObject && ((StaticObject) e).penAble) {
            return;
        }
        e.hitBy(this);
        this.hitEntity = e;
        this.state = 1;
        final double n = 0.0;
        this.yMove = n;
        this.xMove = n;
        this.hitRadianRotate = this.radianRotateMain;
        this.hitDirect = e.moveDirect;
        this.hitDeltaX = this.x - e.x;
        this.hitDeltaY = this.y - e.y;
    }

    @Override
    public void hitBy(final WeaponOut wo) {
        this.state = 2;
    }

    public boolean isHit() {
        return this.state == 1;
    }
}
