package net.dirtcraft.plugins.dirtplaytime.commands;

import net.dirtcraft.plugins.dirtplaytime.data.PlaytimeManager;
import net.dirtcraft.plugins.dirtplaytime.utils.Permissions;
import net.dirtcraft.plugins.dirtplaytime.utils.Strings;
import net.dirtcraft.plugins.dirtplaytime.utils.Utilities;
import org.bukkit.command.CommandSender;

public class ReloadCommand {
	public static boolean run(CommandSender sender) {
		if (!sender.hasPermission(Permissions.RELOAD)) {
			sender.sendMessage(Strings.NO_PERMISSION);
			return true;
		}

		Utilities.loadConfig();
		PlaytimeManager.restartSchedulers();
		sender.sendMessage(Strings.RELOAD);
		return true;
	}
}
