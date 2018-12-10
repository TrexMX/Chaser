package me.TrexMX.Modules;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import me.TrexMX.Main.Main;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;


public class KitModule {

	private static Inventory RestKitInventory, BossKitInventory;
	
	public static void loadKits() {
		loadRestKit();
		loadBossKit();
		Main.getInstance().getLogger().info("Kits loaded");
	}
	
	private static void loadRestKit() {
		ItemStack item;
		for (int x=0;x<ArenaInfo.getRestPlayersKit().length; x++) {
			
			String[] duo = new String[3];
			
			duo = ArenaInfo.getRestPlayersKit()[x].split(";");
			String name= duo[0];
			int amount = Integer.parseInt(duo[1]);
			int slot = Integer.parseInt(duo[2]);
			Material material = Material.valueOf(name);
			item = new ItemStack(material, amount);
			RestKitInventory = Main.getInstance().getServer().createInventory(null, InventoryType.PLAYER);
			RestKitInventory.setItem(slot, item);
		}
		Main.getInstance().getLogger().info("Kit 1 loaded");
	}
	
	private static void loadBossKit() {
		ItemStack item;
		for (int x=0;x<ArenaInfo.getBossPlayerKit().length; x++) {
			String[] duo = new String[3];
			duo = ArenaInfo.getBossPlayerKit()[x].split(";");
			String name= duo[0];
			int amount = Integer.parseInt(duo[1]);
			int slot = Integer.parseInt(duo[2]);
			item = new ItemStack(Material.getMaterial(name,false), amount);
			BossKitInventory = Main.getInstance().getServer().createInventory(null, InventoryType.PLAYER);
			BossKitInventory.setItem(slot, item);
		}
		Main.getInstance().getLogger().info("Kit 2 loaded");
	}
	
	public static Inventory getRestKit() {
		return RestKitInventory;
	}
	
	public static Inventory getBossKit() {
		return BossKitInventory;
	}
}
