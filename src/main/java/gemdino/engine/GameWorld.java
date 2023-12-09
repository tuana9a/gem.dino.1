package gemdino.engine;

import java.awt.Graphics;

import gemdino.configs.ConfigStaticObject;
import gemdino.configs.ConfigWeapon;
import gemdino.weapon.Spear;
import gemdino.weapon.Sword;
import gemdino.weapon.Shoot;
import gemdino.enemy.NormalEnemy;
import gemdino.enemy.HardEnemy;
import gemdino.enemy.SpawnChildBoss;
import gemdino.configs.ConfigEnemy;
import gemdino.enemy.Enemy;
import gemdino.app.App;
import gemdino.weapon.Weapon;
import gemdino.entity.Entity;
import gemdino.entity.Ground;
import gemdino.entity.StaticObject;
import gemdino.entity.TeleportGate;
import gemdino.screen.LoadingScreen;
import gemdino.utils.Loading;
import gemdino.player.Player;
import gemdino.screen.GameScreen;
import lombok.Getter;

public class GameWorld {
    private final App app;
    private Player player;
    @Getter
    private final EntityManager entityManager;

    public GameWorld(App app) {
        this.app = app;
        this.entityManager = new EntityManager(app);
    }

    public void resetEntityManagerAll() {
        entityManager.nullEveryThing();
    }

    public void resetEntityManagerExceptPlayer() {
        entityManager.nullEveryThingExceptPlayer();
    }

    public void resetPlayer() {
        this.player = null;
    }

    public void newGame() {
        this.newGame(null, null);
    }

    public void replay() {
        GameScreen gameScreen = app.getGameScreen();
        GameMap gameMap = app.getGameMap();
        this.player.dropAllWeapon();
        this.resetEntityManagerAll();
        this.player.updatePosition(gameMap.getPixelSpawnX(), gameMap.getPixelSpawnY());
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
        final GameMap gameMap = app.getGameMap();
        gameMap.loading = new Loading(10, 0, 100L);
        LoadingScreen loadingScreen = app.getLoadingScreen();
        app.switchScreen(loadingScreen);
        loadingScreen.create(gameMap.loading);
        GameScreen gameScreen = app.getGameScreen();
        loadingScreen.getExecutorService().submit(new Runnable() {
            @Override
            public void run() {
                if (mapId == null || mapId.equals("")) {
                    GameWorld.this.initMapById("1");
                } else {
                    GameWorld.this.initMapById(mapId);
                }
                if (newPlayer == null) {
                    final int playerId = gameMap.getPlayerId();
                    if (playerId == -1) {
                        GameWorld.this.player = new Player(app, 0, gameMap.getPixelSpawnX(), gameMap.getPixelSpawnY());
                    } else {
                        GameWorld.this.player = new Player(app, playerId, gameMap.getPixelSpawnX(), gameMap.getPixelSpawnY());
                    }
                } else {
                    GameWorld.this.player = newPlayer;
                    GameWorld.this.player.updatePosition(gameMap.getPixelSpawnX(), gameMap.getPixelSpawnY());
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
        });
    }

    public void teleportToNewMap(final String mapId) {
        this.resetEntityManagerAll();
        this.player.clearIntersects();
        final GameMap gameMap = app.getGameMap();
        gameMap.loading = new Loading(10, 0, 100L);
        LoadingScreen loadingScreen = app.getLoadingScreen();
        app.switchScreen(loadingScreen);
        loadingScreen.create(gameMap.loading);
        loadingScreen.getExecutorService().submit(new Runnable() {
            @Override
            public void run() {
                if (mapId == null || mapId.equals("")) {
                    GameWorld.this.initMapById("1");
                } else {
                    GameWorld.this.initMapById(mapId);
                }
                GameWorld.this.player.updatePosition(gameMap.getPixelSpawnX(), gameMap.getPixelSpawnY());
                entityManager.setPlayer(GameWorld.this.player);
                entityManager.addEntity(GameWorld.this.player);
                for (final Weapon w : GameWorld.this.player.getHand().getAllWeapons()) {
                    if (w != null) {
                        entityManager.addEntity(w);
                    }
                }
                entityManager.updateAll();
            }
        });
    }

    public void initMapById(final String mapId) {
        GameMap gameMap = app.getGameMap();
        gameMap.loadMapById(mapId);
        this.initEntitiesWithMap();
    }

    public void initEntitiesWithMap() {
        GameScreen gameScreen = app.getGameScreen();
        gameScreen.notBossStage();
        GameMap gameMap = app.getGameMap();
        final int enemyNumber = gameMap.getEnemyNumber();
        final int[][] enemiesInfo = gameMap.getEnemiesInfo();
        final Enemy[] enemies = new Enemy[enemyNumber];
        final Weapon[] enemyWeapons = new Weapon[enemyNumber];
        for (int i = 0; i < enemyNumber; ++i) {
            final int[] info = enemiesInfo[i];
            if (Enemy.isBoss(info[0])) {
                gameScreen.isBossStage(ConfigEnemy.healths[info[0]]);
                enemies[i] = new SpawnChildBoss(app, info[0], (info[1] + 0.5) * 64.0 - ConfigEnemy.widths[info[0]] / 2, (info[2] + 1) * 64 - ConfigEnemy.heights[info[0]]);
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
        final int weaponNumber = gameMap.getWeaponNumber();
        final Weapon[] aloneWeapons = new Weapon[weaponNumber];
        final int[][] aloneWeaponsInfo = gameMap.getWeaponsInfo();
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
        final StaticObject[] statics = new StaticObject[gameMap.getStaticObjectNumber() + 1];
        final int[][] staticsInfo = gameMap.getStaticObjectsInfo();
        for (int k = 0; k < staticsInfo.length; ++k) {
            final int[] info3 = staticsInfo[k];
            if (Ground.isTeleAbove(info3[0])) {
                statics[k] = new TeleportGate(info3[0], (info3[1] + 0.5) * 64.0 - ConfigStaticObject.widths[info3[0]] / 2, (info3[2] + 1) * 64 - ConfigStaticObject.heights[info3[0]], gameMap.nextMapId);
            } else {
                statics[k] = new StaticObject(info3[0], (info3[1] + 0.5) * 64.0 - ConfigStaticObject.widths[info3[0]] / 2, (info3[2] + 1) * 64 - ConfigStaticObject.heights[info3[0]]);
            }
        }
        entityManager.addAllEntities(new Entity[][]{enemies, enemyWeapons, aloneWeapons, statics});
    }

    public void update() {
        entityManager.updateAll();
    }

    public void updateEveryRelCamAll() {
        entityManager.updateAllEveryRelCam();
    }

    public void render(final Graphics g) {
        GameMap gameMap = app.getGameMap();
        gameMap.render(g);
        entityManager.renderAll(g);
    }

}
