package net.dirtcraft.plugins.dirtplaytime.commands;

import net.dirtcraft.plugins.dirtplaytime.utils.Permissions;
import net.dirtcraft.plugins.dirtplaytime.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BaseCommand implements CommandExecutor, TabCompleter {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (!sender.hasPermission(Permissions.BASE)) {
			sender.sendMessage(Strings.NO_PERMISSION);
			return true;
		}

		if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help"))) {
			ArrayList<String> listings = getListings(sender);
			sender.sendMessage(Strings.BAR_TOP);
			sender.sendMessage("");
			for (String listing : listings) {
				sender.sendMessage(listing);
			}
			sender.sendMessage("");
			sender.sendMessage(Strings.BAR_BOTTOM);
			return true;
		}

		String arg = args[0].toLowerCase();

		switch (arg) {
			case "check":
				return PlaytimeCommand.run(sender, args);
			case "list":
				//return ListCommand.run(sender, args);
			case "add":
				//return AddCommand.run(sender, args);
			case "remove":
				//return RemoveCommand.run(sender, args);
			case "reload":
				//return ReloadCommand.run(sender, args);
		}

		return true;
	}

	private ArrayList<String> getListings(CommandSender sender) {
		ArrayList<String> listings = new ArrayList<>();

		if (sender.hasPermission(Permissions.PLAYTIME) && !sender.hasPermission(Permissions.PLAYTIME_OTHER)) {
			listings.add(Strings.HELP_PLAYTIME);
		}

		if (sender.hasPermission(Permissions.PLAYTIME_OTHER)) {
			listings.add(Strings.HELP_PLAYTIME_OTHER);
		}

		if (sender.hasPermission(Permissions.TOP)) {
			listings.add(Strings.HELP_TOP);
		}

		if (sender.hasPermission(Permissions.ADD)) {
			listings.add(Strings.HELP_ADD);
		}

		if (sender.hasPermission(Permissions.REMOVE)) {
			listings.add(Strings.HELP_REMOVE);
		}

		if (sender.hasPermission(Permissions.SET)) {
			listings.add(Strings.HELP_SET);
		}

		if (sender.hasPermission(Permissions.RELOAD)) {
			listings.add(Strings.HELP_RELOAD);
		}

		return listings;
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		List<String> arguments = new ArrayList<>();

		if (args.length == 1) {
			if (sender.hasPermission(Permissions.PLAYTIME)) {
				arguments.add("check");
			}

			if (sender.hasPermission(Permissions.TOP)) {
				arguments.add("top");
			}

			if (sender.hasPermission(Permissions.ADD)) {
				arguments.add("add");
			}

			if (sender.hasPermission(Permissions.REMOVE)) {
				arguments.add("remove");
			}

			if (sender.hasPermission(Permissions.SET)) {
				arguments.add("set");
			}

			if (sender.hasPermission(Permissions.RELOAD)) {
				arguments.add("reload");
			}
		}

		List<String> tabResults = new ArrayList<>();
		for (String argument : arguments) {
			if (argument.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
				tabResults.add(argument);
			}
		}

		return tabResults;
	}
}
