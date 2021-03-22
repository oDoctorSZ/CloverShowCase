package com.cloverspells.cloverspells.interfaces;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

@FunctionalInterface
public interface OnUseItem {

    public void onUseItem(PlayerInteractEvent e);

}
