package me.TrexMX.Events;

import me.TrexMX.Main.Main;
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
import me.TrexMX.Modules.Game;
import me.TrexMX.Modules.GameState;
import me.TrexMX.Modules.WorldModule;
import me.TrexMX.Teams.BossTeam;
import me.TrexMX.Teams.RestTeam;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

public class EventsListener implements Listener {
	
        
	
	@EventHandler
	public void serverPing(ServerListPingEvent e) {
            e.setMotd(Game.getGameState().toString());
            e.setMaxPlayers(ConfigInfo.getMaxPlayers());
	}
        
	@EventHandler
        public void onPreJoin(AsyncPlayerPreLoginEvent e) {
            
            if (Bukkit.getOnlinePlayers().size() >= ConfigInfo.getMaxPlayers()) {
                e.disallow(Result.KICK_FULL ,"Estamos llenos! Estado del juego: " + Game.getGameState().toString());
            }
        }
	@EventHandler
        public void onPlayerDamage(EntityDamageByEntityEvent e){
            
            if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
                Player damaged = (Player) e.getEntity();
                Player damager = (Player) e.getDamager();
                if (Game.getGameState().equals(GameState.WAITING)) {
                e.setCancelled(true);
                } else {
                    if (RestTeam.getPlayers().contains(damaged) && RestTeam.getPlayers().contains(damager)) {
                        damager.sendMessage("§You can't hurt your team");
                        e.setCancelled(true);
                    }  
                }

            }
        }
        
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
            e.setJoinMessage(null);
            Player player = e.getPlayer();

            for (Player p : Bukkit.getOnlinePlayers()) {
                p.playSound(p.getLocation(), Sound.BLOCK_LAVA_POP, 5,5);
            }
            String variables[] = {"%p%","%cp%","%max%"};
            String replace[] = {player.getName(),String.valueOf(Bukkit.getOnlinePlayers().size()),String.valueOf(ConfigInfo.getMaxPlayers())};
            Bukkit.broadcastMessage(LangInfo.replaceVariables(LangInfo.join_message, variables, replace));
            String[] loc = ConfigInfo.getSpawnLocation().split(",");
            Location lobby = new Location(WorldModule.getLobbyWorld(), Double.parseDouble(loc[0]),Double.parseDouble(loc[1]),Double.parseDouble(loc[2]));
            player.teleport(lobby);

            Game.getWaitingBossBar().addPlayer(player);
            Game.getWaitingBossBar().setTitle(LangInfo.needmoreplayers_bar.replace("%n",String.valueOf(
                       ConfigInfo.getMaxPlayers() - Bukkit.getOnlinePlayers().size())));
            if (Bukkit.getOnlinePlayers().size() == ConfigInfo.getMaxPlayers() && Game.getGameState().equals(GameState.WAITING)) {
                Game.startCountdownToStart();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    Game.getWaitingBossBar().removePlayer(p);
                }
            }
		

	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
            e.setQuitMessage(null);
            Player player = e.getPlayer();
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 5,5);
            }
            String variables[] = {"%p%","%cp%","%max%"};
            String replace[] = {player.getName(),String.valueOf(Bukkit.getOnlinePlayers().size()-1),String.valueOf(ConfigInfo.getMaxPlayers())};
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
