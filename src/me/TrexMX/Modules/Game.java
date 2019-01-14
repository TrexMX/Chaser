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
	public void startGame() {
		
		new BukkitRunnable() {
			int count=10;
			@Override
			public void run() {
				if (count>0 ) {
					Bukkit.broadcastMessage(LangInfo.countdown_message.replaceAll("%count%",String.valueOf(count)));
					count--;
				} else {
					new Players();
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
                        BossBar bar = Bukkit.getServer().createBossBar(LangInfo.bossbar_message.replace("%t%", String.valueOf(count)),
                                BarColor.BLUE, BarStyle.SOLID, BarFlag.DARKEN_SKY);
                        double divpersecond = 1/ConfigInfo.getGameDuration();
			@Override
			public void run() {
				if (count>0) {
                                    bar.setTitle(LangInfo.bossbar_message.replace("%t%", String.valueOf(count)));
                                    bar.setProgress(divpersecond * count);
                                    count--;
                                }
				if (count<=0) {
                                    BossTeam.setWinners(true);
                                    RestTeam.setWinners(false);
                                    bar.setTitle(LangInfo.winner_broadcast.replace("%team%", BossTeam.getTeamName()));
                                    bar.setProgress(1);
                                    endGame();
                                    cancel();
                                }
                                if (count > 0 && state.equals(GameState.ENDED)) {
                                    bar.setTitle(LangInfo.winner_broadcast.replace("%team%", RestTeam.getTeamName()));
                                    bar.setProgress(1);
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
		BossTeam.getBossPlayer().teleport(new Location(WorldModule.getGameWorld(),Double.parseDouble(bossCords[0]),Double.parseDouble(bossCords[1]),Double.parseDouble(bossCords[2])));
                for (Player p : RestTeam.getPlayers()) {
                    int index = 0;
                    String loc = ArenaInfo.getRestPlayersLocations()[index];
                    String[] restCords = loc.split(",");
                    p.teleport(new Location(WorldModule.getGameWorld(),Double.parseDouble(restCords[0]),Double.parseDouble(restCords[1]),Double.parseDouble(restCords[2])));
                    index++;
                }
                 
	}
	
	private void giveKits() {
		 BossTeam.getBossPlayer().getInventory().clear();
		 BossTeam.getBossPlayer().getInventory().setContents(BossTeam.getTeamKit().getContents());
		for (Player p : RestTeam.getPlayers()) {
			p.getInventory().clear();
			p.getInventory().setContents(RestTeam.getTeamKit().getContents());
		}
	}

        private void setEffects() {
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
                
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendTitle(LangInfo.winner_broadcast, "MCMéxico 2018",10,70,20);
                    p.sendMessage(LangInfo.serverrestart_message);
                }
                new BukkitRunnable() {
                    int count = 10;
                    @Override                    
                    public void run() {
                        count--;
                        if (count==0) {
                            Bukkit.getServer().shutdown();
                            Bukkit.broadcastMessage("Bye bye, losers!");
                        }
                    }
                }.runTaskLater(Main.getPlugin(Main.class), 20);
                
	}
        
        public static void setWaiting() {
            state = GameState.WAITING;
            BarFlag fg = BarFlag.DARKEN_SKY;
            WaitingBar = Bukkit.createBossBar(LangInfo.needmoreplayers_bar.replace("%n",String.valueOf(
                   ConfigInfo.getMaxPlayers() - Bukkit.getOnlinePlayers().size())), BarColor.BLUE, BarStyle.SOLID, fg);
            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.broadcastMessage(LangInfo.waiting_message);
                }
            }.runTaskTimer(Main.getPlugin(Main.class), 0, 3600);
        }

	public static GameState getGameState() {
		return state;
	}
	
        public static BossBar getWaitingBossBar() {
            return WaitingBar;
        }
	
}
