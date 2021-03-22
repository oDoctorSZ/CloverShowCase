package com.cloverspells.cloverspells.spells.time;

import com.cloverspells.cloverspells.CloverSpells;
import com.cloverspells.cloverspells.enums.SpellType;
import com.cloverspells.cloverspells.ultils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.RegularColor;

import java.util.ArrayList;
import java.util.List;


public class TimeRecover extends TimeSpell {

    public TimeRecover(double manaCost, String name, SpellType type, boolean repeatingTask) {
        super(manaCost, name, type, repeatingTask);
    }

    @Override
    public void onUseItem(Player player) {

        Location loc = player.getLocation();
        Vector move = loc.getDirection().normalize();

        ParticleBuilder particle = new ParticleBuilder(ParticleEffect.REDSTONE, loc).setParticleData(new RegularColor(0,255,255));

        Bukkit.getScheduler().runTask(CloverSpells.getPlugin(), new Runnable() {
            @Override
            public void run() {

                player.setHealth(20);
                player.setFoodLevel(20);

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect " + player.getName() + " clear");

                for (int i = 0; i < 10; i++) {
                    Utils.justSphere(player, loc, move, particle);
                }

                player.sendMessage(Utils.color("&aYour time has been reseted!"));

            }
        });


    }


}
