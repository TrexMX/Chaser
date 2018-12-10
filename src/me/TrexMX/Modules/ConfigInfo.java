package me.TrexMX.Modules;

import me.TrexMX.Main.Main;

public class ConfigInfo {

	
	//private static String MOTD;
	private static String gameWorldName,lobbyWorldName,spawnLocation;
	private static int maxPlayers;
	private static int gameDuration;
	
	
	public static void loadServerInfo() {
		//MOTD = LoadConfig.getConfig().getString("MOTD");
		maxPlayers = LoadConfig.getConfig().getInt("restPlayers") + 1;
		gameWorldName = LoadConfig.getConfig().getString("GameWorld");
		lobbyWorldName = LoadConfig.getConfig().getString("LobbyWorld");
		spawnLocation = LoadConfig.getConfig().getString("spawnLocation");
		gameDuration = LoadConfig.getConfig().getInt("gameDuration");
		Main.getInstance().getLogger().info("Server config loaded");
	}
	
	/*public static String getMOTD() {
		return MOTD;
	}*/
	public static int getMaxPlayers() {
		return maxPlayers;
	}
	
	public static int getGameDuration() {
		return gameDuration;
	}
	
	public static String getGameWorldName() {
		return gameWorldName;
	}
	public static String getLobbyWorldName() {
		return lobbyWorldName;
	}
	public static String getSpawnLocation() {
		return spawnLocation;
	}
}
