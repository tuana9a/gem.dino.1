package com.tuana9a.gemdino.entities.player;

import com.tuana9a.gemdino.app.EventQueue;
import com.tuana9a.gemdino.engine.GameWorld;
import com.tuana9a.gemdino.entities.weapon.WeaponOut;
import com.tuana9a.gemdino.entities.TeleportGate;
import com.tuana9a.gemdino.entities.weapon.Weapon;
import com.tuana9a.gemdino.entities.Entity;
import com.tuana9a.gemdino.animation.MoveAnimation;
import com.tuana9a.gemdino.animation.StateAnimation;
import com.tuana9a.gemdino.configs.ConfigPlayer;
import com.tuana9a.gemdino.entities.Animal;
import com.tuana9a.gemdino.graphic.Assets;
import com.tuana9a.gemdino.input.KeyboardManager;
import com.tuana9a.gemdino.screen.GameScreen;

public class Player extends Animal {
    @Override
    protected void initCoreInfo(final int playerId) {
        this.setSize(ConfigPlayer.widths[playerId], ConfigPlayer.heights[playerId]);
        this.setActualSize(ConfigPlayer.boundX[playerId], ConfigPlayer.boundY[playerId], ConfigPlayer.boundWidth[playerId], ConfigPlayer.boundHeight[playerId]);
        this.initActualSizeOrigin();
    }

    @Override
    protected void initOtherInfo(final int playerId) {
        this.speed = ConfigPlayer.speeds[playerId];
        this.health = ConfigPlayer.healths[playerId];
        this.holdingHand = ConfigPlayer.holdingHands[playerId];
        this.shoulder = ConfigPlayer.shoulders[playerId];
        this.moveAnimation = new MoveAnimation(Assets.players[playerId]);
    }

    @Override
    protected void initStateAnimation() {
        super.initStateAnimation();
        this.allStateAnimations[Player.NORMAL] = new StateAnimation(Assets.emoteFaceHappy, new double[][]{{(this.width - 32) / 2.0f, -32.0}, {(this.width - 32) / 2.0f, -32.0}}, 32.0, 32.0);
        this.allStateAnimations[Player.DEAD] = new StateAnimation(Assets.deadState, new double[][]{{0.0, 0.0}, {0.0, 0.0}}, this.width, this.height);
        this.allStateAnimations[Player.HIT] = new StateAnimation(Assets.emoteFaceSad, new double[][]{{(this.width - 32) / 2.0f, -32.0}, {(this.width - 32) / 2.0f, -32.0}}, 32.0, 32.0);
    }

    public Player(final GameScreen gameScreen, final int playerId, final double x, final double y) {
        super(playerId, x, y);
        gameScreen.updateUiPayerHp(this.health);
    }

    private void updateMoveKeyboard() {
        KeyboardManager keyboardManager = KeyboardManager.getInstance();
        final double n = 0.0;
        this.yMove = n;
        this.xMove = n;
        final boolean up = keyboardManager.w;
        final boolean down = keyboardManager.s;
        final boolean left = keyboardManager.a;
        final boolean right = keyboardManager.d;
        if (!up || !down) {
            if (up) {
                this.yMove = -this.speed;
            }
            if (down) {
                this.yMove = this.speed;
            }
        }
        if (!left || !right) {
            if (left) {
                this.xMove = -this.speed;
            }
            if (right) {
                this.xMove = this.speed;
            }
        }
    }

    @Override
    public void intersectWith(final Entity e) {
        if (e instanceof Weapon && ((Weapon) e).owner != null) {
            return;
        }
        this.intersectEntities.add(e);
    }

    @Override
    protected void updateInteract() {
        KeyboardManager keyboardManager = KeyboardManager.getInstance();
        if (keyboardManager.dropWeapon()) {
            this.dropWeapon();
        }
        if (keyboardManager.switchWeapon()) {
            this.switchWeapon();
        }
        for (final Entity e : this.intersectEntities) {
            if (e instanceof Weapon && keyboardManager.takeWeapon()) {
                this.takeWeapon((Weapon) e);
            } else if (e instanceof TeleportGate) {
                EventQueue eventQueue = EventQueue.getInstance();
                eventQueue.push(() -> {
                    GameWorld gameWorld = GameWorld.getInstance();
                    gameWorld.teleportToNewMap(((TeleportGate) e).getMapId());
                });
            }
        }
        this.intersectEntities.clear();
    }

    @Override
    public void hitBy(final WeaponOut wo) {
        super.hitBy(wo);
        final GameScreen gameScreen = app.getGameScreen();
        gameScreen.updateUiPayerHp(this.health);
    }

    @Override
    protected void typicalUpdate() {
        this.updateMoveKeyboard();
    }

    public void reborn() {
        super.reborn();
        this.health = ConfigPlayer.healths[this.id];
    }
}
