// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.environment;

import com.tuana9a.entities.Animal;

import java.awt.Graphics;

import com.tuana9a.utils.Utils;
import com.tuana9a.utils.Loading;
import com.tuana9a.screen.GameScreen;

public class Map {
    private final GameScreen gameScreen;
    private int width;
    private int height;
    public String mapId;
    public String nextMapId;
    private int playerId;
    private int playerSpawnX;
    private int playerSpawnY;
    private int[][] mapArray;
    private int staticObjectNumber;
    private int[][] staticObjectsInfo;
    private int enemyNumber;
    private int[][] enemiesInfo;
    private int weaponNumber;
    private int[][] weaponsInfo;
    public Loading loading;
    public static final int FULL_PROCESS = 10;
    public static final long TIME_SIMULATOR = 100L;

    public Map(final GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void loadMapById(String mapId) {
        final String filePath = "resources/map/map" + mapId + ".txt";
        String dataString = Utils.loadTextFile(filePath);
        if (dataString == null || dataString.equals("")) {
            this.loading.onError("Fail to load " + filePath);
            return;
        }
        this.loading.update();
        dataString = dataString.replaceAll("//.*\r\n", "");
        this.loading.update();
        final String[] data = dataString.split("\\s+");
        this.loading.update();
        try {
            int indexGlobal = 0;
            this.width = Integer.parseInt(data[indexGlobal++]);
            this.height = Integer.parseInt(data[indexGlobal++]);
            this.loading.update();
            mapId = data[indexGlobal++];
            this.nextMapId = data[indexGlobal++];
            this.loading.update();
            final String temp = data[indexGlobal++];
            this.playerId = (temp.equalsIgnoreCase("X") ? -1 : Integer.parseInt(temp));
            this.playerSpawnX = Integer.parseInt(data[indexGlobal++]);
            this.playerSpawnY = Integer.parseInt(data[indexGlobal++]);
            this.loading.update();
            this.enemyNumber = Integer.parseInt(data[indexGlobal++]);
            this.weaponNumber = Integer.parseInt(data[indexGlobal++]);
            this.loading.update();
            this.enemiesInfo = new int[this.enemyNumber][3];
            for (int i = 0, enemyIndex = 0; i < this.enemyNumber; ++i, ++enemyIndex) {
                this.enemiesInfo[enemyIndex][0] = Integer.parseInt(data[indexGlobal++]);
                this.enemiesInfo[enemyIndex][1] = Integer.parseInt(data[indexGlobal++]);
                this.enemiesInfo[enemyIndex][2] = Integer.parseInt(data[indexGlobal++]);
            }
            this.loading.update();
            this.weaponsInfo = new int[this.weaponNumber][3];
            for (int i = 0, weaponIndex = 0; i < this.weaponNumber; ++i, ++weaponIndex) {
                this.weaponsInfo[weaponIndex][0] = Integer.parseInt(data[indexGlobal++]);
                this.weaponsInfo[weaponIndex][1] = Integer.parseInt(data[indexGlobal++]);
                this.weaponsInfo[weaponIndex][2] = Integer.parseInt(data[indexGlobal++]);
            }
            this.loading.update();
            this.mapArray = new int[this.height][this.width];
            for (int i = 0; i < this.height; ++i) {
                for (int j = 0; j < this.width; ++j) {
                    final int groundId = Integer.parseInt(data[indexGlobal++]);
                    this.mapArray[i][j] = groundId;
                    if (Ground.isObjectAbove(groundId)) {
                        ++this.staticObjectNumber;
                    }
                }
            }
            this.loading.update();
            this.staticObjectsInfo = new int[this.staticObjectNumber][3];
            int tempIndex = 0;
            for (int k = 0; k < this.height; ++k) {
                for (int l = 0; l < this.width; ++l) {
                    if (Ground.isObjectAbove(this.mapArray[k][l])) {
                        this.staticObjectsInfo[tempIndex][0] = this.mapArray[k][l];
                        this.staticObjectsInfo[tempIndex][1] = l;
                        this.staticObjectsInfo[tempIndex][2] = k;
                        ++tempIndex;
                    }
                }
            }
            this.loading.update();
        } catch (Exception e) {
            e.printStackTrace();
            this.loading.onError("Fail to load " + filePath);
        }
    }

    public void loadMapSimulator() {
    }

    public void update() {
    }

    public void render(final Graphics g) {
        final Camera camera = Camera.getInstance();
        final int startColumn = (int) Math.max(0.0, camera.getxOffset() / 64.0);
        final int endColumn = (int) Math.min(this.width, (camera.getxOffset() + this.gameScreen.getDisplayWidth()) / 64.0 + 1.0);
        final int startRow = (int) Math.max(0.0, camera.getyOffset() / 64.0);
        for (int endRow = (int) Math.min(this.height, (camera.getyOffset() + this.gameScreen.getDisplayHeight()) / 64.0 + 1.0), row = startRow; row < endRow; ++row) {
            for (int column = startColumn; column < endColumn; ++column) {
                final int cameraRenderX = (int) (column * 64 - camera.getxOffset());
                final int cameraRenderY = (int) (row * 64 - camera.getyOffset());
                this.getGround(row, column).render(g, cameraRenderX, cameraRenderY);
            }
        }
    }

    public Ground getGround(final int row, final int column) {
        try {
            final Ground ground = Ground.storage[this.mapArray[row][column]];
            return (ground == null) ? Ground.nullGround : ground;
        } catch (Exception e) {
            return Ground.nullGround;
        }
    }

    public boolean cantWalk(final int row, final int column) {
        return !this.getGround(row, column).isWalkable();
    }

    public boolean canWalkX(final Animal a, final int rowTop, final int rowBottom, final int columnLeft, final int columnRight) {
        for (int row = rowTop; row <= rowBottom; ++row) {
            for (int col = columnLeft; col <= columnRight; ++col) {
                if (this.cantWalk(row, col) && a.actualSize(a.xMove, 0.0).intersects(Ground.getRect(row, col))) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canWalkY(final Animal a, final int rowTop, final int rowBottom, final int columnLeft, final int columnRight) {
        for (int row = rowTop; row <= rowBottom; ++row) {
            for (int col = columnLeft; col <= columnRight; ++col) {
                if (this.cantWalk(row, col) && a.actualSize(0.0, a.yMove).intersects(Ground.getRect(row, col))) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canWalkX(final Animal a) {
        final double leftX = a.x + a.actualSize.x;
        final double rightX = leftX + a.actualSize.width;
        final double topY = a.y + a.actualSize.y;
        final double botY = topY + a.actualSize.height;
        final int colLeft = (int) Math.floor((leftX + a.xMove) / 64.0);
        final int colRight = (int) Math.floor((rightX + a.xMove) / 64.0);
        final int rowTop = (int) Math.floor((topY + a.yMove) / 64.0);
        final int rowBot = (int) Math.floor((botY + a.yMove) / 64.0);
        for (int col = colLeft; col < colRight; ++col) {
            for (int row = rowTop; row < rowBot; ++row) {
                if (this.cantWalk(row, col) && a.actualSize(a.xMove, 0.0).intersects(Ground.getRect(row, col))) {
                    final double minSpace = 1.0;
                    if (a.xMove > 0.0) {
                        a.x = colRight * 64 - a.actualSize.x - a.actualSize.width - minSpace;
                    } else {
                        a.x = (colLeft + 1) * 64 - a.actualSize.x + minSpace;
                    }
                    a.xMove = 0.0;
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canWalkY(final Animal a) {
        final double leftX = a.x + a.actualSize.x;
        final double rightX = leftX + a.actualSize.width;
        final double topY = a.y + a.actualSize.y;
        final double botY = topY + a.actualSize.height;
        final int colLeft = (int) ((leftX + a.xMove) / 64.0);
        final int colRight = (int) ((rightX + a.xMove) / 64.0);
        final int rowTop = (int) Math.floor((topY + a.yMove) / 64.0);
        for (int rowBot = (int) Math.floor((botY + a.yMove) / 64.0), row = rowTop; row < rowBot; ++row) {
            for (int col = colLeft; col < colRight; ++col) {
                if (this.cantWalk(row, col) && a.actualSize(0.0, a.yMove).intersects(Ground.getRect(row, col))) {
                    final double minSpace = 1.0;
                    if (a.yMove > 0.0) {
                        a.y = rowBot * 64 - a.actualSize.y - a.actualSize.height - minSpace;
                    } else {
                        a.y = (rowTop + 1) * 64 - a.actualSize.y + minSpace;
                    }
                    a.yMove = 0.0;
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] getMapArray() {
        return this.mapArray;
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public int getStaticObjectNumber() {
        return this.staticObjectNumber;
    }

    public int[][] getStaticObjectsInfo() {
        return this.staticObjectsInfo;
    }

    public int getWeaponNumber() {
        return this.weaponNumber;
    }

    public int[][] getWeaponsInfo() {
        return this.weaponsInfo;
    }

    public int getEnemyNumber() {
        return this.enemyNumber;
    }

    public int[][] getEnemiesInfo() {
        return this.enemiesInfo;
    }

    public int getMapPixelWidth() {
        return this.width * 64;
    }

    public int getMapPixelHeight() {
        return this.height * 64;
    }

    public float getPixelSpawnX() {
        return (float) (this.playerSpawnX * 64);
    }

    public float getPixelSpawnY() {
        return (float) (this.playerSpawnY * 64);
    }
}
