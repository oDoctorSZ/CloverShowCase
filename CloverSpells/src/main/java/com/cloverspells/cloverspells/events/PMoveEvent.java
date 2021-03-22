package com.cloverspells.cloverspells.events;

import com.cloverspells.cloverspells.spells.time.TimeStop;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PMoveEvent implements Listener {


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (TimeStop.getEntities("player").contains(player)) {e.setCancelled(true);}
    }
}
