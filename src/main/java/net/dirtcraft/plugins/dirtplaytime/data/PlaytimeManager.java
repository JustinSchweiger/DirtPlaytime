package net.dirtcraft.plugins.dirtplaytime.data;

import net.dirtcraft.plugins.dirtplaytime.DirtPlaytime;
import net.dirtcraft.plugins.dirtplaytime.database.DatabaseOperations;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlaytimeManager {
	private static final Set<UUID> onlinePlayers = new HashSet<>();
	private static BukkitTask scheduler;

	public static void startPlaytimeScheduler() {
		scheduler = Bukkit.getScheduler().runTaskTimerAsynchronously(DirtPlaytime.getPlugin(), () -> {
			if (onlinePlayers.isEmpty()) return;

			DatabaseOperations.addPlaytime(onlinePlayers);
		}, 0, 20L);
	}

	public static void addOnlinePlayer(Player player) {
		onlinePlayers.add(player.getUniqueId());
	}

	public static void removeOnlinePlayer(Player player) {
		onlinePlayers.remove(player.getUniqueId());
	}
}
