package com.cloverspells.cloverspells.events;

import com.cloverspells.cloverspells.manager.SpellManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PQuitEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (SpellManager.casters.containsKey(e.getPlayer().getUniqueId())) {
            SpellManager.casters.remove(e.getPlayer().getUniqueId());
        }
    }
}
