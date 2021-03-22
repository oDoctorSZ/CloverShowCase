package com.cloverspells.cloverspells.spells.wind;

import com.cloverspells.cloverspells.CloverSpells;
import com.cloverspells.cloverspells.enums.SpellType;
import com.cloverspells.cloverspells.references.Spell;
import com.cloverspells.cloverspells.ultils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class WindSlay extends Spell {

    private int task;
    private int timer = 20;

    public WindSlay(double manaCost, String name, SpellType type, boolean repeatingTask) {
        super(manaCost, name, type, repeatingTask);
    }

    public WindSlay() {}

    public void onUseItem(Player player) {
        Location loc = player.getEyeLocation();
        Vector move = loc.getDirection().normalize();

        ParticleBuilder particle = new ParticleBuilder(ParticleEffect.SMOKE_NORMAL, loc);


        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(CloverSpells.getPlugin(), new Runnable() {
            @Override
            public void run() {

                if (timer <= 20 && timer != 0) {

                    windSlay(player, loc, move, particle, -2);
                    windSlay(player, loc, move, particle, 0);
                    windSlay(player, loc, move, particle, 2);

                    loc.setX(loc.getX() + loc.getDirection().getX());
                    loc.setY(loc.getY() + loc.getDirection().getY());
                    loc.setZ(loc.getZ() + loc.getDirection().getZ());


                    timer--;

                } else if (timer == 0) {
                    CloverSpells.getPlugin().getServer().getScheduler().cancelTask(task);
                }

            }
        },0,0);

    }

    private void windSlay(Player player, Location loc, Vector move, ParticleBuilder particle, double distance) {

            for (double t = 0; t < Math.PI * 4; t+= Math.PI * 2/ 100) {
                double x,y,z;

                x = distance;
                y = Math.sin(Math.PI  * t / 2) * 6 / 2;
                z = Math.cos(Math.PI * t) * 3 / 2;

                Vector vec = new Vector(x,y,z);

                Utils.rotateAroundAxisX(vec, loc.getPitch());
                Utils.rotateAroundAxisY(vec, -loc.getYaw());


                loc.add(vec);
                loc.add(move.multiply(1).setY(1));

                particle.display();
                loc.getWorld().getNearbyEntities(loc, 1, 4, 1).forEach(entity -> {
                    if (entity instanceof LivingEntity) {

                        if (entity != player) {
                            ((LivingEntity) entity).damage(5, player);
                        }

                    }
                });

                loc.subtract(vec);
                loc.subtract(move.multiply(1).setY(1));

        }

    }

}
