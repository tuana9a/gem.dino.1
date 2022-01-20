package com.tuana9a.OLD.weapon;

import com.tuana9a.OLD.Entity;
import com.tuana9a.OLD.nature.Animal;
import com.tuana9a.ani.TwoDirectAnimation;
import com.tuana9a.app.App;
import com.tuana9a.entity.abilities.AbstractEntity;
import com.tuana9a.entity.config.ConfigWeaponOut;
import com.tuana9a.screen.GameScreen;

public abstract class WeaponOut extends MoveEntity implements AbstractEntity {

    public static final int STATE_NUMBER = 3;
    public static final int MOVING = 0, HIT = 1, TIME_OUT = 2;

    public Weapon weapon;//EXPLAIN: from weapon
    public Animal owner;//EXPLAIN: from animal

    public int damage;

    @Override
    protected void init(int weaponOutId) {
        setSize(ConfigWeaponOut.widths[weaponOutId], ConfigWeaponOut.heights[weaponOutId]);
        setBounds(
                ConfigWeaponOut.boundX[weaponOutId], ConfigWeaponOut.boundY[weaponOutId],
                ConfigWeaponOut.boundWidth[weaponOutId], ConfigWeaponOut.boundHeight[weaponOutId]
        );
        initBoundsOrigin();
    }

    @Override
    protected void initAbstract(int weaponOutId) {
        rotateRels = ConfigWeaponOut.rotateRels[weaponOutId];
        speed = ConfigWeaponOut.speeds[weaponOutId];
        damage = ConfigWeaponOut.damages[weaponOutId];
        abstractTimer.delta = ConfigWeaponOut.lifeTimeLimit[weaponOutId];
        moveAnimation = new TwoDirectAnimation();
    }

    public WeaponOut(GameScreen gameScreen, int weaponOutId, Weapon weapon, Animal owner) {
        super(gameScreen, weaponOutId, weapon.x, weapon.y);
        updateState(MOVING);
        this.owner = owner;
        this.weapon = weapon;
        this.direct = weapon.direct;
        updateRotate(weapon.rotate_R_MAIN);
        updateBoundRotate();
    }


    @Override
    public void update() {
        updateInteract();
        updateAbstract();
        moveAnimation.update();

        //EXPLAIN: typical timer is life time of weapon out
        if (!isForever() && abstractTimer.timeup()) {
            updateState(TIME_OUT);
            entityManager.remove(this);
        }
    }

    //SECTION: collide
    @Override
    public void beCollidedBy(Entity e) {
    }

    @Override
    public void updateInteract() {
    }

    //SECTION: checking
    public boolean isMoving() {
        return state == MOVING;
    }

    public boolean isTimeOut() {
        return state == TIME_OUT;
    }

    public boolean isForever() {
        return abstractTimer.delta == App.Timer._4EVER;
    }

    public Animal getOwner() {
        return owner;
    }
}
