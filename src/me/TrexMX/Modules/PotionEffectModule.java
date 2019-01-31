package me.TrexMX.Modules;

import java.util.ArrayList;
import java.util.List;
import me.TrexMX.Main.Main;
import me.TrexMX.Teams.BossTeam;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionEffectModule {
    
    public static List<PotionEffect> BossEffects;
    
    public static void loadEffects() {
        BossEffects = new ArrayList<>();
        for (int x=0; x < ArenaInfo.getBossEffects().length; x++) {
            String[] effect = new String[3];
            effect = ArenaInfo.getBossEffects()[x].split(";");
            Main.getInstance().getLogger().info(ArenaInfo.getBossEffects()[x]);
            
            BossEffects.add(new PotionEffect(
                    PotionEffectType.getByName(effect[0]), 
                    Integer.parseInt(effect[1])*20,
                    Integer.parseInt(effect[2])));
        }
        BossTeam.setBossEffects(BossEffects);
        
        Main.getInstance().getLogger().info("Potion Effect Loaded");
    }
    
}
