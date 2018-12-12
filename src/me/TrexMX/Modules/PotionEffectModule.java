package me.TrexMX.Modules;

import me.TrexMX.Main.Main;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionEffectModule {
    
    public static PotionEffect[] BossEffects;
    
    public static void loadEffects() {
        int index = 0;
        BossEffects = new PotionEffect[ArenaInfo.getBossEffects().length];
        for (String effects : ArenaInfo.getBossEffects()) {
            String[] effect = new String[3];
            effect = effects.split(";");
            BossEffects[index] = new PotionEffect(PotionEffectType.getByName(effect[0]), Integer.parseInt(effect[1]),
                    Integer.parseInt(effect[2]));
            index++;    
        }
        Main.getInstance().getLogger().info("Potion Effect Loaded");
    }
    
    public static PotionEffect[] getBossPotionEffects() {
        return BossEffects;
    }
}
