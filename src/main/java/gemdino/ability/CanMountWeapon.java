package gemdino.ability;

import java.awt.Graphics;

import gemdino.entity.Animal;
import gemdino.weapon.Weapon;

// TODO: redesign: linh động trong rel position so với animal
//  có thể sẽ không cần chuyển position khi nhân vật quay người
//  nếu muốn xoay thì cần thêm cơ chế detect và checking ...
public class CanMountWeapon {
    public final int MAX_USING = 2;
    private final int maxUsing;
    private final Weapon[] weapons;
    private int selectedWeapon;
    private final Animal owner;

    public void printSlotInfo() {
        System.out.println("========================");
        System.out.println("selected: " + this.selectedWeapon);
        System.out.println("slot (0): " + this.weapons[0]);
        System.out.println("slot (1): " + this.weapons[1]);
    }

    public CanMountWeapon(final Animal owner) {
        this.owner = owner;
        this.maxUsing = 2;
        this.weapons = new Weapon[this.maxUsing];
        this.selectedWeapon = 0;
    }

    public CanMountWeapon(final Animal owner, final int maxUsing) {
        this.owner = owner;
        this.maxUsing = maxUsing;
        this.weapons = new Weapon[maxUsing];
        this.selectedWeapon = 0;
    }

    public void takeWeapon(final Weapon weapon) {
        if (weapon.owner != null) {
            return;
        }
        weapon.setOwner(this.owner);
        weapon.updateState(3);
        int index = this.selectedWeapon;
        int loopCount = 0;
        while (loopCount <= this.maxUsing) {
            ++loopCount;
            if (this.weapons[index] == null) {
                this.selectedWeapon = index;
                this.weapons[index] = weapon;
                return;
            }
            this.weapons[index].updateState(2);
            if (++index != this.maxUsing) {
                continue;
            }
            index = 0;
        }
        this.dropWeapon();
        this.weapons[this.selectedWeapon] = weapon;
    }

    public void dropWeapon() {
        if (this.weapons[this.selectedWeapon] == null) {
            return;
        }
        final Weapon w = this.weapons[this.selectedWeapon];
        w.updateState(0);
        w.updatePosition(this.owner.x + this.owner.width / 2.0 - w.actualSizeW() / 2.0 - w.actualSizeX(), this.owner.y + this.owner.height / 2.0 - w.actualSizeH() / 2.0 - w.actualSizeY());
        w.setOwner(null);
        this.weapons[this.selectedWeapon] = null;
    }

    public void dropWeapon(final Weapon w) {
        if (w.owner != this.owner) {
            return;
        }
        for (int i = 0; i < this.maxUsing; ++i) {
            final Weapon we = this.weapons[i];
            if (w == we) {
                we.updateState(0);
                we.updatePosition(this.owner.x + this.owner.width / 2.0 - w.actualSizeW() / 2.0 - w.actualSizeX(), this.owner.y + this.owner.height / 2.0 - w.actualSizeH() / 2.0 - w.actualSizeY());
                we.setOwner(null);
                this.weapons[i] = null;
                return;
            }
        }
    }

    public void stealWeapon(final Weapon w) {
        if (this.isFull()) {
            return;
        }
        if (w.owner != null) {
            w.owner.dropWeapon(w);
        }
        this.takeWeapon(w);
    }

    public void dropAllWeapon() {
        for (int i = 0; i < this.maxUsing; ++i) {
            if (this.weapons[i] != null) {
                final Weapon w = this.weapons[i];
                w.updateState(0);
                w.updatePosition(this.owner.x + this.owner.width / 2.0 - w.actualSizeW() / 2.0 - w.actualSizeX(), this.owner.y + this.owner.height / 2.0 - w.actualSizeH() / 2.0 - w.actualSizeY());
                w.setOwner(null);
                this.weapons[i] = null;
            }
        }
    }

    public void switchWeapon() {
        if (this.weapons[this.selectedWeapon] != null) {
            this.weapons[this.selectedWeapon].updateState(2);
        }
        ++this.selectedWeapon;
        if (this.selectedWeapon == this.maxUsing) {
            this.selectedWeapon = 0;
        }
        if (this.weapons[this.selectedWeapon] != null) {
            this.weapons[this.selectedWeapon].updateState(3);
        }
    }

    public void switchWeapon(final int i) {
        if (this.weapons[this.selectedWeapon] != null) {
            this.weapons[this.selectedWeapon].updateState(2);
        }
        ++this.selectedWeapon;
        if (this.selectedWeapon == this.maxUsing) {
            this.selectedWeapon = 0;
        }
        if (this.weapons[this.selectedWeapon] != null) {
            this.weapons[this.selectedWeapon].updateState(3);
        }
    }

    public boolean isFull() {
        int count = 0;
        for (final Weapon w : this.weapons) {
            if (w != null) {
                ++count;
            }
        }
        return count == this.maxUsing;
    }

    public Weapon getSelectedWeapon() {
        return this.weapons[this.selectedWeapon];
    }

    public boolean hasWeapon(final int id) {
        return this.weapons[id] != null;
    }

    public Animal owner() {
        return this.owner;
    }

    public Weapon[] getAllWeapons() {
        return this.weapons;
    }

    public void renderSelectedWeapon(final Graphics g) {
        if (this.weapons[this.selectedWeapon] == null) {
            return;
        }
        this.weapons[this.selectedWeapon].render(g);
    }
}
