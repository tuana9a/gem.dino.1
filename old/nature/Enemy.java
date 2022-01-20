package com.tuana9a.OLD.nature;

import com.tuana9a.OLD.Entity;
import com.tuana9a.OLD.weapon.Weapon;
import com.tuana9a.ani.TwoDirectAnimation;
import com.tuana9a.app.App;
import com.tuana9a.app.KeyboardManager;
import com.tuana9a.entity.config.ConfigEnemy;
import com.tuana9a.screen.GameScreen;
import com.tuana9a.utils.Algebra;

public abstract class Enemy extends Animal {

    public Block workingArea;
    public App.Timer reactionTimer;//EXPLAIN: thời gian phản ứng khi gặp người chơi

    @Override
    protected void init(int enemyId) {
        setSize(ConfigEnemy.widths[enemyId], ConfigEnemy.heights[enemyId]);
        setBounds(
                ConfigEnemy.boundX[enemyId], ConfigEnemy.boundY[enemyId],
                ConfigEnemy.boundWidth[enemyId], ConfigEnemy.boundHeight[enemyId]
        );
        initBoundsOrigin();
    }

    @Override
    protected void initAbstract(int enemyId) {
        speed = ConfigEnemy.speeds[enemyId];
        hand = new Hand(this, ConfigEnemy.holdingHands[enemyId]);

        moveAnimation = new TwoDirectAnimation();

        reactionTimer = new App.Timer();
        reactionTimer.delta = ConfigEnemy.reactionTimers[enemyId];
        abstractTimer.delta = ConfigEnemy.abstractTimers[enemyId];
        workingArea = new Block((int) x, (int) y, ConfigEnemy.workDistances[enemyId], ConfigEnemy.workDistances[enemyId]);
    }

    protected Enemy(GameScreen gameScreen, int enemyId, double x, double y) {
        super(gameScreen, enemyId, x, y);
    }


    //SECTION: update
    @Override
    public void updateInteract() {

        clearCollided();
    }

    @Override
    public void updateAbstract() {
        if (reactionTimer.timeup()) {
            reactionTimer.reset();//EXPLAIN: dùng cho bắn
        }

        //        TEST move keyboard
        //        updateMoveKeyboard();

        //        TEST move scripted
        if (!abstractTimer.timeup()) {
            return; //EXPLAIN: dùng cho di chuyển
        }
        abstractTimer.reset();

        Player player = entityManager.getPlayer();
        if (!canSee(player)) {
            //            System.out.println("random");
            updateMoveRandom();
            return;
        }
        //        System.out.println("can see");
        updateMoveEntity(player);
        updateWorkingArea(
                (int) (x - workingArea.width / 2),
                (int) (y - workingArea.height / 2),
                workingArea.width,
                workingArea.height
        );
    }

    private void updateMoveRandom() {
        double currentX = x;
        double currentY = y;
        double nextX = Math.random() * workingArea.width + workingArea.x;
        double nextY = Math.random() * workingArea.width + workingArea.y;
        double radianToNext = Algebra.getRotate(currentX, currentY, nextX, nextY);
        xMove = speed * Math.cos(radianToNext);
        yMove = speed * Math.sin(radianToNext);
    }

    private void updateMoveKeyboard() {
        xMove = 0;
        yMove = 0;
        KeyboardManager keyboardManager = KeyboardManager.getInstance();
        boolean up = keyboardManager.isPressing(KeyboardManager.SECOND_MOVE_UP_KEY);
        boolean down = keyboardManager.isPressing(KeyboardManager.SECOND_MOVE_DOWN_KEY);
        boolean left = keyboardManager.isPressing(KeyboardManager.SECOND_MOVE_LEFT_KEY);
        boolean right = keyboardManager.isPressing(KeyboardManager.SECOND_MOVE_RIGHT_KEY);
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

    private void updateMoveEntity(Entity entity) {
        double startX = x + width / 2;
        double startY = y + bounds.y() + bounds.h();

        double desX = entity.x + entity.width / 2;
        double desY = entity.y + entity.bounds.y() + entity.bounds.h();

        double minDis = 100;
        if (Math.abs(desX - startX) < minDis && Math.abs(desY - startY) < minDis) {
            //            System.out.println("min happend");
            xMove = yMove = 0;
            return;
        }

        double radianToEntity = Algebra.getRotate(startX, startY, desX, desY);

        xMove = speed * Math.cos(radianToEntity);
        yMove = speed * Math.sin(radianToEntity);
    }

    private void updateWorkingArea(int x, int y, int w, int h) {
        if (workingArea == null) {
            workingArea = new Block(x, y, w, h);
        } else {
            workingArea.x = x;
            workingArea.y = y;
            workingArea.width = w;
            workingArea.height = h;
        }
    }


    //SECTION: collide
    @Override
    public void collideWith(Entity e) {
        if (!(e instanceof Weapon)) {
            return;
        }

        Weapon w = (Weapon) e;
        if (w.owner != null) {
            return;
        }

        collidedEntities.add(e);
    }

    //SECTION: checker
    public static boolean isBoss(int id) {
        for (int i : ConfigEnemy.bosseIds) {
            if (id == i) {
                return true;
            }
        }
        return false;
    }

    public static boolean isElite(int id) {
        for (int i : ConfigEnemy.eliteIds) {
            if (id == i) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMinion(int id) {
        for (int i : ConfigEnemy.minionIds) {
            if (id == i) {
                return true;
            }
        }
        return false;
    }
}
