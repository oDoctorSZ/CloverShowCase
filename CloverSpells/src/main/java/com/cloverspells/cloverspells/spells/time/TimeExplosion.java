package com.cloverspells.cloverspells.spells.time;

import com.cloverspells.cloverspells.CloverSpells;
import com.cloverspells.cloverspells.enums.SpellType;
import com.cloverspells.cloverspells.manager.SpellManager;
import com.cloverspells.cloverspells.references.Spell;
import com.cloverspells.cloverspells.ultils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.RegularColor;

public class TimeExplosion extends TimeSpell {

    private int timer = 20;
    public int task;

    public TimeExplosion(double manaCost, String name, SpellType type, boolean repeatingTask) {
        super(manaCost, name, type, repeatingTask);
    }

    public TimeExplosion() {  }

    @Override
    public void onUseItem(Player player) {


            Location loc = player.getEyeLocation();
            Vector move = loc.getDirection().normalize();


            ParticleBuilder particle = new ParticleBuilder(ParticleEffect.REDSTONE, loc).setParticleData(new RegularColor(0,255,255));

            task = Bukkit.getScheduler().scheduleSyncRepeatingTask(CloverSpells.getPlugin(), new BukkitRunnable() {
                @Override
                public void run() {

                    if (timer <= 20 && timer != 0) {
                        Utils.sphere(player, loc, move, particle, true);

                        loc.setX(loc.getX() + loc.getDirection().getX());
                        loc.setY(loc.getY() + loc.getDirection().getY());
                        loc.setZ(loc.getZ() + loc.getDirection().getZ());

                        timer--;

                    } else if (timer == 0) {
                        timer = 20;
                        CloverSpells.getPlugin().getServer().getScheduler().cancelTask(task);
                    }

                }
            }, 1, 2);
    }


}
