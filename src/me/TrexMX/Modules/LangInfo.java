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
	teleported_message,
        gamefinished_message,
        win_message,
        lose_message,
        winner_broadcast,
        bossbar_message,
        serverrestart_message,
                waiting_message,
                needmoreplayers_bar,
                you_killed;

	
	public static void getMessages() {
		join_message = LoadConfig.getLangConfig().getString("join_message");
		left_message = LoadConfig.getLangConfig().getString("left_message");
		countdown_message = LoadConfig.getLangConfig().getString("countdown_message");
		start_message = LoadConfig.getLangConfig().getString("start_message");
		notenoughplayers = LoadConfig.getLangConfig().getString("notenoughplayers");
		teleportcountdown_message = LoadConfig.getLangConfig().getString("teleportcountdown_message");
		teleported_message = LoadConfig.getLangConfig().getString("teleported_message");
                win_message =  LoadConfig.getLangConfig().getString("win_message");
                lose_message = LoadConfig.getLangConfig().getString("lose_message");
                winner_broadcast =  LoadConfig.getLangConfig().getString("winner_broadcast");
                bossbar_message =  LoadConfig.getLangConfig().getString("bossbar_message");
                serverrestart_message = LoadConfig.getLangConfig().getString("serverwillrestart_message");
                waiting_message = LoadConfig.getLangConfig().getString("waiting_broadcast");
                needmoreplayers_bar = LoadConfig.getLangConfig().getString("needmoreplayers_bar");
                you_killed = LoadConfig.getLangConfig().getString("youkilledtheboss");
		Main.getInstance().getLogger().info("Language config loaded");
	}
	
        // Reemplaza las variables en un Vector con las Strings de otro vector, efectivo si tienes que
        // reemplazar muchas variables en un solo mensaje
	public static String replaceVariables(String message, String[] variables, String[] replace) {
		String replaceAll = message;
                for (int x=0;x<variables.length;x++) {
                    replaceAll = replaceAll.replaceAll(variables[x], replace[x]);
		}
		
		return replaceAll;
	}
	
}
