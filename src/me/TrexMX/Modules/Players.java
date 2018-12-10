package me.TrexMX.Modules;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.TrexMX.Main.Main;

public class Players {

	private static Player boss;
	private static ArrayList<Player> restPlayers = new ArrayList<Player>();
	
	public static void setPlayers() {
		setBoss();
		setRest();
		Main.getInstance().getLogger().info("Players set.");
	}
	
	public static void setBoss() {
		boss = Bukkit.getOnlinePlayers().stream().findAny().get();
	}
	
	public static void setRest() {
		
		for(Player players : Bukkit.getOnlinePlayers()) {
			if (!players.equals(boss)) {
				restPlayers.add(players);
			}
		}
	}
	
	public static Player getBossPlayer() {
		return boss;
	}
	
	public static ArrayList<Player> getRestPlayers() {
		return restPlayers;
	}
}
