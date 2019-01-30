package me.TrexMX.Main;

import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

import me.TrexMX.Commands.ChaserCommand;
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
            // Declara la instancia para poder acceder a ella desde otras clases
		instance = this;
                // Carga los archivos de configuración aka arena.yml,config.yml,lang.yml
		LoadConfig.loadConfigurationFiles();
                // Carga cada uno de los archivos, o sea, recupera la informaciók
		ConfigInfo.loadServerInfo();
		LangInfo.getMessages();
		ArenaInfo.loadArenaInfo();
                // Carga los efectos que serán añadidos
                PotionEffectModule.loadEffects();
                // Carga kits
		KitModule.loadKits();
                // Copia los mundos que estén en la carpeta Chaser/worlds/
		WorldModule.copyWorlds();
                // Carga los mundos
		WorldModule.loadWorlds();
                // Obtiene los nombres de la configuración
                BossTeam.setName(ConfigInfo.getBossTeamName());
                RestTeam.setName(ConfigInfo.getRestTeamName());
                // Se pone el servidor en modo "esperando"
                Game.setWaiting();
                // Registra comandos y eventos
		this.getCommand("chaser").setExecutor(new ChaserCommand());
		this.getServer().getPluginManager().registerEvents(new EventsListener(), getPlugin(Main.class));
        }
	
	@Override
	public void onDisable() {
		saveConfig();
                // Elimina mundos usados
		WorldModule.deleteWorlds();
	}
	
	public static Main getInstance() {
		return instance;
	}
       
}
