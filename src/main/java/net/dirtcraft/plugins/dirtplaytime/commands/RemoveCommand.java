package net.dirtcraft.plugins.dirtplaytime.commands;

import net.dirtcraft.plugins.dirtplaytime.data.Player;
import net.dirtcraft.plugins.dirtplaytime.data.PlaytimeManager;
import net.dirtcraft.plugins.dirtplaytime.utils.Permissions;
import net.dirtcraft.plugins.dirtplaytime.utils.Strings;
import net.dirtcraft.plugins.dirtplaytime.utils.Utilities;
import org.bukkit.command.CommandSender;

public class RemoveCommand {
	public static boolean run(CommandSender sender, String[] args) {
		if (!sender.hasPermission(Permissions.REMOVE)) {
			sender.sendMessage(Strings.NO_PERMISSION);
			return true;
		}

		if (args.length < 4) {
			sender.sendMessage(Strings.INVALID_ARGUMENTS);
			return true;
		}

		if (!Utilities.isInteger(args[2])) {
			sender.sendMessage(Strings.TIME_MUST_BE_INTEGER);
			return true;
		}

		if (!(args[3].equalsIgnoreCase("seconds") || args[3].equalsIgnoreCase("minutes") || args[3].equalsIgnoreCase("hours"))) {
			sender.sendMessage(Strings.INVALID_TIME_UNIT);
			return true;
		}

		String username = args[1];
		int timeToRemove = Integer.parseInt(args[2]);
		String timeUnit = args[3].toLowerCase();

		Player target = PlaytimeManager.getPlayerTracker().values().stream().filter(player -> player.getUsername().equalsIgnoreCase(username)).findFirst().orElse(null);

		if (target == null) {
			sender.sendMessage(Strings.PLAYER_NOT_FOUND);
			return true;
		}

		if (timeUnit.equalsIgnoreCase("seconds")) {
			if (timeToRemove > target.getTimePlayed()) {
				target.setTimePlayed(0);
			} else {
				target.removeTimePlayed(timeToRemove);
			}
		} else if (timeUnit.equalsIgnoreCase("minutes")) {
			if (timeToRemove * 60L > target.getTimePlayed()) {
				target.setTimePlayed(0);
			} else {
				target.removeTimePlayed(timeToRemove * 60L);
			}
		} else if (timeUnit.equalsIgnoreCase("hours")) {
			if (timeToRemove * 3600L > target.getTimePlayed()) {
				target.setTimePlayed(0);
			} else {
				target.removeTimePlayed(timeToRemove * 3600L);
			}
		}

		sender.sendMessage(Strings.TIME_REMOVED.replace("{TIME}", String.valueOf(timeToRemove)).replace("{UNIT}", timeUnit).replace("{PLAYER}", target.getUsername()));

		return true;
	}
}
