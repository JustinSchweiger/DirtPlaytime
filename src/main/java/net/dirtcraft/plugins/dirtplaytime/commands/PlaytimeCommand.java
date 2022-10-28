package net.dirtcraft.plugins.dirtplaytime.commands;

import net.dirtcraft.plugins.dirtplaytime.database.DatabaseOperations;
import net.dirtcraft.plugins.dirtplaytime.utils.Strings;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.format.DateTimeFormatter;

public class PlaytimeCommand {
	public static boolean run(CommandSender sender, String[] args) {
		if (args.length == 1) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Strings.INVALID_NUMBER_OF_ARGUMENTS_FOR_CONSOLE);
				return false;
			}

			DatabaseOperations.getPlaytime(((Player) sender).getUniqueId(), player -> {
				int hours = (int) Math.floor(player.getTimePlayed() / 3600F);
				int minutes = (int) Math.floor((player.getTimePlayed() - (hours * 3600)) / 60F);
				int seconds = (int) Math.floor(player.getTimePlayed() % 60F);

				StringBuilder builder = new StringBuilder();
				if (hours == 1) builder.append(hours).append(" hour ");
				if (hours > 1) builder.append(hours).append(" hours ");
				if (minutes == 1) builder.append(minutes).append(" minute ");
				if (minutes > 1) builder.append(minutes).append(" minutes ");
				if (seconds == 1) builder.append(seconds).append(" second");
				if (seconds > 1) builder.append(seconds).append(" seconds");

				sender.sendMessage("");
				sender.sendMessage(Strings.PREFIX + ChatColor.AQUA + "Your stats are:");
				sender.sendMessage("");
				sender.sendMessage(ChatColor.AQUA + "Your Playtime" + ChatColor.DARK_AQUA + ChatColor.BOLD + " \u226b " + ChatColor.GRAY + builder);
				sender.sendMessage(ChatColor.AQUA + "Times Joined" + ChatColor.DARK_AQUA + ChatColor.BOLD + " \u226b " + ChatColor.GRAY + player.getTimesJoined());
				sender.sendMessage(ChatColor.AQUA + "First Joined" + ChatColor.DARK_AQUA + ChatColor.BOLD + " \u226b " + ChatColor.GRAY + player.getFirstJoined().format(DateTimeFormatter.ofPattern("dd. MMMM yyyy")) + ChatColor.DARK_AQUA + " at " + ChatColor.GRAY + player.getFirstJoined().format(DateTimeFormatter.ofPattern("HH:mm")));
				sender.sendMessage("");
			});
			return true;
		}

		return true;
	}
}
