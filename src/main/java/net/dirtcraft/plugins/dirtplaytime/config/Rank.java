package net.dirtcraft.plugins.dirtplaytime.config;

import org.bukkit.ChatColor;

import java.util.List;

public class Rank {
	public String name;
	public String prerequisite;
	public String nextRank;
	private String displayName;
	public int time;
	private String timeUnit;
	public int money;
	public List<String> commands;

	public enum TimeUnit {
		seconds,
		minutes,
		hours,
		days,
	}

	public TimeUnit getTimeUnit() {
		return TimeUnit.valueOf(timeUnit);
	}

	public long getTimeRequirement() {
		switch (getTimeUnit()) {
			case minutes:
				return time * 60L;
			case hours:
				return time * 3600L;
			case days:
				return time * 86400L;
			default:
				return time;
		}
	}

	public String getDisplayName() {
		return ChatColor.translateAlternateColorCodes('&', displayName);
	}
}
