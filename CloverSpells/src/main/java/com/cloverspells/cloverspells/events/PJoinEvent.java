package com.cloverspells.cloverspells.events;

import com.cloverspells.cloverspells.CloverSpells;
import com.cloverspells.cloverspells.enums.SpellType;
import com.cloverspells.cloverspells.manager.SpellManager;
import com.cloverspells.cloverspells.references.Caster;
import com.cloverspells.cloverspells.references.Spell;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PJoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        SpellType typeSpell = null;
        switch (CloverSpells.grimorio) {
            case "[FIRE]":
                typeSpell = SpellType.FIRE;
                break;
            case "[TIME]":
                typeSpell = SpellType.TIME;
                break;
            case "[WIND]":
                typeSpell = SpellType.WIND;
                break;
        }
        e.getPlayer().sendMessage(typeSpell+"");
        if (typeSpell != null) {
            Spell[] casterSpells = SpellManager.AllSpellsByType.get(typeSpell);
            Caster caster = new Caster(e.getPlayer(), typeSpell, casterSpells);
            SpellManager.casters.put(e.getPlayer().getUniqueId(), caster);
        }
    }

}
