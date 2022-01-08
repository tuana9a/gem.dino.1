// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.game;

import java.util.Arrays;

import com.tuana9a.App;
import com.tuana9a.entities.weapon.ShootOut;
import java.awt.Graphics;

import com.tuana9a.entities.enemy.Enemy;
import com.tuana9a.entities.MovingEntity;
import java.util.Comparator;
import com.tuana9a.entities.AnimalHand;
import com.tuana9a.entities.weapon.WeaponOut;
import com.tuana9a.entities.weapon.Weapon;
import com.tuana9a.entities.Entity;
import java.util.ArrayList;
import com.tuana9a.entities.player.Player;
import com.tuana9a.screen.GameScreen;

public class EntityManager
{
    private final GameScreen gameScreen;
    private Player player;
    private final ArrayList<Entity> allEntities;
    private final ArrayList<Weapon> allWeapons;
    private final ArrayList<WeaponOut> allWeaponOuts;
    private AnimalHand playerHand;
    private static Comparator<Entity> compareY;
    
    public EntityManager(final GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.allEntities = new ArrayList<>(300);
        this.allWeapons = new ArrayList<>(10);
        this.allWeaponOuts = new ArrayList<>(150);
    }
    
    public void updateAll() {
        App app = App.getInstance();
        this.player.update();
        if (this.player == null) {
            return;
        }
        if (!app.getKeyboardManager().freeCamMode) {
            this.gameScreen.getGameCamera().centerOnEntity(this.player);
        }
        for (int i = 0; i < this.allEntities.size(); ++i) {
            final Entity e = this.allEntities.get(i);
            if (e != null) {
                if (e instanceof MovingEntity) {
                    final MovingEntity eActive = (MovingEntity)e;
                    this.checkCollideWithOthers(eActive);
                }
                if (!(e instanceof Player)) {
                    if (!(e instanceof Weapon)) {
                        if (e instanceof Enemy) {
                            final Enemy enemy = (Enemy)e;
                            if (enemy.isDead() && enemy.deadTime.isTime()) {
                                this.removeEntity(e);
                            }
                        }
                        else if (e instanceof WeaponOut) {
                            final WeaponOut weaponOut = (WeaponOut)e;
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
        for (int i = 0; i < this.allWeapons.size(); ++i) {
            final Weapon w = this.allWeapons.get(i);
            if (w != null) {
                w.update();
            }
        }
        for (int i = 0; i < this.allWeaponOuts.size(); ++i) {
            final WeaponOut wo = this.allWeaponOuts.get(i);
            if (wo != null) {
                wo.update();
            }
        }
        this.allEntities.sort(EntityManager.compareY);
        this.allWeaponOuts.sort(EntityManager.compareY);
    }
    
    public void updateAllEveryRelCam() {
        for (final Entity e : this.allEntities) {
            if (e == null) {
                continue;
            }
            e.updatePositionCam();
            e.updateRotatePositionCam();
        }
    }
    
    public void renderAll(final Graphics g) {
        App app = App.getInstance();
        if (this.player == null) {
            return;
        }
        for (final WeaponOut wo : this.allWeaponOuts) {
            if (wo == null) {
                continue;
            }
            wo.render(g);
        }
        for (final Entity e : this.allEntities) {
            if (e != null) {
                if (e instanceof WeaponOut) {
                    continue;
                }
                e.render(g);
            }
        }
        if (app.getKeyboardManager().rotationMode && this.playerHand != null) {
            this.playerHand.renderSelectedWeapon(g);
        }
        if (app.getKeyboardManager().innerBoundMode) {
            for (final WeaponOut wo : this.allWeaponOuts) {
                if (wo == null) {
                    continue;
                }
                wo.render(g);
            }
        }
    }
    
    private void checkCollideWithOthers(final MovingEntity e) {
        if (e instanceof ShootOut && ((ShootOut)e).isHit()) {
            return;
        }
        for (int i = 0; i < this.allEntities.size(); ++i) {
            final Entity other = this.allEntities.get(i);
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
        return this.allEntities.contains(e);
    }
    
    public void removeEntity(final Entity e) {
        this.allEntities.set(this.allEntities.indexOf(e), null);
        if (e instanceof WeaponOut) {
            this.allWeaponOuts.set(this.allWeaponOuts.indexOf(e), null);
        }
        else if (e instanceof Weapon) {
            this.allWeapons.set(this.allWeapons.indexOf(e), null);
        }
    }
    
    public void eraseEveryThing() {
        this.allEntities.clear();
        this.allWeapons.clear();
        this.allWeaponOuts.clear();
        this.player = null;
        this.playerHand = null;
    }
    
    public void nullEveryThing() {
        for (final Entity e : this.allEntities) {
            this.removeEntity(e);
        }
        this.player = null;
        this.playerHand = null;
    }
    
    public void nullEveryThingExceptPlayer() {
        for (final Entity e : this.allEntities) {
            this.removeEntity(e);
        }
    }
    
    public void addEntity(final Entity e) {
        int index = this.allEntities.indexOf(null);
        if (index != -1) {
            this.allEntities.set(index, e);
        }
        else {
            this.allEntities.add(e);
        }
        if (e instanceof WeaponOut) {
            final WeaponOut weaponOut = (WeaponOut)e;
            index = this.allWeaponOuts.indexOf(null);
            if (index != -1) {
                this.allWeaponOuts.set(this.allWeaponOuts.indexOf(null), weaponOut);
            }
            else {
                this.allWeaponOuts.add(weaponOut);
            }
        }
        else if (e instanceof Weapon) {
            final Weapon weapon = (Weapon)e;
            index = this.allWeapons.indexOf(null);
            if (index != -1) {
                this.allWeapons.set(this.allWeapons.indexOf(null), weapon);
            }
            else {
                this.allWeapons.add(weapon);
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
    
    public ArrayList<Entity> getAllEntities() {
        return this.allEntities;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public void setPlayer(final Player player) {
        this.player = player;
        this.playerHand = player.getHand();
    }
    
    static {
        EntityManager.compareY = new Comparator<Entity>() {
            @Override
            public int compare(final Entity o1, final Entity o2) {
                if (o1 == null && o2 == null) {
                    return 0;
                }
                if (o1 == null) {
                    return -1;
                }
                if (o2 == null) {
                    return 1;
                }
                return Double.compare(o1.y + o1.actualSize.y + o1.actualSize.height, o2.y + o2.actualSize.y + o2.actualSize.height);
            }
        };
    }
}
