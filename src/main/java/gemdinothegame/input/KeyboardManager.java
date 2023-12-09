package gemdinothegame.input;

import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;

public class KeyboardManager implements KeyListener {
    private boolean[] keys;
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    public boolean w;
    public boolean s;
    public boolean a;
    public boolean d;
    public boolean fire;
    public boolean fireLast;
    public boolean gamePause;
    public boolean dropWeapon;
    public boolean dropWeaponLast;
    public boolean takeWeapon;
    public boolean takeWeaponLast;
    public boolean switchWeapon;
    public boolean switchWeaponLast;
    public boolean innerBoundMode;
    public boolean outBoundMode;
    public boolean workingAreaMode;
    public boolean rotationMode;
    public boolean freeCamMode;

    public KeyboardManager() {
        this.keys = new boolean[1000];
    }

    public void update() {
        this.fire = this.keys[32];
        this.takeWeapon = this.keys[69];
        this.dropWeapon = this.keys[82];
        this.switchWeapon = this.keys[81];
        if (!this.fire) {
            this.fireLast = false;
        }
        if (!this.takeWeapon) {
            this.takeWeaponLast = false;
        }
        if (!this.dropWeapon) {
            this.dropWeaponLast = false;
        }
        if (!this.switchWeapon) {
            this.switchWeaponLast = false;
        }
        this.w = this.keys[87];
        this.s = this.keys[83];
        this.a = this.keys[65];
        this.d = this.keys[68];
        this.up = this.keys[38];
        this.down = this.keys[40];
        this.left = this.keys[37];
        this.right = this.keys[39];
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        try {
            this.keys[e.getKeyCode()] = true;
        } catch (Exception ignored) {
        }
        this.update();
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        try {
            this.keys[e.getKeyCode()] = false;
            switch (e.getKeyCode()) {
                case 112: {
                    this.innerBoundMode = !this.innerBoundMode;
                    break;
                }
                case 113: {
                    this.outBoundMode = !this.outBoundMode;
                    break;
                }
                case 114: {
                    this.rotationMode = !this.rotationMode;
                    break;
                }
                case 115: {
                    this.freeCamMode = !this.freeCamMode;
                    break;
                }
                case 116: {
                    this.workingAreaMode = !this.workingAreaMode;
                    break;
                }
            }
        } catch (Exception ex) {
        }
        this.update();
    }

    @Override
    public void keyTyped(final KeyEvent e) {
        this.update();
    }

    public boolean takeWeapon() {
        return this.takeWeapon && !this.takeWeaponLast && (this.takeWeaponLast = true);
    }

    public boolean switchWeapon() {
        return this.switchWeapon && !this.switchWeaponLast && (this.switchWeaponLast = true);
    }

    public boolean dropWeapon() {
        return this.dropWeapon && !this.dropWeaponLast && (this.dropWeaponLast = true);
    }
}
