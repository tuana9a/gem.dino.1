// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.entities;

import com.tuana9a.screen.GameScreen;

public class TeleGate extends StaticObject
{
    private final String mapId;
    
    public TeleGate(final GameScreen gameScreen, final int staticObjectId, final double x, final double y, final String mapId) {
        super(gameScreen, staticObjectId, x, y);
        this.mapId = mapId;
    }
    
    public void teleToNewMap() {
        this.gameScreen.getStage().teleportToNewMap(this.mapId);
    }
}
