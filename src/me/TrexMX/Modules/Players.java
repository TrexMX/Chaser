package me.TrexMX.Modules;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import me.TrexMX.Main.Main;
import me.TrexMX.Teams.BossTeam;
import me.TrexMX.Teams.RestTeam;

public class Players {

	private Player boss;
	private ArrayList<Player> restPlayers = new ArrayList<Player>();
	
	public Players() {
            boss = Bukkit.getOnlinePlayers().stream().findAny().get();
            BossTeam.setBossPlayer(boss);
            Bukkit.broadcastMessage("§4" + boss.getName() + " §ces el Chaser!");
            boss.sendMessage("§4Eres el chaser!");
            for(Player players : Bukkit.getOnlinePlayers()) {
			if (!players.equals(boss)) {
				restPlayers.add(players);
			}
		}
            RestTeam.setPlayers(restPlayers);
            for(Player p : restPlayers) {
                p.sendMessage("§cTu trabajo es matar al Chaser!");
            }
            Main.getInstance().getLogger().info("Players set on teams.");
	}
	
}
