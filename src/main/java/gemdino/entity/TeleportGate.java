package gemdino.entity;

import gemdino.app.App;

public class TeleportGate extends StaticObject {
    private final String mapId;

    public TeleportGate(App app, final int staticObjectId, final double x, final double y, final String mapId) {
        super(app, staticObjectId, x, y);
        this.mapId = mapId;
    }

    public String getMapId() {
        return mapId;
    }
}
