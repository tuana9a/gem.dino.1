// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.gemdino.entities;

public class TeleportGate extends StaticObject {
    private final String mapId;

    public TeleportGate(final int staticObjectId, final double x, final double y, final String mapId) {
        super(staticObjectId, x, y);
        this.mapId = mapId;
    }

    public String getMapId() {
        return mapId;
    }
}
