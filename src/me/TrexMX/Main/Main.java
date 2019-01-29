package me.TrexMX.Main;

import org.bukkit.plugin.java.JavaPlugin;

import me.TrexMX.Events.EventsListener;
import me.TrexMX.Modules.ArenaInfo;
import me.TrexMX.Modules.ConfigInfo;
import me.TrexMX.Modules.Game;
import me.TrexMX.Modules.KitModule;
import me.TrexMX.Modules.LangInfo;
import me.TrexMX.Modules.LoadConfig;
import me.TrexMX.Modules.PotionEffectModule;
import me.TrexMX.Modules.WorldModule;
import me.TrexMX.Teams.BossTeam;
import me.TrexMX.Teams.RestTeam;

public class Main extends JavaPlugin{

	private static Main instance;
	
	@Override
	public void onEnable() {
		instance = this;
		LoadConfig.loadConfigurationFiles();
		ConfigInfo.loadServerInfo();
		LangInfo.getMessages();
		ArenaInfo.loadArenaInfo();
                PotionEffectModule.loadEffects();
		KitModule.loadKits();
		WorldModule.copyWorlds();
		WorldModule.loadWorlds();
                BossTeam.setName(ConfigInfo.getBossTeamName());
                RestTeam.setName(ConfigInfo.getRestTeamName());
                Game.setWaiting();
		//this.getCommand("/chaser").setExecutor(new ChaserCommand());
		this.getServer().getPluginManager().registerEvents(new EventsListener(), getPlugin(Main.class));
	}
	
	@Override
	public void onDisable() {
		saveConfig();
		WorldModule.deleteWorlds();
	}
	
	public static Main getInstance() {
		return instance;
	}
       
}
