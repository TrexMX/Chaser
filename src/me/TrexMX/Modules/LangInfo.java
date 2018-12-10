package me.TrexMX.Modules;

import me.TrexMX.Main.Main;

public class LangInfo {
	
	public static String 
	join_message,
	left_message,
	countdown_message,
	start_message,
	notenoughplayers,
	teleportcountdown_message,
	teleported_message;

	
	public static void getMessages() {
		join_message = LoadConfig.getLangConfig().getString("join_message");
		left_message = LoadConfig.getLangConfig().getString("left_message");
		countdown_message = LoadConfig.getLangConfig().getString("countdown_message");
		start_message = LoadConfig.getLangConfig().getString("start_message");
		notenoughplayers = LoadConfig.getLangConfig().getString("notenoughplayers");
		teleportcountdown_message = LoadConfig.getLangConfig().getString("teleportcountdown_message");
		teleported_message = LoadConfig.getLangConfig().getString("teleported_message");
		Main.getInstance().getLogger().info("Language config loaded");
	}
	
	public static String replaceVariables(String message, String[] variables, String[] replace) {
		
		for (int x=0;x<variables.length;x++) {
			message.replace(variables[x], replace[x]);
		}
		
		return message;
	}
	
}
