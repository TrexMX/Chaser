package me.TrexMX.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

import me.TrexMX.Modules.ConfigInfo;
import me.TrexMX.Modules.LangInfo;
import me.TrexMX.Modules.Sounds;
import me.TrexMX.Modules.Game;
import me.TrexMX.Modules.GameState;
import me.TrexMX.Teams.BossTeam;
import me.TrexMX.Teams.RestTeam;

public class EventsListener implements Listener {
	
	
	@EventHandler
	public void serverPing(ServerListPingEvent e) {
            e.setMotd(Game.getGameState().toString());
            e.setMaxPlayers(ConfigInfo.getMaxPlayers());
	}
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
            Player player = e.getPlayer();
            if (Game.getGameState() == GameState.STARTING && player.hasPermission("*")) {
                    player.kickPlayer(Game.getGameState().toString());
            }
            String variables[] = {"%p%","%cp%","%max%"};
            String replace[] = {player.getName(),String.valueOf(Bukkit.getOnlinePlayers().size()),String.valueOf(ConfigInfo.getMaxPlayers())};
            Bukkit.broadcastMessage(LangInfo.replaceVariables(LangInfo.join_message, variables, replace));
		
		

	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
            Player player = e.getPlayer();
            String variables[] = {"%p%","%cp%","%max%"};
            String replace[] = {player.getName(),String.valueOf(Bukkit.getOnlinePlayers().size()),String.valueOf(ConfigInfo.getMaxPlayers())};
            Bukkit.broadcastMessage(LangInfo.replaceVariables(LangInfo.left_message, variables, replace));
		
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
            Player deadPlayer = e.getEntity();
            if (deadPlayer.equals(BossTeam.getBossPlayer())) {
                    BossPlayerDeath event = new BossPlayerDeath(deadPlayer, deadPlayer.getKiller());
                    Bukkit.getServer().getPluginManager().callEvent(event);
            }
	}
	
	@EventHandler
	public void onBossDeath(BossPlayerDeath e) {
            RestTeam.setWinners(true);
            BossTeam.setWinners(false);
            Game.endGame();
	}
}
