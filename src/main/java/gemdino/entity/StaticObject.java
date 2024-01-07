package gemdino.entity;

import gemdino.animation.MoveAnimation;
import gemdino.app.App;
import gemdino.configs.ConfigStaticObject;
import gemdino.graphic.Assets;

public class StaticObject extends StaticEntity {
    public static final int STATE_NUMBER = 1;
    public boolean penAble;

    @Override
    protected void initCoreInfo(final int staticObjectId) {
        this.setSize(ConfigStaticObject.widths[staticObjectId], ConfigStaticObject.heights[staticObjectId]);
        this.setActualSize(ConfigStaticObject.boundX[staticObjectId], ConfigStaticObject.boundY[staticObjectId], ConfigStaticObject.boundWidth[staticObjectId], ConfigStaticObject.boundHeight[staticObjectId]);
        this.initActualSizeOrigin();
    }

    @Override
    protected void initOtherInfo(final int staticObjectId) {
        this.penAble = ConfigStaticObject.penAbles[staticObjectId];
        this.moveAnimation = new MoveAnimation(Assets.staticObjects[staticObjectId]);
    }

    public StaticObject(App app, final int staticObjectId, final double x, final double y) {
        super(app, staticObjectId, x, y);
        this.moveDirect = 0;
    }

    @Override
    public void update() {
        this.moveAnimation.update();
        this.updatePositionCam();
    }
}
