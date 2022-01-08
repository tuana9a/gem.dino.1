// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.entities;

import com.tuana9a.state.GameState;

public class TeleGate extends StaticObject
{
    private final String mapId;
    
    public TeleGate(final GameState gameState, final int staticObjectId, final double x, final double y, final String mapId) {
        super(gameState, staticObjectId, x, y);
        this.mapId = mapId;
    }
    
    public void teleToNewMap() {
        this.gameState.getStage().teleToNewMap(this.mapId);
    }
}
