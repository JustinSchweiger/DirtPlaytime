package net.dirtcraft.plugins.dirtplaytime.data;

import net.dirtcraft.plugins.dirtplaytime.DirtPlaytime;
import net.dirtcraft.plugins.dirtplaytime.config.Rank;
import net.dirtcraft.plugins.dirtplaytime.database.DatabaseOperations;
import net.dirtcraft.plugins.dirtplaytime.utils.Strings;
import net.dirtcraft.plugins.dirtplaytime.utils.Utilities;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.time.LocalDateTime;
import java.util.*;

public class PlaytimeManager {
	private static final Set<UUID> onlinePlayers = new HashSet<>();
	private static final Map<UUID, Player> playerTracker = new HashMap<>();
	private static BukkitTask playtimeScheduler;
	private static BukkitTask syncScheduler;
	private static BukkitTask rankupScheduler;

	public static void initialisePlayerTracker() {
		DatabaseOperations.getPlayers(playerTracker::putAll);
	}

	public static void createPlayer(UUID uuid, String username) {
		DatabaseOperations.createPlayer(uuid, username, () -> {
			PlaytimeManager.addOnlinePlayer(uuid);
			playerTracker.put(uuid, new Player(uuid, username, "starter", 0, 1, LocalDateTime.now()));
		});
	}

	public static void startSchedulers() {
		playtimeScheduler = Bukkit.getScheduler().runTaskTimerAsynchronously(DirtPlaytime.getPlugin(), () -> {
			if (onlinePlayers.isEmpty()) return;

			for (UUID uuid : onlinePlayers) {
				Player player = playerTracker.get(uuid);
				if (player == null) continue;

				player.addTimePlayed(1);
			}
		}, 0, 20L);

		syncScheduler = Bukkit.getScheduler().runTaskTimerAsynchronously(DirtPlaytime.getPlugin(), () -> {
			for (UUID uuid : playerTracker.keySet()) {
				Player player = playerTracker.get(uuid);

				DatabaseOperations.updatePlayer(player);
			}
		}, 0, Utilities.config.general.saveInterval * 20L);

		rankupScheduler = Bukkit.getScheduler().runTaskTimerAsynchronously(DirtPlaytime.getPlugin(), () -> {
			List<Rank> ranks = Utilities.config.ranks;
			ranks.sort(Comparator.comparing(Rank::getTimeRequirement));

			for (UUID uuid : onlinePlayers) {
				Player player = playerTracker.get(uuid);
				Rank currentRank = ranks.stream().filter(r -> r.name.equalsIgnoreCase(player.getCurrentPath())).findFirst().orElse(null);
				Rank nextRank = ranks.stream().filter(r -> r.name.equalsIgnoreCase(currentRank.nextRank)).findFirst().orElse(null);

				// Last rank check
				if (nextRank == null) continue;

				if (player.getTimePlayed() >= nextRank.getTimeRequirement()) {
					if (Utilities.config.general.announceRankUpsInChat) {
						Bukkit.broadcastMessage(Strings.RANKUP_MESSAGE
								.replace("{PLAYER}", player.getUsername())
								.replace("{RANK}", nextRank.getDisplayName()));
					}

					if (Utilities.config.general.announceRankUpsInTitle) {
						org.bukkit.entity.Player p = Bukkit.getPlayer(uuid);
						if (p != null) {
							p.sendTitle(Strings.RANKUP_TITLE.replace("{RANK}", nextRank.getDisplayName()), nextRank.getDisplayName(), 10, 70, 20);
						}
					}

					if (Utilities.config.sounds.playRankUpSound) {
						Utilities.playRankUpSound(Bukkit.getPlayer(uuid));
					}

					for (String command : nextRank.commands) {
						Bukkit.getScheduler().runTask(DirtPlaytime.getPlugin(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("{PLAYER}", player.getUsername())));
					}

					if (nextRank.money > 0) {
						DirtPlaytime.getEcon().depositPlayer(Bukkit.getPlayer(uuid), nextRank.money);
						Bukkit.getPlayer(uuid).spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Strings.MONEY_REWARD.replace("{AMOUNT}", String.valueOf(nextRank.money))));
					}

					player.setCurrentPath(nextRank.name);
					DatabaseOperations.updatePlayer(player);
				}
			}
		}, 0, 20L);
	}

	public static void addOnlinePlayer(UUID uuid) {
		onlinePlayers.add(uuid);
	}

	public static void removeOnlinePlayer(UUID uuid) {
		onlinePlayers.remove(uuid);
	}

	public static Map<UUID, Player> getPlayerTracker() {
		return playerTracker;
	}

	public static void restartSchedulers() {
		playtimeScheduler.cancel();
		syncScheduler.cancel();
		rankupScheduler.cancel();
		startSchedulers();
	}
}
