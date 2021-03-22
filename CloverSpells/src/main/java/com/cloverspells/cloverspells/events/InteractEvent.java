package com.cloverspells.cloverspells.events;

import com.cloverspells.cloverspells.CloverSpells;
import com.cloverspells.cloverspells.enums.SpellType;
import com.cloverspells.cloverspells.interfaces.OnUseItem;
import com.cloverspells.cloverspells.manager.SpellManager;
import com.cloverspells.cloverspells.references.Caster;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractEvent implements Listener, OnUseItem {

    @Override
    @EventHandler
    public void onUseItem(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        try {
            if (player.getItemInHand().getType() == Material.STICK) {
                Caster caster = SpellManager.casters.get(player.getUniqueId());
                new SpellManager(caster.getTypeSpell(), player);
            }
            } catch(Exception err) {
            if (player.getItemInHand().getType() == Material.STICK) {
            new SpellManager(SpellType.FIRE, e.getPlayer());
        }
        }



    }
}
