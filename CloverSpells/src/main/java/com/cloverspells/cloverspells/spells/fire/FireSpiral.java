package com.cloverspells.cloverspells.spells.fire;

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

public class FireSpiral extends Spell {

    private int task;
    private int timer = 10;
    private int times = 7;


    public FireSpiral(double manaCost, String name, SpellType type, boolean repeatingTask) {
        super(manaCost, name, type, repeatingTask);
    }

    public FireSpiral() {}


    @Override
    public void onUseItem(Player player) {
        Location loc = player.getEyeLocation();
        Vector move = loc.getDirection().normalize();

        ParticleBuilder particle = new ParticleBuilder(ParticleEffect.FLAME, loc);

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(CloverSpells.getPlugin(), new Runnable() {
            @Override
            public void run() {

                if (timer <= 10 && timer != 0) {

                    circleY(player, loc, move, particle, times);


                    timer--;
                    times--;

                } else if (timer == 0) {

                    CloverSpells.getPlugin().getServer().getScheduler().cancelTask(task);

                    for (double t = 0; t<Math.PI*8;t+=Math.PI*2/1000) {
                        double x,y,z;
                        double r = 1;
                        x = r*t*Math.sin(t*4)/2;
                        y = r*t*Math.cos(t*4)/2;
                        z = r*t/2;
                        r+=0.2;
                        Vector vec = new Vector(x,y,z);
                        Utils.rotateAroundAxisX(vec, loc.getPitch());
                        Utils.rotateAroundAxisY(vec, -loc.getYaw());

                        loc.add(vec);
                        loc.add(move.multiply(1));

                        particle.display();

                        loc.getWorld().getNearbyEntities(loc, 3+r,3+r,3+r).forEach(entity -> {
                            if (entity instanceof LivingEntity) {
                                if (entity != player) {
                                    ((LivingEntity) entity).damage(10, player);
                                    entity.setFireTicks(20*10);
                                }
                            }
                        });

                        loc.subtract(vec);
                        loc.subtract(move.multiply(1));
                    }

                }
                

                }
            },0,0);
    }

    public void circleY(Player player, Location loc, Vector move, ParticleBuilder particle, double radius) {
        for (double t = 0; t<Math.PI*8;t+=Math.PI*2/1000) {
            double x,y,z;

            x = Math.cos(t * Math.PI) * radius;
            y = Math.sin(t * Math.PI) * radius;
            z = 0;

            Vector vec = new Vector(x,y,z);

            Utils.rotateAroundAxisX(vec, loc.getPitch());
            Utils.rotateAroundAxisY(vec, -loc.getYaw());

            loc.add(vec);
            loc.add(move.multiply(1));

            particle.display();

            loc.subtract(vec);
            loc.subtract(move.multiply(1));
        }
    }
}
