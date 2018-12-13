package me.TrexMX.Teams;

import java.util.ArrayList;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class RestTeam {
    
    static ArrayList<Player> players;
    static String name;
    static Inventory kit;
    
    public static void setPlayers(ArrayList<Player> ps) {
       players = ps;
    }
    
    public static void setName(String name) {
        RestTeam.name = name;
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
    
    public static Inventory getTeamKit() {
        return kit;
    }
}
