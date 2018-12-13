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
            for(Player players : Bukkit.getOnlinePlayers()) {
			if (!players.equals(boss)) {
				restPlayers.add(players);
			}
		}
            RestTeam.setPlayers(restPlayers);
            Main.getInstance().getLogger().info("Players set.");
	}
	
}
