package com.tuana9a.OLD.nature;

import com.tuana9a.OLD.Entity;
import com.tuana9a.app.ResourceManager;
import com.tuana9a.game.WorldMap;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BaseGround {
    public static final int DEFAULT_WIDTH = 64, DEFAULT_HEIGHT = 64;

    private WorldMap worldMap;
    private Entity.Block bounds;

    public String name;
    private BufferedImage image;

    private BaseGround(WorldMap worldMap, String name, int x, int y, int w, int h) {
        this.name = name;
        this.worldMap = worldMap;
        this.image = ResourceManager.getInstance().getImage(name);
        this.bounds = new Entity.Block(x, y, w, h);
    }

    public void render(Graphics g) {
        g.drawImage(image, bounds.x(), bounds.y(), bounds.w(), bounds.h(), null);
    }

}
