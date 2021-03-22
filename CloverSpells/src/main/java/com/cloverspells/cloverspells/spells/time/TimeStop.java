package com.cloverspells.cloverspells.spells.time;

import com.cloverspells.cloverspells.CloverSpells;
import com.cloverspells.cloverspells.enums.SpellType;
import com.cloverspells.cloverspells.interfaces.OnUseItem;
import com.cloverspells.cloverspells.manager.SpellManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.ArrayList;
import java.util.List;


public class TimeStop extends TimeSpell {

    private static List<LivingEntity> entities = new ArrayList<>();
    private static List<LivingEntity> npcs = new ArrayList<>();

    public TimeStop(double manaCost, String name, SpellType type, boolean repeatingTask) {
        super(manaCost, name, type, repeatingTask);
    }


    public static List<LivingEntity> getEntities(String type) {
        if (type.equals("player")) {
            return entities;
        } else {
            return npcs;
        }
    }

    @Override
    public void onUseItem(Player player) {
        if (player.getItemInHand().getType() == Material.STICK) {
            if (SpellManager.cooldown.contains(player)) { player.sendMessage("Segura as pontas, você ta em cooldown"); return; }
            Location loc = player.getEyeLocation();
            SpellManager.cooldown.add(player);
            player.getWorld().getNearbyEntities(loc, 5,5,5).forEach(entity -> {
                if (entity instanceof LivingEntity) {
                    if (player != entity) {

                        if (!(entity instanceof Player)) {
                            if (npcs.size() <= 4) {
                                npcs.add((LivingEntity) entity);

                                Bukkit.getScheduler().runTaskLaterAsynchronously(CloverSpells.getPlugin(), new Runnable() {
                                    @Override
                                    public void run() {
                                        npcs.remove(entity);
                                    }
                                }, 20 * 10);
                            }
                        }

                        ((LivingEntity) entity).damage(5, player);

                        entity.teleport(new Location(entity.getWorld(), entity.getLocation().getBlockX(), entity.getLocation().getBlockY()+0.1, entity.getLocation().getBlockZ()));

                        if (!entities.contains(entity)) {
                            if (entity != null) {
                                if (entities.size() <= 4) {
                                    entities.add((LivingEntity) entity);

                                    Bukkit.getScheduler().runTaskLaterAsynchronously(CloverSpells.getPlugin(), new Runnable() {
                                        @Override
                                        public void run() {
                                            entities.remove(entity);
                                        }
                                    }, 20 * 10);
                                }
                            }
                        }
                    }
                }
            });

            Bukkit.getScheduler().runTaskLater(CloverSpells.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    SpellManager.cooldown.remove(player);
                }
            }, 20*30);
        }
    }

}
