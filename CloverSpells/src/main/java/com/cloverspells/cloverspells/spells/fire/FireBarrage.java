package com.cloverspells.cloverspells.spells.fire;

import com.cloverspells.cloverspells.CloverSpells;
import com.cloverspells.cloverspells.enums.SpellType;
import com.cloverspells.cloverspells.references.Spell;
import com.cloverspells.cloverspells.ultils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.RegularColor;

public class FireBarrage extends Spell {

    private int timer = 20;
    private double r = 0.2;
    public int task;
    private int times = 5;

    public FireBarrage(double manaCost, String name, SpellType type, boolean repeatingTask) {
        super(manaCost, name, type, repeatingTask);
    }

    public FireBarrage() {}

    @Override
    public void onUseItem(Player player) {
        Location loc = player.getEyeLocation();
        Vector move = loc.getDirection().normalize();
        FireBarrage fireBarrage = new FireBarrage();

        randomPosition(loc);

        ParticleBuilder particle = new ParticleBuilder(ParticleEffect.FLAME, loc);
        ParticleBuilder particleBuilder  = new ParticleBuilder(ParticleEffect.REDSTONE, loc);


            task = Bukkit.getScheduler().scheduleSyncRepeatingTask(CloverSpells.getPlugin(), new BukkitRunnable() {
                @Override
                public void run() {

                    if (timer <= 20 && timer != 0) {

                        Utils.barrageSphere(player, loc, move, particle,0.5 + r);
                        Utils.barrageSphere(player, loc, move, particleBuilder,0.5 + r);

                        loc.setX(loc.getX() + loc.getDirection().getX());
                        loc.setY(loc.getY() + loc.getDirection().getY());
                        loc.setZ(loc.getZ() + loc.getDirection().getZ());

                        times--;

                        if (times > 0) {
                            fireBarrage.setTimes(times);
                            fireBarrage.onUseItem(player);
                        }

                        timer--;
                        r += 0.2;

                    } else if (timer == 0) {
                        r = 0;
                        CloverSpells.getPlugin().getServer().getScheduler().cancelTask(task);

                    }

                }
            }, 1, 2);
        }

    private void randomPosition(Location loc) {

        double random = Math.random() * 12;
        double percentual = Math.random() * 100;
        double multiply = 1;

        if (percentual <= 50) {
            multiply = -1;
        }

        loc.setX(loc.getX() + Math.random() * random * multiply);
        loc.setY(loc.getY() + Math.random() * random * multiply);
        loc.setZ(loc.getZ() + 1);
    }


    private void setTimes(int times) {
        this.times = times;
    }
}
