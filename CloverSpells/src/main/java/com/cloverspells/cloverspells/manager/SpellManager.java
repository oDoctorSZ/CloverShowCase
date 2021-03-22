package com.cloverspells.cloverspells.manager;

import com.cloverspells.cloverspells.CloverSpells;
import com.cloverspells.cloverspells.enums.SpellType;
import com.cloverspells.cloverspells.references.Caster;
import com.cloverspells.cloverspells.references.Spell;
import com.cloverspells.cloverspells.spells.time.TimeExplosion;
import com.rededark.clovercore.controller.CloverController;
import org.bukkit.entity.Player;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SpellManager {

    public static List<Player> cooldown = new ArrayList<>();
    public static HashMap<UUID, Caster> casters = new HashMap<>();
    public static HashMap<SpellType, Spell[]> AllSpellsByType = new HashMap<>();
    public static List<Spell> allSpells = new ArrayList<>();


    public SpellManager(SpellType type, Player mage) {
        Spell[] casterSpells = AllSpellsByType.get(type);
        Caster caster = new Caster(mage, type, casterSpells);
        if (casters.get(mage.getUniqueId()) == null)  {
            casters.put(mage.getUniqueId(), caster);
            cast(caster);
        } else {
            cast(casters.get(mage.getUniqueId()));
        }
    }

    private void cast(Caster caster) {
        if (!caster.getPlayer().isSneaking()) {
            Spell spell = caster.getCurrentSpell();
            if (spell.getRepeatingTask()) {
                spell.instance(caster.getPlayer(), spell);
            } else {
                spell.onUseItem(caster.getPlayer());
            }
            
            caster.getPlayer().sendMessage(spell.getName());
        } else {
            Spell spell = caster.getCurrentSpell();
            Spell[] spells = caster.getSpells();
            int pos = 0;
            Spell nextSpell;
            for (Spell spell1 : spells) {
                pos++;
                if (spell1.getName().equals(spell.getName())) { break;}
            }
            int newPos = pos >= spells.length ? 0 : pos;
            nextSpell=spells[newPos];
            caster.setCurrentSpell(nextSpell);
            caster.getPlayer().sendMessage(nextSpell.getName());
        }
    }
}