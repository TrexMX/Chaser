package me.TrexMX.Modules;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Sounds {

	public static void playWin(Player player) {
		Location loc = player.getLocation();
		player.playSound(loc, Sound.BLOCK_NOTE_BLOCK_PLING, 10F, 10F);
		player.playSound(loc, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 10F, 10F);
	}
	
	public static void playLose(Player player) {
		Location loc = player.getLocation();
		player.playSound(loc, Sound.BLOCK_ANVIL_FALL, 10F, 10F);
		player.playSound(loc, Sound.ENTITY_WITHER_HURT, 10F, 10F);
	}

	
	public static void playCustomSounds(Player player, Sound[] sounds) {
		Location loc = player.getLocation();
		for (int x=0; x<sounds.length; x++) {
		player.playSound(loc, sounds[x], 10F, 10F);
		}
	}
	
}
