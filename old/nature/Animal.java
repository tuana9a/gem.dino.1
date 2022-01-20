package com.tuana9a.OLD.nature;

import com.tuana9a.OLD.Entity;
import com.tuana9a.entity.abilities.AbstractEntity;
import com.tuana9a.entity.abilities.CanMove;
import com.tuana9a.screen.GameScreen;

public abstract class Animal extends MoveEntity implements CanMove, AbstractEntity {

    protected Animal(GameScreen gameScreen, int id, double x, double y) {
        super(gameScreen, id, x, y);
    }


    @Override
    public void update() {
        updateInteract();

        move();
        updateAbstract();

        onAlive();
    }

    //EXPLAIN: update x,y according  to xMove, yMove
    @Override
    public void move() {
        if (xMove > 0) {
            direct = RIGHT_INDEX;
        } else if (xMove < 0) {
            direct = LEFT_INDEX;
        }

        //EXPLAIN: move is handled by the map
        entityManager.getGameEngine().getMap().animalMove(this);
    }


    //SECTION: colliding
    @Override
    public void beCollidedBy(Entity e) {

    }


    public void onAlive() {
    }

}
