package gemdino.player;

import gemdino.app.App;
import gemdino.app.EventQueue;
import gemdino.engine.GameWorld;
import gemdino.weapon.WeaponOut;
import gemdino.entity.TeleportGate;
import gemdino.weapon.Weapon;
import gemdino.entity.Entity;
import gemdino.animation.MoveAnimation;
import gemdino.animation.StateAnimation;
import gemdino.configs.ConfigPlayer;
import gemdino.entity.Animal;
import gemdino.graphic.Assets;
import gemdino.input.KeyboardManager;
import gemdino.screen.GameScreen;

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

    public Player(App app, final int playerId, final double x, final double y) {
        super(app, playerId, x, y);
        app.getGameScreen().updateUiPayerHp(this.health);
    }

    private void updateMoveKeyboard() {
        KeyboardManager keyboardManager = app.getKeyboardManager();
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
        KeyboardManager keyboardManager = app.getKeyboardManager();
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
                EventQueue eventQueue = app.getEventQueue();
                eventQueue.push(() -> {
                    GameWorld gameWorld = app.getGameWorld();
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
