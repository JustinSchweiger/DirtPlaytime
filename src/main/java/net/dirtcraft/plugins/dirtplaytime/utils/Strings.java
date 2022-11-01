package net.dirtcraft.plugins.dirtplaytime.utils;

import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.UUID;

public class Strings {
	public static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.RED + "Dirt" + ChatColor.AQUA + "Playtime" + ChatColor.GRAY + "] ";
	public static final String INTERNAL_PREFIX = ChatColor.GRAY + "[" + ChatColor.RED + "Dirt" + ChatColor.AQUA + "Playtime" + ChatColor.GRAY + "] ";
	public static final String NO_PERMISSION = PREFIX + ChatColor.RED + "You do not have permission to use this command.\n";
	public static final String RELOAD = PREFIX + ChatColor.GREEN + "Config reloaded successfully.";
	public static final String BAR_TOP = Utilities.format("&x&f&b&7&9&0&0&m-&x&f&b&7&0&0&0&m-&x&f&a&6&8&0&0&m-&x&f&a&5&f&0&0&m-&x&f&a&5&6&0&0&m-&x&f&a&4&e&0&0&m-&x&f&9&4&5&0&0&m-&x&f&9&3&d&0&0&m-&x&f&9&3&4&0&0&m-&x&f&8&2&b&0&0&m-&x&f&8&2&3&0&0&m-&x&f&8&1&a&0&0&m-&x&f&8&1&1&0&0&m-&x&f&7&0&9&0&0&m-&x&f&7&0&0&0&0&m-" + ChatColor.GRAY + "[ " + ChatColor.RED + "DirtCraft " + ChatColor.AQUA + "Playtime" + ChatColor.RESET + ChatColor.GRAY + " ]" + "&x&f&7&0&0&0&0&m-&x&f&7&0&9&0&0&m-&x&f&8&1&1&0&0&m-&x&f&8&1&a&0&0&m-&x&f&8&2&3&0&0&m-&x&f&8&2&b&0&0&m-&x&f&9&3&4&0&0&m-&x&f&9&3&d&0&0&m-&x&f&9&4&5&0&0&m-&x&f&a&4&e&0&0&m-&x&f&a&5&6&0&0&m-&x&f&a&5&f&0&0&m-&x&f&a&6&8&0&0&m-&x&f&b&7&0&0&0&m-&x&f&b&7&9&0&0&m-");
	public static final String BAR_BOTTOM = Utilities.format("&x&f&b&7&9&0&0&m-&x&f&b&7&4&0&0&m-&x&f&b&6&e&0&0&m-&x&f&a&6&9&0&0&m-&x&f&a&6&4&0&0&m-&x&f&a&5&f&0&0&m-&x&f&a&5&9&0&0&m-&x&f&a&5&4&0&0&m-&x&f&a&4&f&0&0&m-&x&f&9&4&a&0&0&m-&x&f&9&4&4&0&0&m-&x&f&9&3&f&0&0&m-&x&f&9&3&a&0&0&m-&x&f&9&3&5&0&0&m-&x&f&9&2&f&0&0&m-&x&f&8&2&a&0&0&m-&x&f&8&2&5&0&0&m-&x&f&8&2&0&0&0&m-&x&f&8&1&a&0&0&m-&x&f&8&1&5&0&0&m-&x&f&8&1&0&0&0&m-&x&f&7&0&b&0&0&m-&x&f&7&0&5&0&0&m-&x&f&7&0&0&0&0&m-&x&f&7&0&0&0&0&m-&x&f&7&0&5&0&0&m-&x&f&7&0&b&0&0&m-&x&f&8&1&0&0&0&m-&x&f&8&1&5&0&0&m-&x&f&8&1&a&0&0&m-&x&f&8&2&0&0&0&m-&x&f&8&2&5&0&0&m-&x&f&8&2&a&0&0&m-&x&f&9&2&f&0&0&m-&x&f&9&3&5&0&0&m-&x&f&9&3&a&0&0&m-&x&f&9&3&f&0&0&m-&x&f&9&4&4&0&0&m-&x&f&9&4&a&0&0&m-&x&f&a&4&f&0&0&m-&x&f&a&5&4&0&0&m-&x&f&a&5&9&0&0&m-&x&f&a&5&f&0&0&m-&x&f&a&6&4&0&0&m-&x&f&a&6&9&0&0&m-&x&f&b&6&e&0&0&m-&x&f&b&7&4&0&0&m-&x&f&b&7&9&0&0&m-");
	public static final String HELP_PLAYTIME = ChatColor.GOLD + "  /dpt " + ChatColor.YELLOW + "check\n";
	public static final String HELP_PLAYTIME_OTHER = ChatColor.GOLD + "  /dpt " + ChatColor.YELLOW + "check [player]\n";
	public static final String HELP_TOP = ChatColor.GOLD + "  /dpt " + ChatColor.YELLOW + "top [page]\n";
	public static final String HELP_RELOAD = ChatColor.GOLD + "  /dpt " + ChatColor.YELLOW + "reload\n";
	public static final String HELP_ADD = ChatColor.GOLD + "  /dpt " + ChatColor.YELLOW + "add <player> <time> <seconds | minutes | hours>\n";
	public static final String HELP_REMOVE = ChatColor.GOLD + "  /dpt " + ChatColor.YELLOW + "remove <player> <time> <seconds | minutes | hours>\n";
	public static final String HELP_SET = ChatColor.GOLD + "  /dpt " + ChatColor.YELLOW + "set <player> <time> <seconds | minutes | hours>\n";
	public static final String INVALID_NUMBER_OF_ARGUMENTS_FOR_CONSOLE = PREFIX + ChatColor.RED + "Invalid number of arguments for this command! Use /dpt check <player> in the console!";
	public static final String PLAYER_NOT_FOUND = PREFIX + ChatColor.RED + "Player not found!";
	public static final String INVALID_ARGUMENTS = PREFIX + ChatColor.RED + "Invalid arguments! Use /dpt help for more information!";
	public static final String TIME_MUST_BE_INTEGER = PREFIX + ChatColor.RED + "The time must be an number!";
	public static final String INVALID_TIME_UNIT = PREFIX + ChatColor.RED + "Invalid time unit! Use seconds, minutes or hours!";
	public static final String TIME_ADDED = PREFIX + ChatColor.GREEN + "Added " + ChatColor.YELLOW + "{TIME} {UNIT}" + ChatColor.GREEN + " to " + ChatColor.AQUA + "{PLAYER}" + ChatColor.GREEN + "'s playtime!";
	public static final String TIME_REMOVED = PREFIX + ChatColor.GREEN + "Removed " + ChatColor.YELLOW + "{TIME} {UNIT}" + ChatColor.GREEN + " from " + ChatColor.AQUA + "{PLAYER}" + ChatColor.GREEN + "'s playtime!";
	public static final String TIME_SET = PREFIX + ChatColor.GREEN + "Set " + ChatColor.AQUA + "{PLAYER}" + ChatColor.GREEN + "'s playtime to " + ChatColor.YELLOW + "{TIME} {UNIT}" + ChatColor.GREEN + "!";
	public static final String RANKUP_MESSAGE = PREFIX + ChatColor.DARK_AQUA + "{PLAYER}" + ChatColor.GRAY + " just ranked up to {RANK}" + ChatColor.GRAY + "!";
	public static final String RANKUP_TITLE = ChatColor.AQUA + "You just ranked up to";
	public static final String MONEY_REWARD = PREFIX + ChatColor.GREEN + "You received " + ChatColor.YELLOW + "{AMOUNT}$" + ChatColor.GREEN + " for ranking up!";
	public static final String PAGE_NEEDS_TO_BE_INTEGER = PREFIX + ChatColor.RED + "The page number must be a number!";
	public static final String PAGE_INDEX_OUT_OF_BOUNDS = PREFIX + ChatColor.RED + "The page number is too big!";
}
