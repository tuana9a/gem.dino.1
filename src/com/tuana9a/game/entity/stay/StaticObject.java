// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.game.entity.stay;

import com.tuana9a.state.GameState;
import com.tuana9a.graphic.animation.MoveAnimation;
import com.tuana9a.graphic.Assets;
import com.tuana9a.config.ConfigStaticObject;
import com.tuana9a.game.entity.StaticEntity;

public class StaticObject extends StaticEntity
{
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
    
    public StaticObject(final GameState gameState, final int staticObjectId, final double x, final double y) {
        super(gameState, staticObjectId, x, y);
        this.moveDirect = 0;
    }
    
    @Override
    public void update() {
        this.moveAnimation.update();
        this.updatePositionCam();
    }
}
