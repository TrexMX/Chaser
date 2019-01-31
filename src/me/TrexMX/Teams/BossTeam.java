package me.TrexMX.Teams;

import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;

public class BossTeam {
    
    private static Player boss;
    private static String name;
    private static Inventory kit;
    private static List<PotionEffect> BossEffects;
    private static boolean winners;

    
    public static void setBossPlayer(Player p) {
        boss = p;
    }
    
    public static void setName(String name) {
        BossTeam.name = name;
    }

    public static void setWinners(Boolean trueorfalse) {
        winners = trueorfalse;
    }

        
    public static void setKit(Inventory kit) {
        BossTeam.kit = kit;
    }
    
    public static void setBossEffects(List<PotionEffect> effects) {
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
    
    public static boolean isWinner() {
        return winners;
    } 
    
    public static List<PotionEffect> getBossEffects() {
        return BossEffects;
    }
}
