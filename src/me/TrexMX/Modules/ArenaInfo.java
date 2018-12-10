package me.TrexMX.Modules;

import java.util.List;

import me.TrexMX.Main.Main;

public class ArenaInfo {
	
	private static String[] RestPlayerLocations,RestPlayersKit,BossPlayerKit;
	private static String BossPlayerLocation;
	
	public static void loadArenaInfo() {
		loadRestKit();
		loadRestLocations();
		loadBossKit();
		loadBossLocation();
	}
	
	private static void loadRestLocations() {
		List<String> RestLocations = LoadConfig.getArenaConfig().getStringList("restLocations");
		int players= Main.getInstance().getConfig().getInt("restPlayers");
		RestPlayerLocations = new String[players];
		for (int x=0; x<players;x++) {
			RestPlayerLocations[x] = RestLocations.get(x);
		}
	}
	
	private static void loadBossLocation() {
		BossPlayerLocation = LoadConfig.getArenaConfig().getString("bossLocation");
	}
	
	private static void loadRestKit() {
		List<String> RestKit = LoadConfig.getArenaConfig().getStringList("restKit");
		int max= RestKit.size();
		RestPlayersKit = new String[max];
		for (int x=0; x<max;x++) {
			RestPlayersKit[x] = RestKit.get(x);
		}
	}
	
	private static void loadBossKit() {

		List<String> BossKit = LoadConfig.getArenaConfig().getStringList("bossKit");
		int max= BossKit.size();
		BossPlayerKit = new String[max];
		for (int x=0; x<max;x++) {
			BossPlayerKit[x] = BossKit.get(x);
		}
	}
	
	public static String[] getRestPlayersLocations() {
		return RestPlayerLocations;
	}
	
	public static String[] getRestPlayersKit() {
		return RestPlayersKit;
	}
	
	public static String[] getBossPlayerKit() {
		return BossPlayerKit;
	}
	
	public static String getBossLocation() {
		return BossPlayerLocation;
	}
}
