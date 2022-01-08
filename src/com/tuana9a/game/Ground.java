// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.game;

import com.tuana9a.graphic.Assets;

import java.awt.Graphics;
import java.awt.Rectangle;
import com.tuana9a.configs.ConfigGround;

import java.awt.image.BufferedImage;

public class Ground
{
    public static final int NUMBER = 11;
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    public static Ground nullGround;
    public static Ground[] storage;
    public int id;
    private final BufferedImage texture;
    private final boolean walkable;
    
    public Ground(final int id, final BufferedImage texture) {
        this.id = id;
        this.texture = texture;
        if (id == -1) {
            this.walkable = false;
        }
        else {
            this.walkable = ConfigGround.walkAbles[id];
        }
    }
    
    public static Rectangle getRect(final int row, final int column) {
        return new Rectangle(column * 64, row * 64, 64, 64);
    }
    
    public void render(final Graphics g, final int x, final int y) {
        g.drawImage(this.texture, x, y, 64, 64, null);
    }
    
    public boolean isWalkable() {
        return this.walkable;
    }
    
    public static boolean isObjectAbove(final int id) {
        for (final int i : ConfigGround.objectsAbove) {
            if (id == i) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isTeleAbove(final int id) {
        for (final int i : ConfigGround.telesAbove) {
            if (id == i) {
                return true;
            }
        }
        return false;
    }
    
    static {
        Ground.nullGround = new Ground(-1, Assets.nullGround);
        Ground.storage = new Ground[] { new Ground(0, Assets.groundLand1), new Ground(1, Assets.groundTree1), new Ground(2, Assets.groundTree2), new Ground(3, Assets.groundBush1), new Ground(4, Assets.groundBush2), new Ground(5, Assets.groundFlower1), new Ground(6, Assets.groundFlower2), new Ground(7, Assets.groundStone1), new Ground(8, Assets.groundStone2), new Ground(9, Assets.groundWater1), new Ground(10, Assets.groundTele1) };
    }
}
