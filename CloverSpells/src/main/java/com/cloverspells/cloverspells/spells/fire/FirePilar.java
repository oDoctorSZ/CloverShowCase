package com.cloverspells.cloverspells.spells.fire;

import com.cloverspells.cloverspells.CloverSpells;
import com.cloverspells.cloverspells.enums.SpellType;
import com.cloverspells.cloverspells.references.Spell;
import com.cloverspells.cloverspells.ultils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class FirePilar extends Spell {

    private int task;
    private int timer = 10;
    private int times = 7;


    public FirePilar(double manaCost, String name, SpellType type, boolean repeatingTask) {
        super(manaCost, name, type, repeatingTask);
    }

    public FirePilar() {}

    @Override
    public void onUseItem(Player player) {
        Location loc = player.getLocation();
        Vector move = loc.getDirection().normalize();

        ParticleBuilder particle = new ParticleBuilder(ParticleEffect.FLAME, loc);

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(CloverSpells.getPlugin(), new Runnable() {
            @Override
            public void run() {

                if (timer <= 10 && timer != 0) {

                    circle(player, loc, move, particle, times);

                    times--;
                    timer--;

                } else if (timer == 0) {
                    CloverSpells.getPlugin().getServer().getScheduler().cancelTask(task);

                    for (double t = 0; t<Math.PI*8;t+=Math.PI*2/1000) {
                        double x,y,z;
                        x = Math.sin(t*6*Math.PI)*2;
                        y = t;
                        z = Math.cos(t*6*Math.PI)*2;

                        Vector vec = new Vector(x,y,z);

                        loc.add(vec);
                        loc.add(move.multiply(0));

                        particle.display();

                        loc.getWorld().getNearbyEntities(loc, 2,6,2).forEach(entity -> {
                            if (entity instanceof LivingEntity) {
                                if (entity!=player) {
                                    ((LivingEntity) entity).damage(10, player);
                                    entity.setFireTicks(20*40);
                                    entity.setVelocity(new Vector(entity.getVelocity().getX(), 1, entity.getVelocity().getZ()));
                                }
                            }
                        });

                        loc.subtract(vec);
                        loc.subtract(move.multiply(0));
                    }

                    for (double t = 0; t<Math.PI*8;t+=Math.PI*2/1000) {
                        double x,y,z;
                        x = Math.sin(t*6*Math.PI)*2 / 2;
                        y = t;
                        z = Math.cos(t*6*Math.PI)*2 / 2;

                        Vector vec = new Vector(x,y,z);

                        loc.add(vec);
                        loc.add(move.multiply(0));

                        particle.display();

                        loc.getWorld().getNearbyEntities(loc, 2,6,2).forEach(entity -> {
                            if (entity instanceof LivingEntity) {
                                if (entity!=player) {
                                    entity.setFireTicks(20*40);
                                }
                            }
                        });

                        loc.subtract(vec);
                        loc.subtract(move.multiply(0));
                    }
                }

            }
        },0,0);

    }



    public void circle(Player player, Location loc, Vector move, ParticleBuilder particle, double radius) {
        for (double t = 0; t<Math.PI*8;t+=Math.PI*2/1000) {
            double x,y,z;
            x = Math.cos(t * Math.PI) * radius;
            y = 0;
            z = Math.sin(t * Math.PI) * radius;

            Vector vec = new Vector(x,y,z);

            loc.add(vec);
            loc.add(move.multiply(0));

            particle.display();

            loc.getWorld().getNearbyEntities(loc, 2,6,2).forEach(entity -> {
                if (entity instanceof LivingEntity) {
                    if (entity!=player) {
                        entity.setFireTicks(20*40);
                    }
                }
            });

            loc.subtract(vec);
            loc.subtract(move.multiply(0));
        }
    }
}
