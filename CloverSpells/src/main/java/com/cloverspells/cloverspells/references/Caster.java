package com.cloverspells.cloverspells.references;

import com.cloverspells.cloverspells.enums.SpellType;
import org.bukkit.entity.Player;

public class Caster {
    private Player player;
    private Spell[] spells;
    private SpellType typeSpell;
    private Spell currentSpell;

    public Caster(Player player, SpellType type, Spell...spells) {
        this.spells = spells;
        this.player = player;
        this.typeSpell = type;
        this.currentSpell = spells[0];
    }

    public void setCurrentSpell(Spell currentSpell) { this.currentSpell = currentSpell; }

    public Spell getCurrentSpell() {
        return currentSpell;
    }

    public SpellType getTypeSpell() {
        return typeSpell;
    }

    public Spell[] getSpells() {
        return spells;
    }

    public void setSpells(Spell[] spells) {
        this.spells = spells;
    }

    public Player getPlayer() {
        return player;
    }
}
