package me.TrexMX.Modules;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.TrexMX.Main.Main;
import org.bukkit.potion.PotionEffect;
public class Game {
	

	private static GameState state;
        private static Player[] winners,losers;
        
	
	@SuppressWarnings("deprecation")
	public void startGame() {
		
		new BukkitRunnable() {
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
                                        setEffects();
					cancel();
				}

			}
		}.runTaskLater(Main.getPlugin(Main.class), 20);
		
		new BukkitRunnable() {
			int count=ConfigInfo.getGameDuration();

			@Override
			public void run() {
				if (count>0) {
                                    // to-do counter playing
                                    count--;
                                }
				if (count<=0 || state.equals(GameState.ENDED)) {
                                    cancel();
				}

			}
		}.runTaskLater(Main.getPlugin(Main.class), 20);
		
	}
	
	@SuppressWarnings("deprecation")
	public void startCountdownToStart() {
		state = GameState.STARTING;
		new BukkitRunnable() {
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
		}.runTaskLater(Main.getPlugin(Main.class), 20);
		
		
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

        private void setEffects() {
           for (PotionEffect effect : PotionEffectModule.getBossPotionEffects()) {
               Players.getBossPlayer().addPotionEffect(effect);
           }
        }
	public static void endGame() {
		state  = GameState.ENDED;
                for (Player p : winners) {
                    p.sendMessage(LangInfo.win_message);
                    Sounds.playWin(p);
                }
                for (Player p : losers) {
                    p.sendMessage(LangInfo.lose_message);
                    Sounds.playLose(p);
                }
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendTitle(LangInfo.winner_broadcast, "MCMéxico 2018");
                }
	}
        
        public static void setWinners(Player[] p){
                winners = p;
        }
        
        public static void setLosers(Player[] p){
                losers = p;
        }
        
        public static Player[] getWinners() {
            return winners;
        }
	
        public static Player[] getLosers() {
            return losers;
        }
	public static GameState getGameState() {
		return state;
	}
	
}
