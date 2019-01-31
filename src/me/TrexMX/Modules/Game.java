package me.TrexMX.Modules;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.TrexMX.Main.Main;
import me.TrexMX.Teams.BossTeam;
import me.TrexMX.Teams.RestTeam;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
public class Game {
	
        private static BossBar Bar;
	private static GameState state;        
	
	@SuppressWarnings("deprecation")
	private static void startGame() {
                if (Game.getGameState().equals(GameState.STARTING)) {
                    state = GameState.PLAYING;
                    Player boss = BossTeam.getBossPlayer();
                    Bukkit.broadcastMessage("§4" + boss.getName() + " §ces el Chaser!");
                    boss.sendMessage("§4Eres el chaser!");
                    for(Player p : RestTeam.getPlayers()) {
                        p.sendMessage("§cTu trabajo es matar al Chaser!");
                    }
                    giveKits();
                    setEffects();
                    Bukkit.broadcastMessage(LangInfo.start_message);
                }

                
		new BukkitRunnable() {
			int count=ConfigInfo.getGameDuration();
                        double divpersecond = 1/ConfigInfo.getGameDuration();
			@Override
			public void run() {

                                
				if (count >0 && state.equals(GameState.PLAYING)) {
                                    Bar.setTitle(LangInfo.bossbar_message.replaceAll("%t%", String.valueOf(count)));
                                    Bar.setProgress(divpersecond * count);
                                    count--;
                                }
                                
				if (count==0 && Game.getGameState().equals(GameState.PLAYING)) {
                                    BossTeam.setWinners(true);
                                    RestTeam.setWinners(false);
                                    
                                    Bar.setTitle(LangInfo.winner_broadcast.replace("%team%", BossTeam.getTeamName()));
                                    Bar.setProgress(1);
                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        p.sendTitle(LangInfo.winner_broadcast.replace("%team%", BossTeam.getTeamName()), "MCMéxico 2018",10,70,20);
                                        Bukkit.broadcastMessage(LangInfo.winner_broadcast.replace("%team%", BossTeam.getTeamName()));
                                    }

                                    
                                    count = -1;
                                    endGame();
                                    this.cancel();
                                }
                                if (count > 0 && Game.getGameState().equals(GameState.ENDED)) {
                                    Bar.setTitle(LangInfo.winner_broadcast.replace("%team%", RestTeam.getTeamName()));
                                    Bar.setProgress(1);
                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        p.sendTitle(LangInfo.winner_broadcast.replace("%team%", RestTeam.getTeamName()), "MCMéxico 2018",10,70,20);
                                        Bukkit.broadcastMessage(LangInfo.winner_broadcast.replace("%team%", RestTeam.getTeamName()));
                                    }
                                    count = -1;
                                   this.cancel();
				}

			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 20);
		
	}
	
	@SuppressWarnings("deprecation")
	public static void countdownToTeleport() {
		state = GameState.STARTING;
                
		new BukkitRunnable() {
			int count=10;
			double divpersecond = 1/10;
			@Override
			public void run() {
				if (count>0) {
					Bukkit.broadcastMessage(LangInfo.teleportcountdown_message.replaceAll("%count%",String.valueOf(count)));
                                        Bar.setTitle(LangInfo.teleportcountdown_message.replaceAll("%count%",String.valueOf(count)));
                                        Bar.setProgress(divpersecond * count);
                                        count--;
				}
				if (count==0) {
                                        
                                        new Players();
					teleportPlayers();
                                        countdownToStart();
                                        count = -1;
					this.cancel();
                                        
				}
				if (Bukkit.getOnlinePlayers().size()<ConfigInfo.getMaxPlayers() && !Game.getGameState().equals(GameState.WAITING)) {
					Bukkit.broadcastMessage(LangInfo.notenoughplayers);
					state = GameState.WAITING;
					this.cancel();
				}
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 20);
		
		
	}
        
        public static void countdownToStart() {
		new BukkitRunnable() {
			int count=10;
                        double divpersecond = 1/10;
			@Override
			public void run() {
				if (count>0 ) {
                                        for(Player p : Bukkit.getOnlinePlayers()) {
						p.sendTitle(LangInfo.countdown_message.replaceAll("%count%",String.valueOf(count)), "", 20, 20, 20);
                                                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, 2);
                                        }
                                        Bar.setTitle(LangInfo.countdown_message.replaceAll("%count%",String.valueOf(count)));
                                        Bar.setProgress(divpersecond * count);
					Bukkit.broadcastMessage(LangInfo.countdown_message.replaceAll("%count%",String.valueOf(count)));
					count--;
                                }
                                if (count == 0 && Game.getGameState().equals(GameState.STARTING)) {
					for(Player p : Bukkit.getOnlinePlayers()) {
						p.sendMessage(LangInfo.teleported_message);
                                                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_SNARE, 2, 2);
					}
					startGame();
                                        count = -1;
					this.cancel();
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
                 Main.getInstance().getLogger().info("BossKit given");
		for (Player p : RestTeam.getPlayers()) {
			p.getInventory().clear();
			p.getInventory().setContents(RestTeam.getTeamKit().getContents());
                        
		}
                Main.getInstance().getLogger().info("RestKit given");
	}

        private static void setEffects() {
               BossTeam.getBossPlayer().addPotionEffects(BossTeam.getBossEffects());
               Main.getInstance().getLogger().info("Boss PotionEffects given");
        }
        
	public static void endGame() {
            // Establece el juego como terminado
		state  = GameState.ENDED;
                // Aquí se dan las 4 formas de victoria
                if (BossTeam.isWinner()){
                    Player p = BossTeam.getBossPlayer();
                    p.sendMessage(LangInfo.win_message);
                    Sounds.playWin(p);
                    for (Player p1 : Bukkit.getOnlinePlayers()) {
                        p1.sendTitle(LangInfo.winner_broadcast.replace("%team%", BossTeam.getTeamName()), "MCMéxico 2018",10,70,20);
                        Bukkit.broadcastMessage(LangInfo.winner_broadcast.replace("%team%", BossTeam.getTeamName()));
                    }
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
                    for (Player p1 : Bukkit.getOnlinePlayers()) {
                        p1.sendTitle(LangInfo.winner_broadcast.replace("%team%", RestTeam.getTeamName()), "MCMéxico 2018",10,70,20);
                        Bukkit.broadcastMessage(LangInfo.winner_broadcast.replace("%team%", RestTeam.getTeamName()));
                    }
                } else {
                    for (Player p : RestTeam.getPlayers()) {
                        p.sendMessage(LangInfo.lose_message);
                        Sounds.playLose(p);
                    }
                }
                // Makes countdown to
                new BukkitRunnable() {
                    int count = ConfigInfo.getShutdownCountdown();
                    @Override                    
                    public void run() {
                        if (count == ConfigInfo.getShutdownCountdown() ){
                            Bukkit.broadcastMessage(LangInfo.serverrestart_message.replaceAll("%t", String.valueOf(ConfigInfo.getShutdownCountdown())));
                        }
                        if (count <6 && count >0) {
                            Bukkit.broadcastMessage("§cRestarting in " + count);
                        }
                        count--;
                        if (count==0) {
                            Bukkit.getServer().shutdown();
                            Bukkit.broadcastMessage("§4Gracias por testear con nosotros!");
                        }
                    }
                }.runTaskTimer(Main.getPlugin(Main.class), 0, 20);
                
	}
        
        public static void setWaiting() {
            // Establece el juego como estado "esperando"
            state = GameState.WAITING;
            // Crea boss bar
            Bar = Bukkit.createBossBar(LangInfo.needmoreplayers_bar.replace("%n",String.valueOf(
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
	
        public static BossBar getBossBar() {
            return Bar;
        }
	
}
