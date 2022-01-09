// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.entities;

import java.util.Arrays;

import com.tuana9a.App;
import com.tuana9a.constants.Comparators;
import com.tuana9a.entities.weapon.ShootOut;

import java.awt.Graphics;

import com.tuana9a.entities.enemy.Enemy;

import com.tuana9a.entities.weapon.WeaponOut;
import com.tuana9a.entities.weapon.Weapon;

import java.util.ArrayList;

import com.tuana9a.entities.player.Player;
import com.tuana9a.environment.Camera;
import com.tuana9a.input.KeyboardManager;

public class EntityManager {
    private static final EntityManager instance = new EntityManager();

    private Player player;
    private AnimalHand playerHand;
    private final ArrayList<Entity> entities;
    private final ArrayList<Weapon> weapons;
    private final ArrayList<WeaponOut> weaponOuts;

    private EntityManager() {
        this.entities = new ArrayList<>(100);
        this.weapons = new ArrayList<>(10);
        this.weaponOuts = new ArrayList<>(200);
    }

    public static EntityManager getInstance() {
        return instance;
    }

    public void updateAll() {
        KeyboardManager keyboardManager = KeyboardManager.getInstance();
        final Camera camera = Camera.getInstance();
        this.player.update();
        if (this.player == null) {
            return;
        }
        if (!keyboardManager.freeCamMode) {
            camera.centerOnEntity(this.player);
        }
        for (final Entity e : this.entities) {
            if (e != null) {
                if (e instanceof MovingEntity) {
                    final MovingEntity eActive = (MovingEntity) e;
                    this.checkCollideWithOthers(eActive);
                }
                if (!(e instanceof Player)) {
                    if (!(e instanceof Weapon)) {
                        if (e instanceof Enemy) {
                            final Enemy enemy = (Enemy) e;
                            if (enemy.isDead() && enemy.deadTime.isTime()) {
                                this.removeEntity(e);
                            }
                        } else if (e instanceof WeaponOut) {
                            final WeaponOut weaponOut = (WeaponOut) e;
                            if (weaponOut.isTimeOut()) {
                                this.removeEntity(e);
                            }
                            continue;
                        }
                        e.update();
                    }
                }
            }
        }
        for (final Weapon w : this.weapons) {
            if (w != null) {
                w.update();
            }
        }
        for (final WeaponOut wo : this.weaponOuts) {
            if (wo != null) {
                wo.update();
            }
        }
        this.entities.sort(Comparators.compareY);
        this.weaponOuts.sort(Comparators.compareY);
    }

    public void updateAllEveryRelCam() {
        for (final Entity e : this.entities) {
            if (e == null) {
                continue;
            }
            e.updatePositionCam();
            e.updateRotatePositionCam();
        }
    }

    public void renderAll(final Graphics g) {
        KeyboardManager keyboardManager = KeyboardManager.getInstance();
        if (this.player == null) {
            return;
        }
        for (final WeaponOut wo : this.weaponOuts) {
            if (wo == null) {
                continue;
            }
            wo.render(g);
        }
        for (final Entity e : this.entities) {
            if (e != null) {
                if (e instanceof WeaponOut) {
                    continue;
                }
                e.render(g);
            }
        }
        if (keyboardManager.rotationMode && this.playerHand != null) {
            this.playerHand.renderSelectedWeapon(g);
        }
        if (keyboardManager.innerBoundMode) {
            for (final WeaponOut wo : this.weaponOuts) {
                if (wo == null) {
                    continue;
                }
                wo.render(g);
            }
        }
    }

    private void checkCollideWithOthers(final MovingEntity e) {
        if (e instanceof ShootOut && ((ShootOut) e).isHit()) {
            return;
        }
        for (final Entity other : this.entities) {
            if (other != null) {
                if (other != e) {
                    if (e.isCollideWith(e.xMove, e.yMove, other)) {
                        e.intersectWith(other);
                    }
                }
            }
        }
    }

    public boolean isManage(final Entity e) {
        return this.entities.contains(e);
    }

    public void removeEntity(final Entity e) {
        this.entities.set(this.entities.indexOf(e), null);
        if (e instanceof WeaponOut) {
            this.weaponOuts.set(this.weaponOuts.indexOf(e), null);
        } else if (e instanceof Weapon) {
            this.weapons.set(this.weapons.indexOf(e), null);
        }
    }

    public void eraseEveryThing() {
        this.entities.clear();
        this.weapons.clear();
        this.weaponOuts.clear();
        this.player = null;
        this.playerHand = null;
    }

    public void nullEveryThing() {
        for (final Entity e : this.entities) {
            this.removeEntity(e);
        }
        this.player = null;
        this.playerHand = null;
    }

    public void nullEveryThingExceptPlayer() {
        for (final Entity e : this.entities) {
            this.removeEntity(e);
        }
    }

    public void addEntity(final Entity e) {
        int index = this.entities.indexOf(null);
        if (index != -1) {
            this.entities.set(index, e);
        } else {
            this.entities.add(e);
        }
        if (e instanceof WeaponOut) {
            final WeaponOut weaponOut = (WeaponOut) e;
            index = this.weaponOuts.indexOf(null);
            if (index != -1) {
                this.weaponOuts.set(this.weaponOuts.indexOf(null), weaponOut);
            } else {
                this.weaponOuts.add(weaponOut);
            }
        } else if (e instanceof Weapon) {
            final Weapon weapon = (Weapon) e;
            index = this.weapons.indexOf(null);
            if (index != -1) {
                this.weapons.set(this.weapons.indexOf(null), weapon);
            } else {
                this.weapons.add(weapon);
            }
        }
    }

    public void addAllEntities(final ArrayList<Entity> entityArrayList) {
        for (final Entity e : entityArrayList) {
            this.addEntity(e);
        }
    }

    public void addAllEntities(final Entity... entitiesArray) {
        this.addAllEntities(new ArrayList<Entity>(Arrays.asList(entitiesArray)));
    }

    public void addAllEntities(final Entity[]... entitiesArray) {
        for (final Entity[] eArray : entitiesArray) {
            this.addAllEntities(eArray);
        }
    }

    public ArrayList<Entity> getEntities() {
        return this.entities;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(final Player player) {
        this.player = player;
        this.playerHand = player.getHand();
    }

}
