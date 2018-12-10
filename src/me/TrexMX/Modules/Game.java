package me.TrexMX.Modules;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.TrexMX.Main.Main;
public class Game {
	

	private static GameState state;
	
	@SuppressWarnings("deprecation")
	public void startGame() {
		
		
		
		Bukkit.getScheduler().runTask(Main.getPlugin(Main.class), new BukkitRunnable() {
			int count=10;

			@Override
			public void run() {
				if (count>0 ) {
					Bukkit.broadcastMessage(LangInfo.countdown_message.replaceAll("%count%",String.valueOf(count)));
					count--;
				} else {
					Players.setPlayers();
					teleportPlayers();
					for(Player p : Bukkit.getOnlinePlayers()) {
						p.sendMessage(LangInfo.teleported_message);
					}
					Bukkit.broadcastMessage(LangInfo.start_message);
					state = GameState.PLAYING;
					giveKits();
					cancel();
				}

			}
		});
		

		
		Bukkit.getScheduler().runTask(Main.getPlugin(Main.class), new BukkitRunnable() {
			int count=ConfigInfo.getGameDuration();

			@Override
			public void run() {
				if (count>0 ) {
					
					count--;
				} else {
					cancel();
				}

			}
		});
		
	}
	
	@SuppressWarnings("deprecation")
	public void startCountdownToStart() {
		state = GameState.STARTING;
		Bukkit.getScheduler().runTask(Main.getPlugin(Main.class), new BukkitRunnable() {
			int count=10;
			
			@Override
			public void run() {
				if (count>0 && Bukkit.getOnlinePlayers().size()>=ConfigInfo.getMaxPlayers()) {
					Bukkit.broadcastMessage(LangInfo.teleportcountdown_message.replaceAll("%count%",String.valueOf(count)));
					count--;
				
				}
				if (count<=0 && Bukkit.getOnlinePlayers().size()>=ConfigInfo.getMaxPlayers()) {
					
					startGame();
					
					cancel();
				}
				if (Bukkit.getOnlinePlayers().size()<ConfigInfo.getMaxPlayers()) {
					Bukkit.broadcastMessage(LangInfo.notenoughplayers);
					state = GameState.WAITING;
					cancel();
				}
			}
		});
		
		
	}
	
	private void teleportPlayers() {
		
		String[] bossCords = ArenaInfo.getBossLocation().split(","); 
		Players.getBossPlayer().teleport(new Location(WorldModule.getGameWorld(),Double.parseDouble(bossCords[0]),Double.parseDouble(bossCords[1]),Double.parseDouble(bossCords[2])));
		for (String loc : ArenaInfo.getRestPlayersLocations()) {
			String[] restCords = loc.split(",");
			for (Player p : Players.getRestPlayers()) {
				p.teleport(new Location(WorldModule.getGameWorld(),Double.parseDouble(restCords[0]),Double.parseDouble(restCords[1]),Double.parseDouble(restCords[2])));
			}
		}
		
		
	}
	
	private void giveKits() {
		Players.getBossPlayer().getInventory().clear();
		Players.getBossPlayer().getInventory().setContents(KitModule.getBossKit().getContents());
		for (Player p : Players.getRestPlayers()) {
			p.getInventory().clear();
			p.getInventory().setContents(KitModule.getRestKit().getContents());
		}
	}

	public static void endGame() {
		state  = GameState.ENDED;
		
	}
	
	public static GameState getGameState() {
		return state;
	}
	
}
