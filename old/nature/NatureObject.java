package com.tuana9a.OLD.nature;

import com.tuana9a.ani.TwoDirectAnimation;
import com.tuana9a.entity.config.ConfigNature;
import com.tuana9a.entity.nature.nature.transform.ExpandObject;
import com.tuana9a.entity.nature.nature.transform.TriggerObject;
import com.tuana9a.screen.GameScreen;

public class NatureObject extends StayEntity {
    public boolean penAble;

    @Override
    protected void init(int id) {
        setSize(ConfigNature.widths[id], ConfigNature.heights[id]);
        setBounds(
                ConfigNature.boundX[id], ConfigNature.boundY[id],
                ConfigNature.boundWidth[id], ConfigNature.boundHeight[id]
        );
        initBoundsOrigin();
    }

    @Override
    protected void initAbstract(int id) {
        penAble = ConfigNature.penAble[id];
        moveAnimation = new TwoDirectAnimation();
    }

    protected NatureObject(GameScreen gameScreen, int id, double x, double y) {
        super(gameScreen,id, x, y);
    }

    public static NatureObject construct(GameScreen gameScreen, int id, double x, double y) {
        if(BaseGround.isTriggerAbove(id)) return TriggerObject.construct(gameScreen, id, x, y);
        if(BaseGround.isExpandAbove(id)) return ExpandObject.construct(gameScreen, id, x, y);
        //TODO: expand giờ mặc định là trans 200px và nằm gọn trong nature object

        return  new NatureObject(gameScreen, id, x, y);
    }


    @Override
    //EXPLAIN: do nothing by default
    public void update() {
    }
}
