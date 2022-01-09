// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.entities;

import com.tuana9a.App;
import com.tuana9a.environment.Camera;
import com.tuana9a.input.KeyboardManager;
import com.tuana9a.utils.Algebra;

import java.awt.Color;

import com.tuana9a.entities.weapon.WeaponOut;
import com.tuana9a.entities.enemy.Enemy;
import com.tuana9a.entities.weapon.Weapon;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.tuana9a.animation.StateAnimation;
import com.tuana9a.animation.MoveAnimation;
import com.tuana9a.utils.Timer;
import com.tuana9a.screen.GameScreen;

public abstract class Entity {
    public static final int STAY_DIRECT = 0;
    public static final int LEFT_DIRECT = 0;
    public static final int RIGHT_DIRECT = 1;
    public GameScreen gameScreen;
    protected int id;
    protected int state;
    public int moveDirect;
    public int width;
    public int height;
    public Timer typicalTimer;
    public MoveAnimation moveAnimation;
    public StateAnimation[] allStateAnimations;
    public ArrayList<Entity> intersectEntities;
    public double x;
    public double y;
    public double xCam;
    public double yCam;
    public Rectangle actualSize;
    public int actualSizeOriginX;
    public int actualSizeOriginY;
    public int actualSizeOriginW;
    public int actualSizeOriginH;
    public double[][] rotateRel;
    public double xRotateRelX;
    public double yRotateRelY;
    public double xRotateCam;
    public double yRotateCam;
    public double radianRotateMain;
    public double radianRotateLeft;
    public static final int LEFT_INDEX = 0;
    public static final int RIGHT_INDEX = 1;
    public static final int X_INDEX = 0;
    public static final int Y_INDEX = 1;

    protected abstract void initCoreInfo(final int p0);

    protected abstract void initOtherInfo(final int p0);

    protected void initStateAnimation() {
    }

    public Entity(final GameScreen gameScreen, final int id, final double x, final double y) {
        this.typicalTimer = new Timer();
        this.gameScreen = gameScreen;
        this.id = id;
        this.state = 0;
        this.updatePosition(x, y);
        this.moveDirect = 1;
        this.intersectEntities = new ArrayList<Entity>();
        this.initCoreInfo(id);
        this.rotateRel = new double[][]{{this.width / 2.0, this.height / 2.0}, {this.width / 2.0, this.height / 2.0}};
        this.xRotateRelX = this.width / 2.0;
        this.yRotateRelY = this.height / 2.0;
        this.initOtherInfo(id);
        this.initStateAnimation();
        this.updateRotateRelative();
    }

    public abstract void update();

    public void render(final Graphics g) {
        KeyboardManager keyboardManager = KeyboardManager.getInstance();
        if (this.xCam + this.actualSize.x + this.actualSize.width < 0.0 || this.xCam > this.gameScreen.getDisplayWidth() || this.yCam + this.actualSize.y + this.actualSize.height < 0.0 || this.yCam + this.actualSize.y > this.gameScreen.getDisplayHeight()) {
            return;
        }
        final Graphics2D g2d = (Graphics2D) g.create();
        if (this.moveDirect == 1) {
            g2d.rotate(this.radianRotateMain, this.xRotateCam, this.yRotateCam);
        } else if (this.moveDirect == 0) {
            g2d.rotate(this.radianRotateLeft, this.xRotateCam, this.yRotateCam);
        }
        final double recoilWeapon = (this instanceof Weapon) ? ((Weapon) this).recoilX : 0.0;
        this.moveAnimation.render(g2d, this.moveDirect, this.xCam + recoilWeapon, this.yCam, this.width, this.height);
        if (this.allStateAnimations != null && this.allStateAnimations[this.state] != null) {
            final StateAnimation animation = this.allStateAnimations[this.state];
            if (animation != null) {
                animation.render(g2d, this.moveDirect, this.xCam, this.yCam, this.xRotateCam, this.yRotateCam);
                animation.update();
            }
        }
        if (this.radianRotateMain > 9.42477796076938 || this.radianRotateMain < -9.42477796076938) {
            System.out.println(this.getClass().getName().replaceAll(".+\\.", "") + " radian overflow " + this.radianRotateMain);
        }
        if (keyboardManager.outBoundMode) {
            this.renderOuterBound(g);
        }
        if (this instanceof Enemy && keyboardManager.workingAreaMode) {
            this.renderWorkingArea(g);
        }
        if (this instanceof Enemy && keyboardManager.workingAreaMode) {
            this.renderEyeDistance(g);
        }
        if (keyboardManager.innerBoundMode) {
            this.renderInnerBound(g);
        }
        if (keyboardManager.rotationMode) {
            this.renderRotation(g);
        }
    }

    public void renderInnerBound(final Graphics g) {
        if (this instanceof WeaponOut) {
            g.setColor(Color.GREEN);
        } else if (this instanceof Animal) {
            g.setColor(Color.BLUE);
        } else if (this instanceof Weapon) {
            g.setColor(Color.BLUE);
        } else {
            g.setColor(Color.BLACK);
        }
        g.fillRect((int) this.xCam + this.actualSize.x, (int) this.yCam + this.actualSize.y, this.actualSize.width, this.actualSize.height);
        g.setColor(Color.CYAN);
        g.drawRect((int) this.xCam + this.actualSize.x, (int) this.yCam + this.actualSize.y, this.actualSize.width, this.actualSize.height);
    }

    public void renderOuterBound(final Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect((int) this.xCam, (int) this.yCam, this.width, this.height);
        g.setColor(Color.CYAN);
        g.drawRect((int) this.xCam, (int) this.yCam, this.width, this.height);
        g.drawLine((int) this.xCam, (int) this.yCam + this.actualSize.y, (int) this.xCam + this.actualSize.x, (int) this.yCam + this.actualSize.y);
        g.drawLine((int) this.xCam, (int) this.yCam, (int) this.xCam + this.actualSize.x, (int) this.yCam);
        g.drawLine((int) this.xCam + this.actualSize.x, (int) this.yCam, (int) this.xCam + this.actualSize.x, (int) this.yCam + this.actualSize.y);
        g.drawLine((int) this.xCam, (int) this.yCam, (int) this.xCam, (int) this.yCam + this.actualSize.y);
    }

    public void renderRotation(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g.create();
        double tempRadian = 0.0;
        if (this.moveDirect == 1) {
            tempRadian = this.radianRotateMain;
        } else if (this.moveDirect == 0) {
            tempRadian = this.radianRotateLeft;
        }
        g2d.rotate(tempRadian, this.xRotateCam, this.yRotateCam);
        g.setColor(Color.YELLOW);
        final int r2 = this.width / 2;
        g.fillArc((int) (this.xRotateCam - r2 / 2), (int) (this.yRotateCam - r2 / 2), r2, r2, 0, (int) Math.toDegrees(-tempRadian));
        g.setColor(Color.CYAN);
        g2d.setColor(Color.CYAN);
        final int r3 = this.width / 2;
        g.drawLine((int) this.xRotateCam, (int) this.yRotateCam, (int) this.xRotateCam + r3, (int) this.yRotateCam);
        g2d.drawLine((int) this.xRotateCam, (int) this.yRotateCam, (int) this.xRotateCam + r3, (int) this.yRotateCam);
        g.drawArc((int) (this.xRotateCam - r3 / 2), (int) (this.yRotateCam - r3 / 2), r3, r3, 0, (int) Math.toDegrees(-tempRadian));
        g.setColor(Color.RED);
        final int r4 = 3;
        g.fillOval((int) this.xRotateCam - r4, (int) this.yRotateCam - r4, r4 * 2, r4 * 2);
    }

    public void renderWorkingArea(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.RED);
        final Enemy e = (Enemy) this;
        final Camera camera = Camera.getInstance();
        final double x = camera.getCamRenderX(e.workingArea.x);
        final double y = camera.getCamRenderY(e.workingArea.y);
        g2d.drawRect((int) x, (int) y, e.workingArea.width, e.workingArea.height);
    }

    public void renderEyeDistance(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.BLUE);
        final Enemy e = (Enemy) this;
        final double r = e.eye().maxDis();
        final double x = e.xCam + e.width / 2.0 - r;
        final double y = e.yCam + e.height / 2.0 - r;
        g2d.drawOval((int) x, (int) y, (int) r * 2, (int) r * 2);
    }

    protected void updateInteract() {
    }

    public void intersectWith(final Entity e) {
    }

    public void hitBy(final WeaponOut wo) {
    }

    public void updateState(final int state) {
        this.state = state;
    }

    public void updateRotate(final double radian) {
        this.radianRotateMain = radian;
        this.radianRotateLeft = radian + ((radian >= 0.0) ? -1 : 1) * 3.141592653589793;
        this.updateBoundWhenRotate();
    }

    public void updateBoundWhenRotate() {
        final double xRelTemp = this.actualSizeOriginX + this.actualSizeOriginW / 2.0 - this.xRotateRelX;
        final double yRelTemp = this.actualSizeOriginY + this.actualSizeOriginH / 2.0 - this.yRotateRelY;
        final boolean heightSmaller = this.actualSizeOriginH < this.actualSizeOriginW;
        double tempRadian = 0.0;
        if (this.moveDirect == 1) {
            tempRadian = this.radianRotateMain;
        } else if (this.moveDirect == 0) {
            tempRadian = this.radianRotateLeft;
        }
        if (heightSmaller) {
            this.actualSize.width = Math.max((int) (this.actualSizeOriginW * Math.abs(Math.cos(tempRadian))), this.actualSizeOriginH);
            this.actualSize.height = Math.max((int) (this.actualSizeOriginW * Math.abs(Math.sin(tempRadian))), this.actualSizeOriginH);
        } else {
            this.actualSize.width = Math.max((int) (this.actualSizeOriginH * Math.abs(Math.sin(tempRadian))), this.actualSizeOriginW);
            this.actualSize.height = Math.max((int) (this.actualSizeOriginH * Math.abs(Math.cos(tempRadian))), this.actualSizeOriginW);
        }
        this.actualSize.x = (int) (this.xRotateRelX + Algebra.rotateX(xRelTemp, yRelTemp, tempRadian) - this.actualSize.width / 2.0);
        this.actualSize.y = (int) (this.yRotateRelY + Algebra.rotateY(xRelTemp, yRelTemp, tempRadian) - this.actualSize.height / 2.0);
    }

    public void correctRadianRotate() {
        if (this.radianRotateMain > 3.141592653589793) {
            while (this.radianRotateMain > 3.141592653589793) {
                this.radianRotateMain -= 6.283185307179586;
            }
            this.updateRotate(this.radianRotateMain);
        } else if (this.radianRotateMain < -3.141592653589793) {
            while (this.radianRotateMain < -3.141592653589793) {
                this.radianRotateMain += 6.283185307179586;
            }
            this.updateRotate(this.radianRotateMain);
        }
    }

    public void updatePosition(final double x, final double y) {
        this.x = x;
        this.y = y;
        this.updatePositionCam();
    }

    public void updatePositionCam() {
        final Camera camera = Camera.getInstance();
        this.xCam = camera.getCamRenderX(this.x);
        this.yCam = camera.getCamRenderY(this.y);
        this.updateRotatePositionCam();
    }

    public void updateRotateRelative() {
        if (this.moveDirect == 1) {
            this.xRotateRelX = this.rotateRel[1][0];
            this.yRotateRelY = this.rotateRel[1][1];
        } else if (this.moveDirect == 0) {
            this.xRotateRelX = this.rotateRel[0][0];
            this.yRotateRelY = this.rotateRel[0][1];
        }
    }

    public void updateRotatePositionCam() {
        final Camera camera = Camera.getInstance();
        this.xRotateCam = camera.getCamRenderX(this.x + this.xRotateRelX);
        this.yRotateCam = camera.getCamRenderY(this.y + this.yRotateRelY);
    }

    public Rectangle actualSize(final double xOffset, final double yOffset) {
        return new Rectangle((int) (this.x + this.actualSize.x + xOffset), (int) (this.y + this.actualSize.y + yOffset), this.actualSize.width, this.actualSize.height);
    }

    public Rectangle actualSize() {
        return this.actualSize(0.0, 0.0);
    }

    public boolean isCollideWith(final Entity e) {
        return this.isCollideWith(0.0, 0.0, e);
    }

    public boolean isCollideWith(final double xOffset, final double yOffset, final Entity e) {
        return this.actualSize(xOffset, yOffset).intersects(e.actualSize());
    }

    public void setSize(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    public void setActualSize(final int x, final int y, final int width, final int height) {
        if (this.actualSize == null) {
            this.actualSize = new Rectangle(x, y, width, height);
        } else {
            this.actualSize.x = x;
            this.actualSize.y = y;
            this.actualSize.width = width;
            this.actualSize.height = height;
        }
    }

    public void initActualSizeOrigin() {
        this.actualSizeOriginX = this.actualSize.x;
        this.actualSizeOriginY = this.actualSize.y;
        this.actualSizeOriginW = this.actualSize.width;
        this.actualSizeOriginH = this.actualSize.height;
    }

    public int actualSizeX() {
        return this.actualSize.x;
    }

    public int actualSizeY() {
        return this.actualSize.y;
    }

    public int actualSizeW() {
        return this.actualSize.width;
    }

    public int actualSizeH() {
        return this.actualSize.height;
    }
}
