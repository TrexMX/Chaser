package me.TrexMX.Modules;

import me.TrexMX.Main.Main;

public class ConfigInfo {

	
	//private static String MOTD;
	private static String gameWorldName,lobbyWorldName,spawnLocation,bossTeamName, restTeamName;
	private static int maxPlayers,gameDuration;
	
	
	public static void loadServerInfo() {
		//MOTD = LoadConfig.getConfig().getString("MOTD");
		maxPlayers = LoadConfig.getConfig().getInt("restPlayers") + 1;
		gameWorldName = LoadConfig.getConfig().getString("GameWorld");
		lobbyWorldName = LoadConfig.getConfig().getString("LobbyWorld");
		spawnLocation = LoadConfig.getConfig().getString("spawnLocation");
		gameDuration = LoadConfig.getConfig().getInt("gameDuration");
                bossTeamName = LoadConfig.getConfig().getString("BossTeamName");
                restTeamName = LoadConfig.getConfig().getString("RestPlayersTeamName");
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
        
        public static String getBossTeamName() {
            return bossTeamName;
        }
        
        public static String getRestTeamName() {
            return restTeamName;
        }
}
