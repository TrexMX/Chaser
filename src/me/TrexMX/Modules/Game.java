package me.TrexMX.Modules;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.TrexMX.Main.Main;
import me.TrexMX.Teams.BossTeam;
import me.TrexMX.Teams.RestTeam;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.potion.PotionEffect;
public class Game {
	
        private static BossBar WaitingBar;
	private static GameState state;        
	
	@SuppressWarnings("deprecation")
	private static void startGame() {
		
		new BukkitRunnable() {
			int count=10;
			@Override
			public void run() {
				if (count>0 ) {
					Bukkit.broadcastMessage(LangInfo.countdown_message.replaceAll("%count%",String.valueOf(count)));
					count--;
                                }
                                if (count == 0) {
					for(Player p : Bukkit.getOnlinePlayers()) {
						p.sendMessage(LangInfo.teleported_message);
					}
					Bukkit.broadcastMessage(LangInfo.start_message);
					state = GameState.PLAYING;
					giveKits();
                                        setEffects();
                                        count = -1;
					cancel();
				}

			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 20);
		
		new BukkitRunnable() {
			int count=ConfigInfo.getGameDuration();
                        BossBar bar = Bukkit.getServer().createBossBar(LangInfo.bossbar_message.replace("%t%", String.valueOf(count)),
                                BarColor.BLUE, BarStyle.SOLID, BarFlag.DARKEN_SKY);
                        
                        double divpersecond = 1/ConfigInfo.getGameDuration();
			@Override
			public void run() {
                                if (count == ConfigInfo.getGameDuration()) {
                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        bar.addPlayer(p);
                                        bar.setVisible(true);
                                    }   
                                }
                                
				if (count>0 && state.equals(GameState.PLAYING)) {
                                    bar.setTitle(LangInfo.bossbar_message.replace("%t%", String.valueOf(count)));
                                    bar.setProgress(divpersecond * count);
                                    count--;
                                }
				if (count==0) {
                                    BossTeam.setWinners(true);
                                    RestTeam.setWinners(false);
                                    bar.setTitle(LangInfo.winner_broadcast.replace("%team%", BossTeam.getTeamName()));
                                    bar.setProgress(1);
                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        p.sendTitle(LangInfo.winner_broadcast.replace("%team%", BossTeam.getTeamName()), "MCMéxico 2018",10,70,20);
                                        Bukkit.broadcastMessage(LangInfo.winner_broadcast.replace("%team%", BossTeam.getTeamName()));
                                    }

                                    endGame();
                                    count = -1;
                                    cancel();
                                }
                                if (count > 0 && state.equals(GameState.ENDED)) {
                                    bar.setTitle(LangInfo.winner_broadcast.replace("%team%", RestTeam.getTeamName()));
                                    bar.setProgress(1);
                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        p.sendTitle(LangInfo.winner_broadcast.replace("%team%", RestTeam.getTeamName()), "MCMéxico 2018",10,70,20);
                                        Bukkit.broadcastMessage(LangInfo.winner_broadcast.replace("%team%", RestTeam.getTeamName()));
                                    }

                                   cancel();
				}

			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 20);
		
	}
	
	@SuppressWarnings("deprecation")
	public static void startCountdownToStart() {
		state = GameState.STARTING;
                WaitingBar.removeAll();
                WaitingBar.setVisible(false);
		new BukkitRunnable() {
			int count=10;
			
			@Override
			public void run() {
				if (count>0) {
					Bukkit.broadcastMessage(LangInfo.teleportcountdown_message.replaceAll("%count%",String.valueOf(count)));
					count--;
				
				}
				if (count==0) {
                                        startGame();
                                        new Players();
					teleportPlayers();
					cancel();
                                        count = -1;
				}
				if (Bukkit.getOnlinePlayers().size()<ConfigInfo.getMaxPlayers()) {
					Bukkit.broadcastMessage(LangInfo.notenoughplayers);
					state = GameState.WAITING;
					cancel();
				}
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 20);
		
		
	}
	
	private static void teleportPlayers() {
		String[] bossCords = ArenaInfo.getBossLocation().split(","); 
		BossTeam.getBossPlayer().teleport(new Location(WorldModule.getGameWorld(),Double.parseDouble(bossCords[0]),Double.parseDouble(bossCords[1]),Double.parseDouble(bossCords[2])));
                for (Player p : RestTeam.getPlayers()) {
                    int index = 0;
                    String loc = ArenaInfo.getRestPlayersLocations()[index];
                    String[] restCords = loc.split(",");
                    p.teleport(new Location(WorldModule.getGameWorld(),Double.parseDouble(restCords[0]),Double.parseDouble(restCords[1]),Double.parseDouble(restCords[2])));
                    index++;
                }
                 
	}
	
	private static void giveKits() {
		 BossTeam.getBossPlayer().getInventory().clear();
		 BossTeam.getBossPlayer().getInventory().setContents(BossTeam.getTeamKit().getContents());
		for (Player p : RestTeam.getPlayers()) {
			p.getInventory().clear();
			p.getInventory().setContents(RestTeam.getTeamKit().getContents());
		}
	}

        private static void setEffects() {
           for (PotionEffect effect : BossTeam.getBossEffects()) {
               BossTeam.getBossPlayer().addPotionEffect(effect);
           }
        }
	public static void endGame() {
		state  = GameState.ENDED;
                if (BossTeam.isWinner()){
                    Player p = BossTeam.getBossPlayer();
                    p.sendMessage(LangInfo.win_message);
                    Sounds.playWin(p);
                } else {
                    Player p = BossTeam.getBossPlayer();
                    p.sendMessage(LangInfo.lose_message);
                    Sounds.playLose(p);
                }
                if (RestTeam.isWinner()) {
                    for (Player p : RestTeam.getPlayers()) {
                        p.sendMessage(LangInfo.win_message);
                        Sounds.playWin(p);
                    }
                } else {
                    for (Player p : RestTeam.getPlayers()) {
                        p.sendMessage(LangInfo.lose_message);
                        Sounds.playLose(p);
                    }
                }
                
                new BukkitRunnable() {
                    int count = 20;
                    @Override                    
                    public void run() {
                        if (count == 20 ){
                            Bukkit.broadcastMessage(LangInfo.serverrestart_message);
                        }
                        if (count <6 && count >0) {
                            Bukkit.broadcastMessage("§cRestarting in " + count);
                        }
                        count--;
                        if (count==0) {
                            Bukkit.getServer().shutdown();
                            Bukkit.broadcastMessage("§4Bye bye, losers!");
                        }
                    }
                }.runTaskTimer(Main.getPlugin(Main.class), 0, 20);
                
	}
        
        public static void setWaiting() {
            // Establece el juego como estado "esperando"
            state = GameState.WAITING;
            // Crea boss bar
            WaitingBar = Bukkit.createBossBar(LangInfo.needmoreplayers_bar.replace("%n",String.valueOf(
                       ConfigInfo.getMaxPlayers() - Bukkit.getOnlinePlayers().size())), BarColor.BLUE, BarStyle.SOLID, BarFlag.DARKEN_SKY);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (Game.getGameState().equals(GameState.WAITING)){
                        Bukkit.broadcastMessage(LangInfo.waiting_message);
                    } else {
                        this.cancel();
                    }
                    
                }
            }.runTaskTimer(Main.getPlugin(Main.class), 0, 600);
        }

	public static GameState getGameState() {
		return state;
	}
	
        public static BossBar getWaitingBossBar() {
            return WaitingBar;
        }
	
}
