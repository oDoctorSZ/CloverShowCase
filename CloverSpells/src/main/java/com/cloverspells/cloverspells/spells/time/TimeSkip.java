package com.cloverspells.cloverspells.spells.time;

import com.cloverspells.cloverspells.CloverSpells;
import com.cloverspells.cloverspells.enums.SpellType;
import com.cloverspells.cloverspells.interfaces.OnUseItem;
import com.cloverspells.cloverspells.ultils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.ParticlePacket;
import xyz.xenondevs.particle.data.color.RegularColor;

import java.util.HashMap;


public class TimeSkip extends TimeSpell  {

    public TimeSkip(double manaCost, String name, SpellType type, boolean repeatingTask) {
        super(manaCost, name, type, repeatingTask);
    }

    @Override
    public void onUseItem(Player player) {
        Location loc = player.getTargetBlock(null, 25).getLocation();
        Vector move = player.getLocation().getDirection().normalize();


        ParticleBuilder particle = new ParticleBuilder(ParticleEffect.REDSTONE, loc).setParticleData(new RegularColor(0,255,255));

        if (player.getItemInHand().getType() == Material.STICK) {

            playerLDirec.put(player, loc.getDirection());

            if (!(loc.getBlock().getType() == Material.AIR)) {

                loc.setY(loc.getWorld().getHighestBlockYAt(loc));
                player.teleport(loc);

                for (double t = 0; t <= Math.PI * 8; t += Math.PI * 2 / 100) {

                    double x = Math.cos(t * 4);
                    double y = Math.cos(t * Math.PI * 2 / 2) * Math.sin(t * 8);
                    double z = Math.sin(t * 4);

                    Vector vec = new Vector(x,y,z);

                    Utils.rotateAroundAxisX(vec, player.getEyeLocation().getPitch());
                    Utils.rotateAroundAxisY(vec, -player.getEyeLocation().getYaw());


                    player.getLocation().setDirection(playerLDirec.get(player));


                    loc.add(vec);
                    loc.add(move.multiply(0).setY(1));

                    particle.toPacket();
                    particle.display();

                    loc.subtract(vec);
                    loc.subtract(move.multiply(0).setY(1));

                }




            }


        }

    }

    public HashMap<Player, Vector> playerLDirec = new HashMap<>();
    public HashMap<Player, Float> playerLPitch = new HashMap<>();
}
