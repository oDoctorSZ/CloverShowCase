package com.cloverspells.cloverspells;

import com.cloverspells.cloverspells.enums.SpellType;
import com.cloverspells.cloverspells.events.InteractEvent;
import com.cloverspells.cloverspells.events.PJoinEvent;
import com.cloverspells.cloverspells.events.PMoveEvent;
import com.cloverspells.cloverspells.events.PQuitEvent;
import com.cloverspells.cloverspells.spells.Mago;
import com.cloverspells.cloverspells.ultils.Utils;
import com.rededark.clovercore.CloverCore;
import com.rededark.clovercore.controller.CloverController;
import com.rededark.clovercore.pojo.Mage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

public final class CloverSpells extends JavaPlugin {

    private static Logger logger;

    public HashMap<UUID, SpellType> spells = new HashMap<>();

    public static String grimorio;

    public CloverSpells getInstance() {
        return this;
    }


    @Override
    public void onEnable() {
        logger = getLogger();
        Bukkit.getPluginManager().registerEvents(new InteractEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PMoveEvent(), this);
        Bukkit.getPluginManager().registerEvents(new Mago(), this);
        Bukkit.getPluginManager().registerEvents(new PJoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PQuitEvent(), this);


        Utils.initializeObserver();
        Utils.registerSpells();
        Utils.npcObserver();
        grimorio = getConfig().getString("players.player.grimorio");
        saveConfig();
    }

    @Override
    public void onDisable() {  }

    ////////////////// GetPlugin() and Logger //////////////////
    public static CloverSpells getPlugin() {
        return getPlugin(CloverSpells.class);
    }
    public static Logger getPluginLogger() {
        return logger;
    }


}
