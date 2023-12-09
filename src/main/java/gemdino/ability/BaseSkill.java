package gemdino.ability;

import gemdino.entity.Animal;

public abstract class BaseSkill {
    public Animal fromAnimal;
    public static final long DEFAULT_SKILL_TIME = 5000L;

    public BaseSkill(final Animal fromAnimal) {
        this.fromAnimal = fromAnimal;
    }

    public abstract void perform();
}
