package com.cloverspells.cloverspells.spells.time;

import com.cloverspells.cloverspells.enums.SpellType;
import com.cloverspells.cloverspells.references.Spell;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TimeSpell extends Spell {

    public TimeSpell(double manaCost, String name, SpellType type, boolean repeatingTask) {
        super(manaCost, name, type, repeatingTask);
        this.setManaCost(manaCost);
        this.setName(name);
        this.setType(type);
    }

    public TimeSpell() {
        super();
    }
}
