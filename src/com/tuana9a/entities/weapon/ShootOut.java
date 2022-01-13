// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.entities.weapon;

import com.tuana9a.engine.EntityManager;
import com.tuana9a.entities.StaticObject;
import com.tuana9a.animation.StateAnimation;
import com.tuana9a.graphic.Assets;
import com.tuana9a.entities.Animal;
import com.tuana9a.entities.Entity;

public class ShootOut extends WeaponOut
{
    protected Entity hitEntity;
    protected double hitDeltaX;
    protected double hitDeltaY;
    protected double hitRadianRotate;
    protected int hitDirect;
    
    public ShootOut(final int weaponOutId, final Weapon fromWeapon, final Animal owner) {
        super(weaponOutId, fromWeapon, owner);
    }
    
    @Override
    protected void initStateAnimation() {
        super.initStateAnimation();
        switch (this.id) {
            case 0: {
                this.allStateAnimations[0] = new StateAnimation(Assets.shotGunOutEffect, new double[][] { { 0.0, 0.0 }, { 0.0, 0.0 } }, this.width, this.height);
                break;
            }
            case 2: {
                this.allStateAnimations[0] = new StateAnimation(Assets.bowOutEffect, new double[][] { { 0.0, 0.0 }, { 0.0, 0.0 } }, this.width, this.height);
                break;
            }
            case 4: {
                this.allStateAnimations[0] = new StateAnimation(Assets.gatlingOutEffect, new double[][] { { 0.0, 0.0 }, { 0.0, 0.0 } }, this.width, this.height);
                break;
            }
        }
    }
    
    @Override
    protected void typicalUpdate() {
        EntityManager entityManager = EntityManager.getInstance();
        if (this.hitEntity != null) {
            if (!entityManager.isManage(this.hitEntity)) {
                this.state = 2;
            }
            this.updatePosition(this.hitEntity.x + this.hitDeltaX, this.hitEntity.y + this.hitDeltaY);
        }
        else {
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
        if (e instanceof StaticObject && ((StaticObject)e).penAble) {
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
