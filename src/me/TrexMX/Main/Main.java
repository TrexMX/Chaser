package me.TrexMX.Main;

import org.bukkit.plugin.java.JavaPlugin;

import me.TrexMX.Commands.ChaserCommand;
import me.TrexMX.Events.EventsListener;
import me.TrexMX.Modules.ArenaInfo;
import me.TrexMX.Modules.ConfigInfo;
import me.TrexMX.Modules.KitModule;
import me.TrexMX.Modules.LangInfo;
import me.TrexMX.Modules.LoadConfig;
import me.TrexMX.Modules.WorldModule;

public class Main extends JavaPlugin{

	private static Main instance;
	
	@Override
	public void onEnable() {
		EventsListener events = new EventsListener();
		instance = this;
		LoadConfig.loadConfigurationFiles();
		ConfigInfo.loadServerInfo();
		LangInfo.getMessages();
		ArenaInfo.loadArenaInfo();
		//WorldModule.copyWorlds();
		//WorldModule.loadWorlds();
		KitModule.loadKits();
		this.getCommand("/chaser").setExecutor(new ChaserCommand());
		this.getServer().getPluginManager().registerEvents(events, this);

	}
	
	@Override
	public void onDisable() {
		saveConfig();
		//WorldModule.deleteWorlds();
	}
	
	public static Main getInstance() {
		return instance;
	}
	
}
