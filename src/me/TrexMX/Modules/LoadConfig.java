package me.TrexMX.Modules;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.TrexMX.Main.Main;

public class LoadConfig {
	
	
	private static YamlConfiguration arena,lang;
	private static FileConfiguration config;

	
	public static void loadConfigurationFiles() {
		File arenaFile = new File(Main.getInstance().getDataFolder() + File.separator + "arena.yml");
		if (!arenaFile.exists()) {
			Main.getInstance().getDataFolder().mkdirs();
			Main.getInstance().saveResource("arena.yml",false);
		}
		File langFile = new File(Main.getInstance().getDataFolder() + File.separator +"lang.yml");
		if (!langFile.exists()) {
			Main.getInstance().getDataFolder().mkdirs();
			Main.getInstance().saveResource("lang.yml",false);
		}
		
		arena = new YamlConfiguration();
		lang = new YamlConfiguration();
		
		
		
		
		try {
			Main.getInstance().saveDefaultConfig();
			config = Main.getInstance().getConfig();
			arena.load(arenaFile);
			lang.load(langFile);
			Main.getInstance().getLogger().info("Files loaded");
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	

	
	
	public static YamlConfiguration getArenaConfig() {
		return arena;
	}
	
	public static YamlConfiguration getLangConfig() {
		return lang;
	}
	
	public static FileConfiguration getConfig() {
		return config;
	}
	
	
}
