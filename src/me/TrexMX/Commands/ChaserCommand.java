package me.TrexMX.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChaserCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length < 1) {
				p.sendMessage("§cPlugin developed by Ernesto Ramirez");
				
			} else {
				if (args[0].equalsIgnoreCase("world")) {
					
					p.teleport(new Location(Bukkit.getWorld(args[1]), 0, 70, 0));
				}
				
			}
			
		}
		return false;
	}

}
