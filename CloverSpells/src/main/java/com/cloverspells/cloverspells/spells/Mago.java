package com.cloverspells.cloverspells.spells;

import com.cloverspells.cloverspells.CloverSpells;
import com.cloverspells.cloverspells.ultils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.RegularColor;

public class Mago implements Listener {

    @EventHandler
    public void onUseItem(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (player.getItemInHand().getType() == Material.BLAZE_ROD) {
            player.sendMessage("Usa essa classe para testes!");
        }
}


}
