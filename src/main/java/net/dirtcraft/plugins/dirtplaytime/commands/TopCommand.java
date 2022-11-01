package net.dirtcraft.plugins.dirtplaytime.commands;

import net.dirtcraft.plugins.dirtplaytime.data.Player;
import net.dirtcraft.plugins.dirtplaytime.data.PlaytimeManager;
import net.dirtcraft.plugins.dirtplaytime.utils.Permissions;
import net.dirtcraft.plugins.dirtplaytime.utils.Strings;
import net.dirtcraft.plugins.dirtplaytime.utils.Utilities;
import net.dirtcraft.plugins.dirtplaytime.utils.gradient.GradientHandler;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TopCommand {
	public static boolean run(CommandSender sender, String[] args) {
		if (!sender.hasPermission(Permissions.TOP)) {
			sender.sendMessage(Strings.NO_PERMISSION);
			return true;
		}

		List<Player> players = new ArrayList<>(PlaytimeManager.getPlayerTracker().values());
		players.sort(Comparator.comparing(Player::getTimePlayed).reversed());

		if (args.length > 1 && !Utilities.isInteger(args[1])) {
			sender.sendMessage(Strings.PAGE_NEEDS_TO_BE_INTEGER);
			return true;
		}

		int listEntries = Math.min(Utilities.config.general.leaderboardSize, 15);
		int page = args.length > 1 ? Integer.parseInt(args[1]) : 1;

		int maxPages = (int) Math.ceil((double) PlaytimeManager.getPlayerTracker().size() / (double) listEntries);
		if (page > maxPages || page < 1) {
			sender.sendMessage(Strings.PAGE_INDEX_OUT_OF_BOUNDS + " Index must be smaller or equal to: " + maxPages);
			return true;
		}

		int start = (page - 1) * listEntries;
		int end = page * listEntries;
		if (end > PlaytimeManager.getPlayerTracker().size()) {
			end = PlaytimeManager.getPlayerTracker().size();
		}

		sender.sendMessage("");
		sender.sendMessage(Strings.BAR_TOP);
		sender.sendMessage("");
		sender.sendMessage(ChatColor.AQUA + "Top " + ChatColor.DARK_AQUA + (start + 1) + ChatColor.RED + " - " + ChatColor.DARK_AQUA + end + ChatColor.AQUA + " players playtime:");
		sender.sendMessage("");
		for (int i = start; i < end; i++) {
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
					.append("  " + ChatColor.GREEN + (i + 1) + ". " + ChatColor.AQUA + player.getUsername())
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(
							ChatColor.AQUA + "Playtime" + ChatColor.DARK_AQUA + ChatColor.BOLD + " \u226b " + ChatColor.GRAY + builder + "\n" +
									ChatColor.AQUA + "Times Joined" + ChatColor.DARK_AQUA + ChatColor.BOLD + " \u226b " + ChatColor.GRAY + player.getTimesJoined() + "\n" +
									ChatColor.AQUA + "First Joined" + ChatColor.DARK_AQUA + ChatColor.BOLD + " \u226b " + ChatColor.GRAY + player.getFirstJoined().format(DateTimeFormatter.ofPattern("dd. MMMM yyyy")) + ChatColor.DARK_AQUA + " at " + ChatColor.GRAY + player.getFirstJoined().format(DateTimeFormatter.ofPattern("HH:mm"))
					))).create();

			sender.spigot().sendMessage(playtimeComponent);
		}

		sender.sendMessage("");
		TextComponent bottomBar = new TextComponent(TextComponent.fromLegacyText(GradientHandler.hsvGradient("--------------------", new java.awt.Color(251, 121, 0), new java.awt.Color(247, 0, 0), GradientHandler::linear, net.md_5.bungee.api.ChatColor.STRIKETHROUGH)));
		TextComponent pagePrev;
		if (page == 1) {
			pagePrev = new TextComponent(ChatColor.GRAY + "  \u25C0 ");
			pagePrev.setBold(true);
			pagePrev.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.RED + "You are already on the first page!")));
		} else {
			pagePrev = new TextComponent(ChatColor.GREEN + "  \u25C0 ");
			pagePrev.setBold(true);
			pagePrev.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + "Previous page")));
			pagePrev.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dpt top " + (page - 1)));
		}
		bottomBar.addExtra(pagePrev);
		TextComponent pageNext;
		if (page == maxPages) {
			pageNext = new TextComponent(ChatColor.GRAY + " \u25B6  ");
			pageNext.setBold(true);
			pageNext.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.RED + "You are already on the last page!")));
		} else {
			pageNext = new TextComponent(ChatColor.GREEN + " \u25B6  ");
			pageNext.setBold(true);
			pageNext.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + "Next page")));
			pageNext.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dpt top " + (page + 1)));
		}
		bottomBar.addExtra(pageNext);
		bottomBar.addExtra(new TextComponent(TextComponent.fromLegacyText(GradientHandler.hsvGradient("---------------------", new java.awt.Color(247, 0, 0), new java.awt.Color(251, 121, 0), GradientHandler::linear, net.md_5.bungee.api.ChatColor.STRIKETHROUGH))));
		sender.spigot().sendMessage(bottomBar);
		sender.sendMessage("");

		return true;
	}
}
