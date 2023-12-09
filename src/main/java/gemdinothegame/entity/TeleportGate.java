package gemdinothegame.entity;

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
