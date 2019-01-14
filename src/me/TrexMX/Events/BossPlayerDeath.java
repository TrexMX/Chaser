package me.TrexMX.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BossPlayerDeath extends Event {
	
	private static HandlerList handlers = new HandlerList();
	
	Player boss;
	Player killer;
	
	public BossPlayerDeath(Player boss, Player killer) {
		this.boss = boss;
		this.killer = killer;
	}
	
	public Player getBoss() {
		return boss;
	}
	
	public Player getKiller() {
		return killer;
	}
	
	@Override
	public HandlerList getHandlers() {


		return handlers;
	}

	static HandlerList getHandlerList() {
		return handlers;
	}
	
}
