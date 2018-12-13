package me.TrexMX.Teams;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;

public class BossTeam {
    
    private static Player boss;
    private static String name;
    private static Inventory kit;
    private static PotionEffect[] BossEffects;

    
    public static void setBossPlayer(Player p) {
        boss = p;
    }
    
    public static void setName(String name) {
        BossTeam.name = name;
    }

    public static void setKit(Inventory kit) {
        BossTeam.kit = kit;
    }
    
    public static void setBossEffects(PotionEffect[] effects) {
        BossEffects  = effects;
    }
    
    public static Player getBossPlayer() {
        return boss;
    }
    
    public static String getTeamName() {
        return name;
    }
    
    public static Inventory getTeamKit() {
        return kit;
    }
    
    public static PotionEffect[] getBossEffects() {
        return BossEffects;
    }
}
