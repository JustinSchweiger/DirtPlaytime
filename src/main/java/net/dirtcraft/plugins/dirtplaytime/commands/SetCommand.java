package net.dirtcraft.plugins.dirtplaytime.commands;

import net.dirtcraft.plugins.dirtplaytime.data.Player;
import net.dirtcraft.plugins.dirtplaytime.data.PlaytimeManager;
import net.dirtcraft.plugins.dirtplaytime.utils.Permissions;
import net.dirtcraft.plugins.dirtplaytime.utils.Strings;
import net.dirtcraft.plugins.dirtplaytime.utils.Utilities;
import org.bukkit.command.CommandSender;

public class SetCommand {

	public static boolean run(CommandSender sender, String[] args) {
		if (!sender.hasPermission(Permissions.SET)) {
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
		int timeToSet = Integer.parseInt(args[2]);
		String timeUnit = args[3].toLowerCase();

		Player target = PlaytimeManager.getPlayerTracker().values().stream().filter(player -> player.getUsername().equalsIgnoreCase(username)).findFirst().orElse(null);

		if (target == null) {
			sender.sendMessage(Strings.PLAYER_NOT_FOUND);
			return true;
		}

		switch (timeUnit) {
			case "seconds":
				target.setTimePlayed(timeToSet);
				break;
			case "minutes":
				target.setTimePlayed(timeToSet * 60L);
				break;
			case "hours":
				target.setTimePlayed(timeToSet * 3600L);
				break;
		}

		sender.sendMessage(Strings.TIME_SET.replace("{TIME}", String.valueOf(timeToSet)).replace("{UNIT}", timeUnit).replace("{PLAYER}", target.getUsername()));

		return true;
	}
}
