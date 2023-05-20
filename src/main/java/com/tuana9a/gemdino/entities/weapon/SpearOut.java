// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.gemdino.entities.weapon;

import com.tuana9a.gemdino.entities.Animal;
import com.tuana9a.gemdino.entities.Entity;
import com.tuana9a.gemdino.utils.Algebra;

public class SpearOut extends WeaponOut
{
    public SpearOut(final int weaponOutId, final Weapon fromWeapon, final Animal owner) {
        super(weaponOutId, fromWeapon, owner);
    }
    
    @Override
    protected void typicalUpdate() {
        if (this.fromWeapon.isOnShoulder()) {
            this.state = 2;
            return;
        }
        final Spear s = (Spear)this.fromWeapon;
        this.moveDirect = s.moveDirect;
        this.updateRotate(s.radianRotateMain);
        double deltaX = 0.0;
        double deltaY = 0.0;
        final double weaponRecoilX = s.recoilX;
        double meleeRecoilX = 0.0;
        double meleeRecoilY = 0.0;
        final double space = -(float)this.width / 4.0f;
        final double xRelTemp = s.actualSizeOriginW + this.width / 2.0f + space;
        final double yRelTemp = s.actualSizeOriginY + s.actualSizeOriginH / 2.0f - s.yRotateRelY;
        if (s.moveDirect == 1) {
            deltaX = Algebra.rotateX(xRelTemp, yRelTemp, s.radianRotateMain);
            deltaY = Algebra.rotateY(xRelTemp, yRelTemp, s.radianRotateMain);
            meleeRecoilX = weaponRecoilX * Math.cos(s.radianRotateMain);
            meleeRecoilY = weaponRecoilX * Math.sin(s.radianRotateMain);
        }
        else if (s.moveDirect == 0) {
            deltaX = Algebra.rotateX(xRelTemp, -yRelTemp, s.radianRotateMain);
            deltaY = Algebra.rotateY(xRelTemp, -yRelTemp, s.radianRotateMain);
            meleeRecoilX = -weaponRecoilX * Math.cos(s.radianRotateMain);
            meleeRecoilY = -weaponRecoilX * Math.sin(s.radianRotateMain);
        }
        final double meleeX = s.x + s.xRotateRelX + deltaX - this.width / 2.0f;
        final double meleeY = s.y + s.yRotateRelY + deltaY - this.height / 2.0f;
        this.updatePosition(meleeX + meleeRecoilX, meleeY + meleeRecoilY);
    }
    
    @Override
    public void move() {
    }
    
    @Override
    public void intersectWith(final Entity e) {
        if (e instanceof WeaponOut || e instanceof Weapon || e.equals(this.owner) || this.intersectEntities.contains(e)) {
            return;
        }
        this.intersectEntities.add(e);
        e.hitBy(this);
    }
}
