// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.game;

import java.awt.Graphics;
import com.tuana9a.game.entity.stay.TeleGate;
import com.tuana9a.config.ConfigStaticObject;
import com.tuana9a.game.entity.stay.StaticObject;
import com.tuana9a.config.ConfigWeapon;
import com.tuana9a.game.entity.weapon.Spear;
import com.tuana9a.game.entity.weapon.Sword;
import com.tuana9a.game.entity.weapon.Shoot;
import com.tuana9a.game.entity.move.enemy.NormalEnemy;
import com.tuana9a.game.entity.move.enemy.HardEnemy;
import com.tuana9a.game.entity.move.enemy.Boss;
import com.tuana9a.config.ConfigEnemy;
import com.tuana9a.game.entity.move.enemy.Enemy;
import com.tuana9a.App;
import com.tuana9a.game.entity.weapon.Weapon;
import com.tuana9a.utility.Loading;
import com.tuana9a.game.entity.Entity;
import com.tuana9a.game.entity.move.player.Player;
import com.tuana9a.state.GameState;

public class Stage
{
    private GameState gameState;
    private final Map currentMap;
    private EntityManager entityManager;
    private Player player;
    
    public Stage(final GameState gameState) {
        this.gameState = gameState;
        this.entityManager = new EntityManager(gameState);
        this.currentMap = new Map(gameState);
    }
    
    public void resetEntityManagerAll() {
        this.entityManager.nullEveryThing();
    }
    
    public void resetEntityManagerExceptPlayer() {
        this.entityManager.nullEveryThingExceptPlayer();
    }
    
    public void resetPlayer() {
        this.player = null;
    }
    
    public void newGame() {
        this.newGame(null, null);
    }
    
    public void replay() {
        this.player.dropAllWeapon();
        this.resetEntityManagerAll();
        this.player.updatePosition(this.currentMap.getPixelSpawnX(), this.currentMap.getPixelSpawnY());
        this.player.reborn();
        this.gameState.updateUiPayerHp(this.player.getHealth());
        this.entityManager.addEntity(this.player);
        this.entityManager.setPlayer(this.player);
        this.initEntitiesWithMap();
        this.entityManager.updateAll();
    }
    
    public void newGame(final Player newPlayer, final String mapId) {
        this.resetPlayer();
        this.resetEntityManagerAll();
        this.currentMap.loading = new Loading(10, 0, 100L);
        final App app = this.gameState.getApp();
        app.switchToState(app.loadState);
        app.loadState.initLoadState(this.currentMap.loading);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mapId == null || mapId.equals("")) {
                    Stage.this.initMapById("1");
                }
                else {
                    Stage.this.initMapById(mapId);
                }
                if (newPlayer == null) {
                    final int playerId = Stage.this.currentMap.getPlayerId();
                    if (playerId == -1) {
                        Stage.this.player = new Player(Stage.this.gameState, 0, Stage.this.currentMap.getPixelSpawnX(), Stage.this.currentMap.getPixelSpawnY());
                    }
                    else {
                        Stage.this.player = new Player(Stage.this.gameState, playerId, Stage.this.currentMap.getPixelSpawnX(), Stage.this.currentMap.getPixelSpawnY());
                    }
                }
                else {
                    Stage.this.player = newPlayer;
                    Stage.this.player.updatePosition(Stage.this.currentMap.getPixelSpawnX(), Stage.this.currentMap.getPixelSpawnY());
                    for (final Weapon w : Stage.this.player.getHand().getAllWeapons()) {
                        if (w != null) {
                            Stage.this.entityManager.addEntity(w);
                        }
                    }
                }
                Stage.this.entityManager.setPlayer(Stage.this.player);
                Stage.this.entityManager.addEntity(Stage.this.player);
                Stage.this.entityManager.updateAll();
            }
        }).start();
    }
    
    public void teleToNewMap(final String mapId) {
        this.resetEntityManagerAll();
        this.player.clearIntersects();
        this.currentMap.loading = new Loading(10, 0, 100L);
        final App app = this.gameState.getApp();
        app.switchToState(app.loadState);
        app.loadState.initLoadState(this.currentMap.loading);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mapId == null || mapId.equals("")) {
                    Stage.this.initMapById("1");
                }
                else {
                    Stage.this.initMapById(mapId);
                }
                Stage.this.player.updatePosition(Stage.this.currentMap.getPixelSpawnX(), Stage.this.currentMap.getPixelSpawnY());
                Stage.this.entityManager.setPlayer(Stage.this.player);
                Stage.this.entityManager.addEntity(Stage.this.player);
                for (final Weapon w : Stage.this.player.getHand().getAllWeapons()) {
                    if (w != null) {
                        Stage.this.entityManager.addEntity(w);
                    }
                }
                Stage.this.entityManager.updateAll();
            }
        }).start();
    }
    
    public void initMapById(final String mapId) {
        this.currentMap.loadMapById(mapId);
        this.initEntitiesWithMap();
    }
    
    public void initEntitiesWithMap() {
        this.gameState.notBossStage();
        final int enemyNumber = this.currentMap.getEnemyNumber();
        final int[][] enemiesInfo = this.currentMap.getEnemiesInfo();
        final Enemy[] enemies = new Enemy[enemyNumber];
        final Weapon[] enemyWeapons = new Weapon[enemyNumber];
        for (int i = 0; i < enemyNumber; ++i) {
            final int[] info = enemiesInfo[i];
            if (Enemy.isBoss(info[0])) {
                this.gameState.isBossStage(ConfigEnemy.healths[info[0]]);
                enemies[i] = new Boss(this.gameState, info[0], (info[1] + 0.5) * 64.0 - ConfigEnemy.widths[info[0]] / 2, (info[2] + 1) * 64 - ConfigEnemy.heights[info[0]]);
            }
            else if (Enemy.isHard(info[0])) {
                enemies[i] = new HardEnemy(this.gameState, info[0], (info[1] + 0.5) * 64.0 - ConfigEnemy.widths[info[0]] / 2, (info[2] + 1) * 64 - ConfigEnemy.heights[info[0]]);
            }
            else {
                enemies[i] = new NormalEnemy(this.gameState, info[0], (info[1] + 0.5) * 64.0 - ConfigEnemy.widths[info[0]] / 2, (info[2] + 1) * 64 - ConfigEnemy.heights[info[0]]);
            }
        }
        for (int i = 0; i < enemyNumber; ++i) {
            final int randomWeaponId = i % 5;
            if (Weapon.isShootWeapon(randomWeaponId)) {
                enemyWeapons[i] = new Shoot(this.gameState, randomWeaponId, enemies[i]);
            }
            else if (Weapon.isSword(randomWeaponId)) {
                enemyWeapons[i] = new Sword(this.gameState, randomWeaponId, enemies[i]);
            }
            else if (Weapon.isSpear(randomWeaponId)) {
                enemyWeapons[i] = new Spear(this.gameState, randomWeaponId, enemies[i]);
            }
        }
        final int weaponNumber = this.currentMap.getWeaponNumber();
        final Weapon[] aloneWeapons = new Weapon[weaponNumber];
        final int[][] aloneWeaponsInfo = this.currentMap.getWeaponsInfo();
        for (int j = 0; j < weaponNumber; ++j) {
            final int[] info2 = aloneWeaponsInfo[j];
            if (Weapon.isShootWeapon(info2[0])) {
                aloneWeapons[j] = new Shoot(this.gameState, info2[0], (info2[1] + 0.5) * 64.0 - ConfigWeapon.widths[info2[0]] / 2, (info2[2] + 1) * 64 - ConfigWeapon.heights[info2[0]]);
            }
            else if (Weapon.isSword(info2[0])) {
                aloneWeapons[j] = new Sword(this.gameState, info2[0], (info2[1] + 0.5) * 64.0 - ConfigWeapon.widths[info2[0]] / 2, (info2[2] + 1) * 64 - ConfigWeapon.heights[info2[0]]);
            }
            else if (Weapon.isSpear(info2[0])) {
                aloneWeapons[j] = new Spear(this.gameState, info2[0], (info2[1] + 0.5) * 64.0 - ConfigWeapon.widths[info2[0]] / 2, (info2[2] + 1) * 64 - ConfigWeapon.heights[info2[0]]);
            }
        }
        final StaticObject[] statics = new StaticObject[this.currentMap.getStaticObjectNumber() + 1];
        final int[][] staticsInfo = this.currentMap.getStaticObjectsInfo();
        for (int k = 0; k < staticsInfo.length; ++k) {
            final int[] info3 = staticsInfo[k];
            if (Ground.isTeleAbove(info3[0])) {
                statics[k] = new TeleGate(this.gameState, info3[0], (info3[1] + 0.5) * 64.0 - ConfigStaticObject.widths[info3[0]] / 2, (info3[2] + 1) * 64 - ConfigStaticObject.heights[info3[0]], this.currentMap.nextMapId);
            }
            else {
                statics[k] = new StaticObject(this.gameState, info3[0], (info3[1] + 0.5) * 64.0 - ConfigStaticObject.widths[info3[0]] / 2, (info3[2] + 1) * 64 - ConfigStaticObject.heights[info3[0]]);
            }
        }
        this.entityManager.addAllEntities(new Entity[][] { enemies, enemyWeapons, aloneWeapons, statics });
    }
    
    public void update() {
        this.entityManager.updateAll();
    }
    
    public void updateEveryRelCamAll() {
        this.entityManager.updateAllEveryRelCam();
    }
    
    public void render(final Graphics g) {
        this.currentMap.render(g);
        this.entityManager.renderAll(g);
    }
    
    public Map getCurrentMap() {
        return this.currentMap;
    }
    
    public EntityManager getEntityManager() {
        return this.entityManager;
    }
    
    public Player getPlayer() {
        return this.player;
    }
}
