// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.game.entity.move.skills;

import com.tuana9a.game.entity.move.Animal;

public abstract class Skill
{
    public Animal fromAnimal;
    public static final long DEFAULT_SKILL_TIME = 5000L;
    
    public Skill(final Animal fromAnimal) {
        this.fromAnimal = fromAnimal;
    }
    
    public abstract void perform();
}
