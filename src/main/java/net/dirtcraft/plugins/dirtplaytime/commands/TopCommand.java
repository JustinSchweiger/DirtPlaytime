package net.dirtcraft.plugins.dirtplaytime.commands;

import net.dirtcraft.plugins.dirtplaytime.data.Player;
import net.dirtcraft.plugins.dirtplaytime.data.PlaytimeManager;
import net.dirtcraft.plugins.dirtplaytime.utils.Permissions;
import net.dirtcraft.plugins.dirtplaytime.utils.Strings;
import net.dirtcraft.plugins.dirtplaytime.utils.Utilities;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TopCommand {
	public static final String[] leaderboardChars = new String[]{
			"\u2776",
			"\u2777",
			"\u2778",
			"\u2779",
			"\u277A",
			"\u277B",
			"\u277C",
			"\u277D",
			"\u277E",
			"\u277F",
			"\u24eb",
			"\u24ec",
			"\u24ed",
			"\u24ee",
			"\u24ef"
	};

	public static boolean run(CommandSender sender) {
		if (!sender.hasPermission(Permissions.TOP)) {
			sender.sendMessage(Strings.NO_PERMISSION);
			return true;
		}

		List<Player> players = new ArrayList<>(PlaytimeManager.getPlayerTracker().values());
		players.sort(Comparator.comparing(Player::getTimePlayed).reversed());

		sender.sendMessage("");
		sender.sendMessage(Strings.BAR_TOP);
		sender.sendMessage("");
		sender.sendMessage(ChatColor.AQUA + "Top " + ChatColor.DARK_AQUA + Math.min(Utilities.config.general.leaderboardSize, 20) + ChatColor.AQUA + " players playtime:");
		sender.sendMessage("");
		for (int i = 0; i < Math.min(Utilities.config.general.leaderboardSize, 20); i++) {
			if (i >= players.size())
				break;

			Player player = players.get(i);
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

			BaseComponent[] playtimeComponent = new ComponentBuilder("")
					.append("  " + ChatColor.GREEN + leaderboardChars[i] + " " + ChatColor.AQUA + player.getUsername())
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(
							ChatColor.AQUA + "Playtime" + ChatColor.DARK_AQUA + ChatColor.BOLD + " \u226b " + ChatColor.GRAY + builder + "\n" +
									ChatColor.AQUA + "Times Joined" + ChatColor.DARK_AQUA + ChatColor.BOLD + " \u226b " + ChatColor.GRAY + player.getTimesJoined() + "\n" +
									ChatColor.AQUA + "First Joined" + ChatColor.DARK_AQUA + ChatColor.BOLD + " \u226b " + ChatColor.GRAY + player.getFirstJoined().format(DateTimeFormatter.ofPattern("dd. MMMM yyyy")) + ChatColor.DARK_AQUA + " at " + ChatColor.GRAY + player.getFirstJoined().format(DateTimeFormatter.ofPattern("HH:mm"))
					))).create();

			sender.spigot().sendMessage(playtimeComponent);
		}

		sender.sendMessage("");
		sender.sendMessage(Strings.BAR_BOTTOM);
		sender.sendMessage("");

		return true;
	}
}
