package com.cloverspells.cloverspells.references;

import com.cloverspells.cloverspells.enums.SpellType;
import com.cloverspells.cloverspells.spells.fire.FireBall;
import com.cloverspells.cloverspells.spells.fire.FireBarrage;
import com.cloverspells.cloverspells.spells.fire.FirePilar;
import com.cloverspells.cloverspells.spells.fire.FireSpiral;
import com.cloverspells.cloverspells.spells.time.TimeExplosion;
import com.cloverspells.cloverspells.spells.wind.WindCut;
import com.cloverspells.cloverspells.spells.wind.WindFly;
import com.cloverspells.cloverspells.spells.wind.WindSlay;
import org.bukkit.entity.Player;
import sun.awt.image.OffScreenImageSource;

import java.sql.Time;

public class Spell {
    private double manaCost;
    private String name;
    private SpellType type;
    private boolean repeatingTask;

    public Spell(double manaCost, String name, SpellType type, boolean repeatingTask) {
        this.manaCost = manaCost;
        this.name = name;
        this.type = type;
        this.repeatingTask = repeatingTask;
    }

    public void instance(Player player, Spell spell) {
        if (repeatingTask) {
            if (spell instanceof TimeExplosion) { new TimeExplosion().onUseItem(player); }
            if (spell instanceof FireBall) { new FireBall().onUseItem(player); }
            if (spell instanceof FireBarrage) { new FireBarrage().onUseItem(player); }
            if (spell instanceof WindCut) { new WindCut().onUseItem(player);}
            if (spell instanceof WindSlay) { new WindSlay().onUseItem(player);}
            if (spell instanceof FirePilar) { new FirePilar().onUseItem(player);}
            if (spell instanceof FireSpiral) { new FireSpiral().onUseItem(player);}
            if (spell instanceof WindFly) { new WindFly().onUseItem(player);}



        }
    }

    public boolean getRepeatingTask() {
        return repeatingTask;
    }

    public Spell() { }

    public void onUseItem(Player player){ }


    public double getManaCost() {
        return manaCost;
    }

    public SpellType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setManaCost(double manaCost) {
        this.manaCost = manaCost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(SpellType type) {
        this.type = type;
    }
}
