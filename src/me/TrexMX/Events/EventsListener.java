package me.TrexMX.Events;

import java.util.ArrayList;
import me.TrexMX.Main.Main;
import me.TrexMX.Modules.ArenaInfo;
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
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;

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
            if (!Game.getGameState().equals(GameState.WAITING)) {
                e.disallow(Result.KICK_OTHER, "Estado del juego: "+ Game.getGameState().toString());
            }
        }
        
	@EventHandler
        public void onPlayerDamage(EntityDamageByEntityEvent e){
            
            if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
                Player damaged = (Player) e.getEntity();
                Player damager = (Player) e.getDamager();
                if (Game.getGameState().equals(GameState.WAITING) || Game.getGameState().equals(GameState.STARTING)) {
                    damager.sendMessage("§cThe game hasn't started yet");
                    e.setCancelled(true);
                }
            if (Game.getGameState().equals(GameState.PLAYING)) {
                    if (RestTeam.getPlayers().contains(damaged) && RestTeam.getPlayers().contains(damager)) {
                        damager.sendMessage("§cYou can't hurt your team");
                        e.setCancelled(true);
                    }  
                }
            if (Game.getGameState().equals(GameState.ENDED)) {
                    damager.sendMessage("§cThe game has ended");
                    e.setCancelled(true);
            }
            }
            
        }
        
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
            e.setJoinMessage(null);
            Player player = e.getPlayer();
            player.getInventory().clear();
            player.setHealth(20);
            player.setFoodLevel(20);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.playSound(p.getLocation(), Sound.BLOCK_LAVA_POP, 5,5);
            }
            
            String variables[] = {"%p%","%cp%","%max%"};
            String replace[] = {player.getName(),String.valueOf(Bukkit.getOnlinePlayers().size()),String.valueOf(ConfigInfo.getMaxPlayers())};
            Bukkit.broadcastMessage(LangInfo.replaceVariables(LangInfo.join_message, variables, replace));
            String[] loc = ConfigInfo.getSpawnLocation().split(",");
            Location lobby = new Location(WorldModule.getLobbyWorld(), Double.parseDouble(loc[0]),Double.parseDouble(loc[1]),Double.parseDouble(loc[2]));
            player.teleport(lobby);

            Game.getBossBar().addPlayer(player);
            Game.getBossBar().setTitle(LangInfo.needmoreplayers_bar.replace("%n",String.valueOf(
                       ConfigInfo.getMaxPlayers() - Bukkit.getOnlinePlayers().size())));
            if (Bukkit.getOnlinePlayers().size() == ConfigInfo.getMaxPlayers() && Game.getGameState().equals(GameState.WAITING)) {
                Game.countdownToTeleport();
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
            if (RestTeam.getPlayers().contains(deadPlayer)) {
                deadPlayer.setGameMode(GameMode.SPECTATOR);
                ArrayList<Player> alive = RestTeam.getPlayers();
                alive.remove(deadPlayer);
                RestTeam.setPlayers(alive);
                if (!RestTeam.getPlayers().iterator().hasNext()) {
                    RestTeam.setWinners(false);
                    BossTeam.setWinners(true);
                    Game.endGame();
                }
            }
	}
	
	@EventHandler
	public void onBossDeath(BossPlayerDeath e) {
            RestTeam.setWinners(true);
            BossTeam.setWinners(false);
            e.getKiller().sendMessage(LangInfo.you_killed);
            Game.endGame();
	}
        @EventHandler
        public void onBreak(BlockBreakEvent e) {
            if (!Game.getGameState().equals(GameState.PLAYING)) {
                e.setCancelled(true);
            }
        }
        @EventHandler
        public void onPlace(BlockPlaceEvent e) {
            if (!Game.getGameState().equals(GameState.PLAYING)) {
                e.setCancelled(true);
            }
        }
        @EventHandler
        public void onDrop(PlayerDropItemEvent e) {
            e.setCancelled(true);
        }
        @EventHandler
        public void onPlayerMove(PlayerMoveEvent e) {
            if (Game.getGameState().equals(GameState.STARTING) && e.getPlayer().getWorld().equals(Bukkit.getWorld(ConfigInfo.getGameWorldName()))) {
                
                e.setCancelled(true);
            }
        }
        @EventHandler
        public void onPlayerHunger(FoodLevelChangeEvent e) {
                e.setCancelled(true);
        }
}
