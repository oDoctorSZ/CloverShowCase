package com.cloverspells.cloverspells.spells.time;

import com.cloverspells.cloverspells.CloverSpells;
import com.cloverspells.cloverspells.enums.SpellType;
import com.cloverspells.cloverspells.ultils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.RegularColor;

public class TimeStrike extends TimeSpell {

    public TimeStrike(double manaCost, String name, SpellType type, boolean repeatingTask) {
        super(manaCost, name, type, repeatingTask);
    }

    @Override
    public void onUseItem(Player player) {

        Location loc = player.getEyeLocation();
        Vector move = loc.getDirection().normalize();

        ParticleBuilder particle = new ParticleBuilder(ParticleEffect.REDSTONE, loc).setParticleData(new RegularColor(0, 255, 255));
        ParticleBuilder particleGray = new ParticleBuilder(ParticleEffect.REDSTONE, loc).setParticleData(new RegularColor(138, 138, 138));

        if (player.getItemInHand().getType() == Material.STICK) {

            Bukkit.getScheduler().runTask(CloverSpells.getPlugin(), new Runnable() {
                @Override
                public void run() {

                    for (int v = 0; v < 3; v++) {
                        for (int i = 0; i < 3; i++) {

                            for (double t = 0; t < Math.PI * 16; t += Math.PI * 2 / 20) {

                                double x, y, z;

                                x = Math.sin(t * Math.PI * 6) * Math.sin(t * 2) * 2;
                                y = Math.cos(t * Math.PI * 6) * 2;
                                z = 1;

                                Vector vec = new Vector(x, y, z);

                                Utils.rotateAroundAxisX(vec, player.getEyeLocation().getPitch());
                                Utils.rotateAroundAxisY(vec, -player.getEyeLocation().getYaw());

                                loc.add(vec);
                                loc.add(move.multiply(1));

                                particle.display();

                                loc.subtract(vec);
                                loc.subtract(move.multiply(1));

                            }

                            }

                            for (int j = 0; j < 3; j++) {

                                for (double t = 0; t < Math.PI * 4; t += Math.PI * 2 / 40) {

                                    double x, y, z;

                                    x = Math.sin(t * Math.PI * 6) * 3;
                                    y = Math.cos(t * Math.PI * 6) * 3;
                                    z = j;

                                    Vector vec = new Vector(x, y, z);

                                    Utils.rotateAroundAxisX(vec, player.getEyeLocation().getPitch());
                                    Utils.rotateAroundAxisY(vec, -player.getEyeLocation().getYaw());

                                    loc.add(vec);
                                    loc.add(move.multiply(1));

                                    particleGray.display();

                                    loc.subtract(vec);
                                    loc.subtract(move.multiply(1));

                                }
                            }

                            timeNCircles(player, loc, particleGray, 6);

                            timeNCircles(player, loc, particleGray, 18);


                            for (double t = 0; t < Math.PI * 8; t += Math.PI * 2 / 220) {

                                double x, y, z;

                                x = Math.sin(t * Math.PI * 6) * 2;
                                y = Math.cos(t * Math.PI * 6) * 2;
                                z = t + 1;

                                Vector vec = new Vector(x, y, z);

                                Utils.rotateAroundAxisX(vec, player.getEyeLocation().getPitch());
                                Utils.rotateAroundAxisY(vec, -player.getEyeLocation().getYaw());

                                loc.add(vec);
                                loc.add(move.multiply(1));
                                loc.getWorld().getNearbyEntities(loc, 2,3,2).forEach(entity -> {
                                    if (entity instanceof LivingEntity) {
                                        if (entity != player) {
                                            ((LivingEntity) entity).damage(5, player);
                                        }

                                    }
                                });

                                particle.display();

                                loc.subtract(vec);
                                loc.subtract(move.multiply(1));

                        }

                    }


                }
            });


            }



    }

    private void timeNCircles(Player player, Location loc, ParticleBuilder particle, double distance) {

        Bukkit.getScheduler().runTask(CloverSpells.getPlugin(), new Runnable() {
            @Override
            public void run() {

                for (double t = 0; t < Math.PI * 8; t += Math.PI * 2 / 70) {
                    Vector move = loc.getDirection().normalize();

                    double x, y, z;

                    x = Math.sin(t * Math.PI * 6) * 3;
                    y = Math.cos(t * Math.PI * 6) * 3;
                    z = distance + Math.cos(t * Math.PI * 6) * 3;

                    Vector vec = new Vector(x, y, z);

                    Utils.rotateAroundAxisX(vec, player.getEyeLocation().getPitch());
                    Utils.rotateAroundAxisY(vec, -player.getEyeLocation().getYaw());

                    loc.add(vec);
                    loc.add(move.multiply(1));

                    particle.display();

                    loc.subtract(vec);
                    loc.subtract(move.multiply(1));

                }

            }
        });


            }

}
