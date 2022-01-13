// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.engine;

import java.awt.Graphics;

import com.tuana9a.entities.*;
import com.tuana9a.configs.ConfigStaticObject;
import com.tuana9a.configs.ConfigWeapon;
import com.tuana9a.entities.weapon.Spear;
import com.tuana9a.entities.weapon.Sword;
import com.tuana9a.entities.weapon.Shoot;
import com.tuana9a.entities.enemy.NormalEnemy;
import com.tuana9a.entities.enemy.HardEnemy;
import com.tuana9a.entities.enemy.Boss;
import com.tuana9a.configs.ConfigEnemy;
import com.tuana9a.entities.enemy.Enemy;
import com.tuana9a.app.App;
import com.tuana9a.entities.weapon.Weapon;
import com.tuana9a.screen.LoadScreen;
import com.tuana9a.utils.Loading;
import com.tuana9a.entities.player.Player;
import com.tuana9a.screen.GameScreen;

public class GameWorld {
    private static final GameWorld instance= new GameWorld();

    private final GameMap currentGameMap;
    private Player player;

    private GameWorld() {
        this.currentGameMap = new GameMap();
    }

    public static GameWorld getInstance() {
        return instance;
    }

    public void resetEntityManagerAll() {
        EntityManager entityManager = EntityManager.getInstance();
        entityManager.nullEveryThing();
    }

    public void resetEntityManagerExceptPlayer() {
        EntityManager entityManager = EntityManager.getInstance();
        entityManager.nullEveryThingExceptPlayer();
    }

    public void resetPlayer() {
        this.player = null;
    }

    public void newGame() {
        this.newGame(null, null);
    }

    public void replay() {
        EntityManager entityManager = EntityManager.getInstance();
        GameScreen gameScreen = GameScreen.getInstance();
        this.player.dropAllWeapon();
        this.resetEntityManagerAll();
        this.player.updatePosition(this.currentGameMap.getPixelSpawnX(), this.currentGameMap.getPixelSpawnY());
        this.player.reborn();
        gameScreen.updateUiPayerHp(this.player.getHealth());
        entityManager.addEntity(this.player);
        entityManager.setPlayer(this.player);
        this.initEntitiesWithMap();
        entityManager.updateAll();
    }

    public void newGame(final Player newPlayer, final String mapId) {
        this.resetPlayer();
        this.resetEntityManagerAll();
        this.currentGameMap.loading = new Loading(10, 0, 100L);
        final App app = App.getInstance();
        LoadScreen loadScreen = LoadScreen.getInstance();
        app.switchToState(loadScreen);
        loadScreen.initLoadState(this.currentGameMap.loading);
        GameScreen gameScreen = GameScreen.getInstance();
        new Thread(new Runnable() {
            @Override
            public void run() {
                EntityManager entityManager = EntityManager.getInstance();
                if (mapId == null || mapId.equals("")) {
                    GameWorld.this.initMapById("1");
                } else {
                    GameWorld.this.initMapById(mapId);
                }
                if (newPlayer == null) {
                    final int playerId = GameWorld.this.currentGameMap.getPlayerId();
                    if (playerId == -1) {
                        GameWorld.this.player = new Player(gameScreen, 0, GameWorld.this.currentGameMap.getPixelSpawnX(), GameWorld.this.currentGameMap.getPixelSpawnY());
                    } else {
                        GameWorld.this.player = new Player(gameScreen, playerId, GameWorld.this.currentGameMap.getPixelSpawnX(), GameWorld.this.currentGameMap.getPixelSpawnY());
                    }
                } else {
                    GameWorld.this.player = newPlayer;
                    GameWorld.this.player.updatePosition(GameWorld.this.currentGameMap.getPixelSpawnX(), GameWorld.this.currentGameMap.getPixelSpawnY());
                    for (final Weapon w : GameWorld.this.player.getHand().getAllWeapons()) {
                        if (w != null) {
                            entityManager.addEntity(w);
                        }
                    }
                }
                entityManager.setPlayer(GameWorld.this.player);
                entityManager.addEntity(GameWorld.this.player);
                entityManager.updateAll();
            }
        }).start();
    }

    public void teleportToNewMap(final String mapId) {
        this.resetEntityManagerAll();
        this.player.clearIntersects();
        this.currentGameMap.loading = new Loading(10, 0, 100L);
        final App app = App.getInstance();
        LoadScreen loadScreen = LoadScreen.getInstance();
        app.switchToState(loadScreen);
        loadScreen.initLoadState(this.currentGameMap.loading);
        new Thread(new Runnable() {
            @Override
            public void run() {
                EntityManager entityManager = EntityManager.getInstance();
                if (mapId == null || mapId.equals("")) {
                    GameWorld.this.initMapById("1");
                } else {
                    GameWorld.this.initMapById(mapId);
                }
                GameWorld.this.player.updatePosition(GameWorld.this.currentGameMap.getPixelSpawnX(), GameWorld.this.currentGameMap.getPixelSpawnY());
                entityManager.setPlayer(GameWorld.this.player);
                entityManager.addEntity(GameWorld.this.player);
                for (final Weapon w : GameWorld.this.player.getHand().getAllWeapons()) {
                    if (w != null) {
                        entityManager.addEntity(w);
                    }
                }
                entityManager.updateAll();
            }
        }).start();
    }

    public void initMapById(final String mapId) {
        this.currentGameMap.loadMapById(mapId);
        this.initEntitiesWithMap();
    }

    public void initEntitiesWithMap() {
        GameScreen gameScreen = GameScreen.getInstance();
        gameScreen.notBossStage();
        EntityManager entityManager = EntityManager.getInstance();
        final int enemyNumber = this.currentGameMap.getEnemyNumber();
        final int[][] enemiesInfo = this.currentGameMap.getEnemiesInfo();
        final Enemy[] enemies = new Enemy[enemyNumber];
        final Weapon[] enemyWeapons = new Weapon[enemyNumber];
        for (int i = 0; i < enemyNumber; ++i) {
            final int[] info = enemiesInfo[i];
            if (Enemy.isBoss(info[0])) {
                gameScreen.isBossStage(ConfigEnemy.healths[info[0]]);
                enemies[i] = new Boss(info[0], (info[1] + 0.5) * 64.0 - ConfigEnemy.widths[info[0]] / 2, (info[2] + 1) * 64 - ConfigEnemy.heights[info[0]]);
            } else if (Enemy.isHard(info[0])) {
                enemies[i] = new HardEnemy(info[0], (info[1] + 0.5) * 64.0 - ConfigEnemy.widths[info[0]] / 2, (info[2] + 1) * 64 - ConfigEnemy.heights[info[0]]);
            } else {
                enemies[i] = new NormalEnemy(info[0], (info[1] + 0.5) * 64.0 - ConfigEnemy.widths[info[0]] / 2, (info[2] + 1) * 64 - ConfigEnemy.heights[info[0]]);
            }
        }
        for (int i = 0; i < enemyNumber; ++i) {
            final int randomWeaponId = i % 5;
            if (Weapon.isShootWeapon(randomWeaponId)) {
                enemyWeapons[i] = new Shoot(randomWeaponId, enemies[i]);
            } else if (Weapon.isSword(randomWeaponId)) {
                enemyWeapons[i] = new Sword(randomWeaponId, enemies[i]);
            } else if (Weapon.isSpear(randomWeaponId)) {
                enemyWeapons[i] = new Spear(randomWeaponId, enemies[i]);
            }
        }
        final int weaponNumber = this.currentGameMap.getWeaponNumber();
        final Weapon[] aloneWeapons = new Weapon[weaponNumber];
        final int[][] aloneWeaponsInfo = this.currentGameMap.getWeaponsInfo();
        for (int j = 0; j < weaponNumber; ++j) {
            final int[] info2 = aloneWeaponsInfo[j];
            if (Weapon.isShootWeapon(info2[0])) {
                aloneWeapons[j] = new Shoot(info2[0], (info2[1] + 0.5) * 64.0 - ConfigWeapon.widths[info2[0]] / 2, (info2[2] + 1) * 64 - ConfigWeapon.heights[info2[0]]);
            } else if (Weapon.isSword(info2[0])) {
                aloneWeapons[j] = new Sword(info2[0], (info2[1] + 0.5) * 64.0 - ConfigWeapon.widths[info2[0]] / 2, (info2[2] + 1) * 64 - ConfigWeapon.heights[info2[0]]);
            } else if (Weapon.isSpear(info2[0])) {
                aloneWeapons[j] = new Spear(info2[0], (info2[1] + 0.5) * 64.0 - ConfigWeapon.widths[info2[0]] / 2, (info2[2] + 1) * 64 - ConfigWeapon.heights[info2[0]]);
            }
        }
        final StaticObject[] statics = new StaticObject[this.currentGameMap.getStaticObjectNumber() + 1];
        final int[][] staticsInfo = this.currentGameMap.getStaticObjectsInfo();
        for (int k = 0; k < staticsInfo.length; ++k) {
            final int[] info3 = staticsInfo[k];
            if (Ground.isTeleAbove(info3[0])) {
                statics[k] = new TeleportGate(info3[0], (info3[1] + 0.5) * 64.0 - ConfigStaticObject.widths[info3[0]] / 2, (info3[2] + 1) * 64 - ConfigStaticObject.heights[info3[0]], this.currentGameMap.nextMapId);
            } else {
                statics[k] = new StaticObject(info3[0], (info3[1] + 0.5) * 64.0 - ConfigStaticObject.widths[info3[0]] / 2, (info3[2] + 1) * 64 - ConfigStaticObject.heights[info3[0]]);
            }
        }
        entityManager.addAllEntities(new Entity[][]{enemies, enemyWeapons, aloneWeapons, statics});
    }

    public void update() {
        EntityManager entityManager = EntityManager.getInstance();
        entityManager.updateAll();
    }

    public void updateEveryRelCamAll() {
        EntityManager entityManager = EntityManager.getInstance();
        entityManager.updateAllEveryRelCam();
    }

    public void render(final Graphics g) {
        EntityManager entityManager = EntityManager.getInstance();
        this.currentGameMap.render(g);
        entityManager.renderAll(g);
    }

    public GameMap getCurrentMap() {
        return this.currentGameMap;
    }

}
