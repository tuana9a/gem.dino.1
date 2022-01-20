package com.tuana9a.OLD.nature;

import com.tuana9a.ani.TwoDirectAnimation;
import com.tuana9a.app.KeyboardManager;
import com.tuana9a.entity.config.ConfigPlayer;
import com.tuana9a.screen.GameScreen;

public class Player extends Animal {

    @Override
    protected void init(int playerId) {
        setSize(ConfigPlayer.widths[playerId], ConfigPlayer.heights[playerId]);
        setBounds(
                ConfigPlayer.boundX[playerId], ConfigPlayer.boundY[playerId],
                ConfigPlayer.boundWidth[playerId], ConfigPlayer.boundHeight[playerId]
        );
        initBoundsOrigin();
    }

    @Override
    protected void initAbstract(int playerId) {
        speed = ConfigPlayer.speeds[playerId];
        hand = new Hand(this, ConfigPlayer.holdingHands[playerId]);

        moveAnimation = new TwoDirectAnimation();
    }

    protected Player(GameScreen gameScreen, int playerId, double x, double y) {
        super(gameScreen, playerId, x, y);
    }

    public static Player construct(GameScreen gameScreen, int playerId, double x, double y) {
        return new Player(gameScreen, playerId, x, y);
    }

    //SECTION: update
    private void updateMoveKeyboard() {
        xMove = yMove = 0;
        KeyboardManager keyboardManager = KeyboardManager.getInstance();
        boolean up = keyboardManager.isPressing(KeyboardManager.PRIMARY_MOVE_UP_KEY);
        boolean down = keyboardManager.isPressing(KeyboardManager.PRIMARY_MOVE_DOWN_KEY);
        boolean left = keyboardManager.isPressing(KeyboardManager.PRIMARY_MOVE_LEFT_KEY);
        boolean right = keyboardManager.isPressing(KeyboardManager.PRIMARY_MOVE_RIGHT_KEY);
        if (!(up && down)) {
            if (up) {
                yMove = -speed;
            }
            if (down) {
                yMove = speed;
            }
        }
        if (!(left && right)) {
            if (left) {
                xMove = -speed;
            }
            if (right) {
                xMove = speed;
            }
        }
    }

    @Override
    public void updateAbstract() {
        updateMoveKeyboard();
    }


    //SECTION: collide
    @Override
    public void updateInteract() {
        clearCollided();
    }

}
