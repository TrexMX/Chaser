package me.TrexMX.Modules;

import me.TrexMX.Main.Main;
import me.TrexMX.Teams.BossTeam;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionEffectModule {
    
    public static PotionEffect[] BossEffects;
    
    public static void loadEffects() {
        int howmany = 0;
        for (String effects : ArenaInfo.getBossEffects()) {
            String[] effect = new String[3];
            effect = effects.split(";");
            BossEffects[howmany] = new PotionEffect(
                    PotionEffectType.getByName(effect[0]), 
                    Integer.parseInt(effect[1]),
                    Integer.parseInt(effect[2]));
            howmany++;    
        }
        BossTeam.setBossEffects(BossEffects);
        Main.getInstance().getLogger().info("Potion Effect Loaded");
    }
    
}
