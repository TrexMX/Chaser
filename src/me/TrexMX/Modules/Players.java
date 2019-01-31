package me.TrexMX.Modules;

import com.google.common.collect.ImmutableList;
import io.netty.util.internal.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import me.TrexMX.Main.Main;
import me.TrexMX.Teams.BossTeam;
import me.TrexMX.Teams.RestTeam;

public class Players {

	private Player boss;
	private ArrayList<Player> restPlayers = new ArrayList<Player>();
	
	public Players() {
            List<Player> ps = ImmutableList.copyOf(Bukkit.getOnlinePlayers());
            boss = ps.get(ThreadLocalRandom.current().nextInt(ps.size()));
            BossTeam.setBossPlayer(boss);

            for(Player players : Bukkit.getOnlinePlayers()) {
			if (!players.equals(boss)) {
				restPlayers.add(players);
			}
		}
            RestTeam.setPlayers(restPlayers);

            Main.getInstance().getLogger().info("Players set on teams.");
	}
	
}
