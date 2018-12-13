package me.TrexMX.Teams;

import java.util.ArrayList;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class RestTeam {
    
    private static ArrayList<Player> players;
    private static String name;
    private static Inventory kit;
    private static boolean winners;
    
    public static void setPlayers(ArrayList<Player> ps) {
       players = ps;
    }
    
    public static void setName(String name) {
        RestTeam.name = name;
    }
    
    public static void setWinners(Boolean trueorfalse) {
        winners = trueorfalse;
    }

    public static void setKit(Inventory kit) {
        RestTeam.kit = kit;
    }
    
    public static ArrayList<Player> getPlayers() {
        return players;
    }
    
    public static String getTeamName() {
        return name;
    }
    
    public static boolean isWinner() {
        return winners;
    } 
    
    public static Inventory getTeamKit() {
        return kit;
    }
}
