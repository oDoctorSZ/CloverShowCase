package com.cloverspells.cloverspells.spells.fire;

import com.cloverspells.cloverspells.CloverSpells;
import com.cloverspells.cloverspells.enums.SpellType;
import com.cloverspells.cloverspells.references.Spell;
import com.cloverspells.cloverspells.ultils.Utils;
import com.rededark.clovercore.controller.CloverController;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class FireBall extends Spell {
    int task;
    int timer;

    public FireBall(double manaCost, String name, SpellType type, boolean repeatingTask) {
        super(manaCost, name, type, repeatingTask);
    }

    public FireBall() { }

    @Override
    public void onUseItem(Player player) {
        timer = 20;
        Location loc = player.getEyeLocation();
        Vector move = loc.getDirection().normalize();
        ParticleBuilder particle = new ParticleBuilder(ParticleEffect.FLAME, loc);


        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(CloverSpells.getPlugin(), new BukkitRunnable() {
            @Override
            public void run() {

                if (timer <= 20 && timer != 0) {
                    Utils.sphere(player, loc, move, particle, false);

                    loc.setX(loc.getX() + loc.getDirection().getX());
                    loc.setY(loc.getY() + loc.getDirection().getY());
                    loc.setZ(loc.getZ() + loc.getDirection().getZ());
                    timer--;

                } else if (timer == 0) {
                    CloverSpells.getPlugin().getServer().getScheduler().cancelTask(task);
                }

            }
        }, 1, 2);
    }
}
