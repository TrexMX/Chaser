package me.TrexMX.Modules;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

import me.TrexMX.Main.Main;

public class WorldModule {
	
	private static World gameWorld, lobbyWorld;
	
	public static void loadWorlds() {
		gameWorld = new WorldCreator(ConfigInfo.getGameWorldName()).type(WorldType.NORMAL).createWorld();
		lobbyWorld = new WorldCreator(ConfigInfo.getLobbyWorldName()).type(WorldType.NORMAL).createWorld();
		Main.getInstance().getLogger().info("Mundos cargados");
	}
	
	public static World getGameWorld() { 
		return gameWorld;
	}
	
	public static void copyWorlds() {
		
		File srcGameW = new File(Main.getInstance().getDataFolder().getAbsolutePath() + File.separator +ConfigInfo.getGameWorldName() + File.separator);
		File srcLobbyW = new File(Main.getInstance().getDataFolder().getAbsolutePath() + File.separator +ConfigInfo.getLobbyWorldName() + File.separator);
		File srcWorlds = Bukkit.getWorldContainer().getAbsoluteFile();
		
		try {
			FileUtils.copyDirectory(srcGameW, srcWorlds);
		} catch (IOException e) {
			e.printStackTrace();
		}
                
            try {
                FileUtils.copyDirectory(srcLobbyW, srcWorlds);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Main.getInstance().getLogger().info("Mundos cargaos");
	}
	
	public static void deleteWorlds() {

		File srcGameW = new File(Bukkit.getWorldContainer().getAbsoluteFile() + File.separator +ConfigInfo.getGameWorldName());
		File srcLobbyW = new File(Bukkit.getWorldContainer().getAbsoluteFile() + File.separator +ConfigInfo.getLobbyWorldName());
		Bukkit.unloadWorld(ConfigInfo.getGameWorldName(), true);
		Bukkit.unloadWorld(ConfigInfo.getLobbyWorldName(), true);
		
		try {
			FileUtils.deleteDirectory(srcGameW);
			FileUtils.deleteDirectory(srcLobbyW);
			Main.getInstance().getLogger().info("Mundos eliminados");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static World getLobbyWorld() { 
		return lobbyWorld;
	}
}
