package com.cloverspells.cloverspells.spells.fire;

import com.cloverspells.cloverspells.enums.SpellType;
import com.cloverspells.cloverspells.references.Spell;
import com.cloverspells.cloverspells.ultils.Utils;
import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;


public class FireHeal extends Spell {
    boolean o = false;
    boolean x = true;

    private boolean[][] shape = {
            {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o},
            {o, o, o, x, o, o, o, o, o, o, o, o, x, o, o, o},
            {o, o, x, x, o, o, o, o, o, o, o, o, x, x, o, o},
            {o, x, x, x, x, o, o, o, o, o, o, x, x, x, x, o},
            {o, x, x, x, x, o, o, o, o, o, o, x, x, x, x, o},
            {o, o, x, x, x, x, o, o, o, o, x, x, x, x, o, o},
            {o, o, o, x, x, x, x, o, o, x, x, x, x, o, o, o},
            {o, o, o, o, x, x, x, x, x, x, x, x, o, o, o, o},
            {o, o, o, o, o, x, x, x, x, x, x, o, o, o, o, o},
            {o, o, o, o, o, o, x, x, x, x, o, o, o, o, o, o},
            {o, o, o, o, o, x, x, o, o, x, x, o, o, o, o, o},
            {o, o, o, o, x, x, x, o, o, x, x, x, o, o, o, o},
            {o, o, o, o, x, x, o, o, o, o, x, x, o, o, o, o},
            {o, o, o, o, x, o, o, o, o, o, o, x, o, o, o, o},
            {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o},
    };

    public static Vector getBackVector(Location loc) {
        final float newZ = (float) (loc.getZ() + (1 * Math.sin(Math.toRadians(loc.getYaw() + 90))));
        final float newX = (float) (loc.getX() + (1 * Math.cos(Math.toRadians(loc.getYaw() + 90))));
        return new Vector(newX - loc.getX(), 0, newZ - loc.getZ());
    }


    public FireHeal(double manaCost, String name, SpellType type, boolean repeatingTask) {
        super(manaCost, name, type, repeatingTask);
    }

    @Override
    public void onUseItem(Player player) {
        Location loc = player.getEyeLocation();
        ParticleBuilder particle = new ParticleBuilder(ParticleEffect.FLAME, loc);
        ParticleBuilder particleT = new ParticleBuilder(ParticleEffect.FLAME, loc);

        if (!(player.getHealth() + 10 >= 20)) {
            player.setHealth(player.getHealth() + 10);
        }

        Utils.circleNoInter(loc, particle, 1);
        drawParticles(loc, particleT);

    }

    private void drawParticles(Location location, ParticleBuilder particleBuilder) {
        double space = 0.20;
        double defX = location.getX() - (space * shape[0].length / 2) + space;
        double defZ = location.getZ() - (space * shape[0].length / 2) + space;
        double x = defX;
        double z = defZ;
        double y = location.clone().getY() + 2.8;
        double fire = -((location.getYaw() + 180) / 60);
        fire += (location.getYaw() < -180 ? 3.25 : 2.985);

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j]) {

                    Location target = location.clone();
                    target.setX(x);
                    target.setY(y);
                    target.setZ(z);

                    Vector v = target.toVector().subtract(location.toVector());
                    Vector v2 = getBackVector(location);
                    Utils.rotateAroundAxisY(v, fire);
                    Utils.rotateAroundAxisX(v, -fire);
                    v2.multiply(0).setY(0);

                    location.add(v);
                    location.add(v2);
                    for (int k = 0; k < 3; k++)
                        particleBuilder.display();
                    location.subtract(v2);
                    location.subtract(v);
                }
                x += space;
            }
            y -= space;
            //z -= 0.05f;
            x = defX;
        }
    }
}
