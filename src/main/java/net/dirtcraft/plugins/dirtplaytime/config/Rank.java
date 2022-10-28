package net.dirtcraft.plugins.dirtplaytime.config;

import org.bukkit.ChatColor;

import java.util.List;

public class Rank {
	private String name;
	public int time;
	private String timeUnit;
	public int money;
	public List<String> commands;

	public enum TimeUnit {
		minutes,
		hours,
		days,
	}

	public TimeUnit getTimeUnit() {
		return TimeUnit.valueOf(timeUnit);
	}
	public String getName() {
		return ChatColor.translateAlternateColorCodes('&', name);
	}
}
