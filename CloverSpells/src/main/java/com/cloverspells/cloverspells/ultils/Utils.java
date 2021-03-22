package com.cloverspells.cloverspells.ultils;

import com.cloverspells.cloverspells.CloverSpells;
import com.cloverspells.cloverspells.enums.SpellType;
import com.cloverspells.cloverspells.manager.SpellManager;
import com.cloverspells.cloverspells.references.Spell;
import com.cloverspells.cloverspells.spells.fire.*;
import com.cloverspells.cloverspells.spells.time.*;
import com.cloverspells.cloverspells.spells.wind.WindCut;
import com.cloverspells.cloverspells.spells.wind.WindFly;
import com.cloverspells.cloverspells.spells.wind.WindSlay;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.RegularColor;

import java.util.logging.Logger;

public class Utils {

    private static Logger logger = CloverSpells.getPluginLogger();

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String decolor(String text) {
        return ChatColor.stripColor(text);
    }

    public static String recolor(String text) {
        return text.replace(ChatColor.COLOR_CHAR, '&');
    }

    public static void log(String... menssagem) {
        for (String menssage : menssagem) {
            logger.info(menssage);
        }
    }

    public static void warn(String... menssagem) {
        for (String menssage : menssagem) {
            logger.warning(menssage);
        }
    }

    public static void error(String... menssagem) {
        for (String menssage : menssagem) {
            logger.severe(menssage);
        }
    }

    public static Vector rotateAroundAxisX(Vector v, double angle) {
        angle = Math.toRadians(angle);
        double y, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        y = v.getY() * cos - v.getZ() * sin;
        z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }

    public static Vector rotateAroundAxisY(Vector v, double angle) {
        angle = Math.toRadians(angle);
        double x, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos + v.getZ() * sin;
        z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }


    public static void initializeObserver() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(CloverSpells.getPlugin(), new Runnable() {
            @Override
            public void run() {
                if (TimeStop.getEntities("player") != null) {
                    TimeStop.getEntities("player").forEach(livingEntity -> {
                        livingEntity.setVelocity(new Vector(0, 0, 0));
                        Location loc = livingEntity.getLocation();
                        ParticleBuilder particle = new ParticleBuilder(ParticleEffect.REDSTONE, loc).setParticleData(new RegularColor(0, 255, 255));

                        Vector vec = loc.getDirection().normalize();

                        for (double theta = 0; theta <= Math.PI * 16; theta = theta + Math.PI * 2 / 100) {

                            double x = Math.sin(theta / 10 * Math.PI);
                            double y = Math.sin((theta % 10) * Math.PI * 4) * Math.cos(theta / 10 * Math.PI);
                            double z = Math.cos((theta % 10) * Math.PI * 4) * Math.cos(theta / 10 * Math.PI);

                            loc.add(x, y, z);
                            loc.add(vec.multiply(0).setY(1));

                            particle.display();

                            loc.subtract(x, y, z);
                            loc.subtract(vec.multiply(0).setY(1));
                        }

                        ParticleBuilder particleCircle = new ParticleBuilder(ParticleEffect.REDSTONE, loc).setParticleData(new RegularColor(138, 138, 138));


                        for (double theta = 0; theta <= Math.PI * 2; theta = theta + Math.PI / 100) {
                            double r = 1.5;

                            double x = r * Math.cos(theta);
                            double y = 1;
                            double z = r * Math.sin(theta);

                            loc.add(x, y, z);

                            particleCircle.display();

                            loc.subtract(x, y, z);
                        }

                    });
                }
            }
        }, 0, 10);
    }

    public static void registerSpells() {
        Spell[] spellsTime = {
                new TimeExplosion(10, "§6§lExplosion!", SpellType.TIME, true),
                new TimeStop(10, "§4§lTime Breaker!", SpellType.TIME, false),
                new TimeSkip(10, "§b§lSkip!", SpellType.TIME, false),
                new TimeStrike(10, "§a§lStrike!", SpellType.TIME, false),
                new TimeRecover(10, "§8§lRecover!", SpellType.TIME, false)
        };

        //MAGIAS GRIMÓRIO DE FOGO
        Spell[] spellsFire = {
                new FireBall(10, "§c§lFire Ball", SpellType.FIRE, true),
                new FireSpiral(10, "§e§lFire Spiral", SpellType.FIRE, true),
                new FirePilar(10, "§4§lFire Pilar", SpellType.FIRE, true),
                new FireBarrage(10, "§b§lFire Barrage", SpellType.FIRE, true),
                new FireHeal(10, "§6§lFire Heal", SpellType.FIRE, false)
        };

        //MAGIAS GRIMÓRIO DO VENTO
        Spell[] spellsWind = {
                new WindCut(10, "§a§lWind Cut", SpellType.WIND, true),
                new WindSlay(10, "§b§lWind Slay", SpellType.WIND, true),
                new WindFly(10, "§6§lWind Fly", SpellType.WIND, true),

        };

        SpellManager.AllSpellsByType.put(SpellType.FIRE, spellsFire);
        SpellManager.AllSpellsByType.put(SpellType.TIME, spellsTime);
        SpellManager.AllSpellsByType.put(SpellType.WIND, spellsWind);

        //GRIMÓRIO DE TEMPO
        SpellManager.allSpells.add(new TimeExplosion(10, "§6§lExplosion!", SpellType.TIME, true));
        SpellManager.allSpells.add(new TimeStop(10, "§4§lTime Breaker!", SpellType.TIME, false));
        SpellManager.allSpells.add(new TimeSkip(10, "§b§lSkip!", SpellType.TIME, false));
        SpellManager.allSpells.add(new TimeStrike(10, "§a§lStrike!", SpellType.TIME, false));
        SpellManager.allSpells.add(new TimeRecover(10, "§8§lRecover!", SpellType.TIME, false));

        //GRIMÓRIO DE FOGO
        SpellManager.allSpells.add(new FireBall(10, "§c§lFire Ball", SpellType.FIRE, true));
        SpellManager.allSpells.add(new FireSpiral(10, "§e§lFire Spiral", SpellType.FIRE, true));
        SpellManager.allSpells.add(new FirePilar(10, "§4§lFire Pilar", SpellType.FIRE, true));
        SpellManager.allSpells.add(new FireHeal(10, "§6§lFire Heal", SpellType.FIRE, false));
        SpellManager.allSpells.add(new FireBarrage(10, "§b§lFire Barrage", SpellType.FIRE, true));

        //GRIMORIO DO VENTO
        SpellManager.allSpells.add(new WindCut(10, "§a§lWind Cut", SpellType.WIND, true));
        SpellManager.allSpells.add(new WindSlay(10, "§b§lWind Slay", SpellType.WIND, true));
        SpellManager.allSpells.add(new WindFly(10, "§6§lWind Fly", SpellType.WIND, true));

    }


    public static void sphere(Player player, Location loc, Vector move, ParticleBuilder particle, boolean addCircle) {

        Bukkit.getScheduler().runTask(CloverSpells.getPlugin(), new Runnable() {
            @Override
            public void run() {
                for (double t = 0; t < Math.PI * 8; t += Math.PI * 2 / 70) {

                    double x, y, z;

                    x = Math.sin(t / 10 * Math.PI);
                    y = Math.sin((t % 10) * Math.PI * 4) * Math.cos(t / 10 * Math.PI);
                    z = Math.cos((t % 10) * Math.PI * 4) * Math.cos(t / 10 * Math.PI);

                    Vector vec = new Vector(x, y, z);

                    Utils.rotateAroundAxisX(vec, player.getEyeLocation().getPitch());
                    Utils.rotateAroundAxisY(vec, -player.getEyeLocation().getYaw());

                    loc.add(vec);
                    loc.add(move.multiply(1));

                    particle.display();

                    if (addCircle) {
                        loc.getWorld().getNearbyEntities(loc, 2, 2, 2).forEach(entity -> {
                            if (entity instanceof LivingEntity) {
                                ((LivingEntity) entity).damage(5, player);

                                if (entity != player) {
                                    entity.setVelocity(new Vector(entity.getVelocity().getX(),
                                            1,
                                            entity.getVelocity().getZ()));
                                }

                            }
                        });
                    } else {
                        loc.getWorld().getNearbyEntities(loc, 2, 2, 2).forEach(entity -> {
                            if (entity instanceof LivingEntity) {

                                if (entity != player) {
                                    ((LivingEntity) entity).damage(5, player);
                                    entity.setFireTicks(20 * 10);
                                }

                            }
                        });
                    }


                    loc.subtract(vec);
                    loc.subtract(move.multiply(1));

                }

                if (addCircle) {
                    ParticleBuilder particleCircle = new ParticleBuilder(ParticleEffect.REDSTONE, loc).setParticleData(new RegularColor(138, 138, 138));

                    for (double theta = 0; theta <= Math.PI * 2; theta = theta + Math.PI / 70) {
                        double r = 1.5;

                        double x = r * Math.cos(theta);
                        double y = 0;
                        double z = r * Math.sin(theta);

                        loc.add(x, y, z);

                        particleCircle.display();

                        loc.subtract(x, y, z);
                    }
                }

            }
        });

    }

    public static void justSphere(Player player, Location loc, Vector move, ParticleBuilder particle) {

        Bukkit.getScheduler().runTask(CloverSpells.getPlugin(), new Runnable() {
            @Override
            public void run() {
                for (double t = 0; t < Math.PI * 8; t += Math.PI * 2 / 70) {

                    double x, y, z;

                    x = Math.sin(t / 10 * Math.PI);
                    y = Math.sin((t % 10) * Math.PI * 4) * Math.cos(t / 10 * Math.PI);
                    z = Math.cos((t % 10) * Math.PI * 4) * Math.cos(t / 10 * Math.PI);

                    Vector vec = new Vector(x, y, z);

                    Utils.rotateAroundAxisX(vec, player.getEyeLocation().getPitch());
                    Utils.rotateAroundAxisY(vec, -player.getEyeLocation().getYaw());

                    loc.add(vec);
                    loc.add(move.multiply(0).setY(1));

                    particle.display();

                    loc.subtract(vec);
                    loc.subtract(move.multiply(0).setY(1));

                }

                ParticleBuilder particleCircle = new ParticleBuilder(ParticleEffect.REDSTONE, loc).setParticleData(new RegularColor(138, 138, 138));


                for (double theta = 0; theta <= Math.PI * 2; theta = theta + Math.PI / 70) {
                    double r = 1.5;

                    double x = r * Math.cos(theta);
                    double y = 0;
                    double z = r * Math.sin(theta);

                    loc.add(x, y, z);
                    loc.add(move.multiply(0).setY(1));

                    particleCircle.display();

                    loc.subtract(x, y, z);
                    loc.subtract(move.multiply(0).setY(1));
                }
            }
        });

    }


    public static void npcObserver() {

        Bukkit.getScheduler().scheduleSyncRepeatingTask(CloverSpells.getPlugin(), new Runnable() {
            @Override
            public void run() {

                if (TimeStop.getEntities("npc") != null) {
                    TimeStop.getEntities("npc").forEach(livingEntity -> {
                        livingEntity.setVelocity(new Vector(0, 0, 0));
                    });
                }

            }

        }, 0, 0);
    }

    public static void barrageSphere(Player player, Location loc, Vector move, ParticleBuilder particle, double radius) {
        Bukkit.getScheduler().runTask(CloverSpells.getPlugin(), new Runnable() {
            @Override
            public void run() {

                    for (double t = 0; t < Math.PI * 8; t += Math.PI * 2 / 70) {

                        double x, y, z;

                        x = Math.sin(t / 10 * Math.PI);
                        y = Math.sin((t % 10) * Math.PI * 4) * Math.cos(t / 10 * Math.PI);
                        z = Math.cos((t % 10) * Math.PI * 4) * Math.cos(t / 10 * Math.PI);

                        Vector vec = new Vector(x, y, z);

                        Utils.rotateAroundAxisX(vec, player.getEyeLocation().getPitch());
                        Utils.rotateAroundAxisY(vec, -player.getEyeLocation().getYaw());

                        loc.add(vec);
                        loc.add(move.multiply(0));


                        particle.display();

                        loc.getWorld().getNearbyEntities(loc, 2, 2, 2).forEach(entity -> {
                            if (entity instanceof LivingEntity) {

                                if (entity != player) {
                                    ((LivingEntity) entity).damage(5, player);
                                    entity.setFireTicks(20 * 10);
                                }

                            }
                        });


                        loc.subtract(vec);
                        loc.subtract(move.multiply(0));


                }

            }
        });

    }

    public static void circleNoInter(Location loc, ParticleBuilder particleCircle, double height) {

        for (double theta = 0; theta <= Math.PI * 2; theta = theta + Math.PI / 70) {
            double r = 1.5;

            double x = r * Math.cos(theta);
            double y = height;
            double z = r * Math.sin(theta);

            loc.add(x, y, z);

            particleCircle.display();

            loc.subtract(x, y, z);
        }
    }

}

