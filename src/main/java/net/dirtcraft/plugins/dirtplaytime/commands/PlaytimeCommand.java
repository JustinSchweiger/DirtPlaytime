package net.dirtcraft.plugins.dirtplaytime.commands;

import net.dirtcraft.plugins.dirtplaytime.config.Rank;
import net.dirtcraft.plugins.dirtplaytime.data.PlaytimeManager;
import net.dirtcraft.plugins.dirtplaytime.utils.Permissions;
import net.dirtcraft.plugins.dirtplaytime.utils.Strings;
import net.dirtcraft.plugins.dirtplaytime.utils.Utilities;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.format.DateTimeFormatter;

public class PlaytimeCommand {
	public static boolean run(CommandSender sender, String[] args) {
		if (!sender.hasPermission(Permissions.PLAYTIME)) {
			sender.sendMessage(Strings.NO_PERMISSION);
			return true;
		}

		if (args.length == 1) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Strings.INVALID_NUMBER_OF_ARGUMENTS_FOR_CONSOLE);
				return false;
			}

			net.dirtcraft.plugins.dirtplaytime.data.Player player = PlaytimeManager.getPlayerTracker().get(((Player) sender).getUniqueId());
			showPlaytime(sender, player, false);
			return true;
		} else if (args.length == 2) {
			if (!sender.hasPermission(Permissions.PLAYTIME_OTHER)) {
				sender.sendMessage(Strings.NO_PERMISSION);
				return false;
			}

			net.dirtcraft.plugins.dirtplaytime.data.Player target = PlaytimeManager.getPlayerTracker()
					.values()
					.stream()
					.filter(player -> player.getUsername().equalsIgnoreCase(args[1]))
					.findFirst()
					.orElse(null);

			if (target == null) {
				sender.sendMessage(Strings.PLAYER_NOT_FOUND);
				return false;
			}

			showPlaytime(sender, target, true);
		}

		return true;
	}

	private static void showPlaytime(CommandSender sender, net.dirtcraft.plugins.dirtplaytime.data.Player player, boolean otherPlayer) {
		Rank currentRank = Utilities.config.ranks.stream().filter(r -> r.name.equalsIgnoreCase(player.getCurrentPath())).findFirst().orElse(null);
		Rank nextRank = Utilities.config.ranks.stream().filter(r -> r.name.equalsIgnoreCase(currentRank.nextRank)).findFirst().orElse(null);

		int hours = (int) Math.floor(player.getTimePlayed() / 3600F);
		int minutes = (int) Math.floor((player.getTimePlayed() - (hours * 3600)) / 60F);
		int seconds = (int) Math.floor(player.getTimePlayed() % 60F);

		StringBuilder builder = new StringBuilder();
		if (hours == 1)
			builder.append(hours).append(" hour ");
		if (hours > 1)
			builder.append(hours).append(" hours ");
		if (minutes == 1)
			builder.append(minutes).append(" minute ");
		if (minutes > 1)
			builder.append(minutes).append(" minutes ");
		if (seconds == 1)
			builder.append(seconds).append(" second");
		if (seconds > 1)
			builder.append(seconds).append(" seconds");

		sender.sendMessage("");

		if (otherPlayer) {
			sender.sendMessage(ChatColor.AQUA + player.getUsername() + ChatColor.DARK_AQUA + "'s Playtime");
		} else {
			sender.sendMessage(ChatColor.DARK_AQUA + "Your playtime:");
		}

		sender.sendMessage("");
		sender.sendMessage(ChatColor.AQUA + "Playtime" + ChatColor.DARK_AQUA + ChatColor.BOLD + " \u226b " + ChatColor.GRAY + builder);
		sender.sendMessage(ChatColor.AQUA + "Times Joined" + ChatColor.DARK_AQUA + ChatColor.BOLD + " \u226b " + ChatColor.GRAY + player.getTimesJoined());
		sender.sendMessage(ChatColor.AQUA + "First Joined" + ChatColor.DARK_AQUA + ChatColor.BOLD + " \u226b " + ChatColor.GRAY + player.getFirstJoined().format(DateTimeFormatter.ofPattern("dd. MMMM yyyy")) + ChatColor.DARK_AQUA + " at " + ChatColor.GRAY + player.getFirstJoined().format(DateTimeFormatter.ofPattern("HH:mm")));
		sender.sendMessage("");

		if (nextRank != null) {
			int remainingHours = (int) Math.floor((nextRank.getTimeRequirement() - player.getTimePlayed()) / 3600F);
			int remainingMinutes = (int) Math.floor((nextRank.getTimeRequirement() - player.getTimePlayed() - remainingHours * 3600F) / 60F);
			int remainingSeconds = (int) Math.floor((nextRank.getTimeRequirement() - player.getTimePlayed()) % 60F);

			StringBuilder remainingTimeBuilder = new StringBuilder();
			if (remainingHours == 1)
				remainingTimeBuilder.append(remainingHours).append(" hour ");
			if (remainingHours > 1)
				remainingTimeBuilder.append(remainingHours).append(" hours ");
			if (remainingMinutes == 1)
				remainingTimeBuilder.append(remainingMinutes).append(" minute ");
			if (remainingMinutes > 1)
				remainingTimeBuilder.append(remainingMinutes).append(" minutes ");
			if (remainingSeconds == 1)
				remainingTimeBuilder.append(remainingSeconds).append(" second");
			if (remainingSeconds > 1)
				remainingTimeBuilder.append(remainingSeconds).append(" seconds");

			sender.sendMessage(currentRank.getDisplayName() + ChatColor.DARK_AQUA + ChatColor.BOLD + " \u226b " + nextRank.getDisplayName() + ChatColor.DARK_AQUA + " in " + ChatColor.GRAY + remainingTimeBuilder);
		} else {
			sender.sendMessage(currentRank.getDisplayName() + ChatColor.DARK_AQUA + ChatColor.BOLD + " \u226b " + ChatColor.GRAY + "No more ranks!");
		}
	}
}
