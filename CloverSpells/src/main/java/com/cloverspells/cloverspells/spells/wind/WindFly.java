package com.cloverspells.cloverspells.spells.wind;

import com.cloverspells.cloverspells.CloverSpells;
import com.cloverspells.cloverspells.enums.SpellType;
import com.cloverspells.cloverspells.references.Spell;
import com.cloverspells.cloverspells.ultils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class WindFly extends Spell {

    private int task;
    private int timer = 30;

    public WindFly(double manaCost, String name, SpellType type, boolean repeatingTask) {
        super(manaCost, name, type, repeatingTask);
    }

    public WindFly() {}

    public void onUseItem(Player player) {
        Location loc = player.getEyeLocation();
        Vector move = loc.getDirection().normalize();

        ParticleBuilder particle = new ParticleBuilder(ParticleEffect.SMOKE_NORMAL, loc);
        player.setAllowFlight(true);

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(CloverSpells.getPlugin(), new Runnable() {
            @Override
            public void run() {

                if (timer <= 30 && timer != 0) {

                    windFlyParticle(loc, move, particle);

                    loc.setX(loc.getX());
                    loc.setY(loc.getY());
                    loc.setZ(loc.getZ());

                    timer--;

                } else if (timer == 0) {
                    CloverSpells.getPlugin().getServer().getScheduler().cancelTask(task);

                }

            }

        },0,1);
    }

    private void windFlyParticle(Location loc, Vector move, ParticleBuilder particle) {

        for (double t = 0; t < Math.PI * 4; t+= Math.PI * 2 / 100) {

            double x,y,z;

            x = Math.cos(t * Math.PI) / 2;
            y = 0;
            z = Math.sin(t * Math.PI) / 2;

            Vector vec = new Vector(x,y,z);

            Utils.rotateAroundAxisX(vec, loc.getPitch());
            Utils.rotateAroundAxisY(vec, -loc.getYaw());


            loc.add(vec);
            loc.add(move.multiply(0));

            particle.display();

            loc.subtract(vec);
            loc.subtract(move.multiply(0));

        }

    }


}
