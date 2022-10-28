package net.dirtcraft.plugins.dirtplaytime.utils;

import org.bukkit.ChatColor;

public class Strings {
	public static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.RED + "Dirt" + ChatColor.AQUA + "Playtime" + ChatColor.GRAY + "] ";
	public static final String INTERNAL_PREFIX = ChatColor.GRAY + "[" + ChatColor.RED + "Dirt" + ChatColor.AQUA + "Playtime" + ChatColor.GRAY + "] ";
	public static final String NO_PERMISSION = PREFIX + ChatColor.RED + "You do not have permission to use this command.\n";
	public static final String RELOAD = PREFIX + ChatColor.GREEN + "Config reloaded successfully.";
	public static final String BAR_TOP = Utilities.format("&x&f&b&7&9&0&0&m-&x&f&b&7&0&0&0&m-&x&f&a&6&8&0&0&m-&x&f&a&5&f&0&0&m-&x&f&a&5&6&0&0&m-&x&f&a&4&e&0&0&m-&x&f&9&4&5&0&0&m-&x&f&9&3&d&0&0&m-&x&f&9&3&4&0&0&m-&x&f&8&2&b&0&0&m-&x&f&8&2&3&0&0&m-&x&f&8&1&a&0&0&m-&x&f&8&1&1&0&0&m-&x&f&7&0&9&0&0&m-&x&f&7&0&0&0&0&m-" + ChatColor.GRAY + "[ " + ChatColor.RED + "DirtCraft " + ChatColor.AQUA + "Playtime" + ChatColor.RESET + ChatColor.GRAY + " ]" + "&x&f&7&0&0&0&0&m-&x&f&7&0&9&0&0&m-&x&f&8&1&1&0&0&m-&x&f&8&1&a&0&0&m-&x&f&8&2&3&0&0&m-&x&f&8&2&b&0&0&m-&x&f&9&3&4&0&0&m-&x&f&9&3&d&0&0&m-&x&f&9&4&5&0&0&m-&x&f&a&4&e&0&0&m-&x&f&a&5&6&0&0&m-&x&f&a&5&f&0&0&m-&x&f&a&6&8&0&0&m-&x&f&b&7&0&0&0&m-&x&f&b&7&9&0&0&m-");
	public static final String BAR_BOTTOM = Utilities.format("&x&f&b&7&9&0&0&m-&x&f&b&7&4&0&0&m-&x&f&b&6&e&0&0&m-&x&f&a&6&9&0&0&m-&x&f&a&6&4&0&0&m-&x&f&a&5&f&0&0&m-&x&f&a&5&9&0&0&m-&x&f&a&5&4&0&0&m-&x&f&a&4&f&0&0&m-&x&f&9&4&a&0&0&m-&x&f&9&4&4&0&0&m-&x&f&9&3&f&0&0&m-&x&f&9&3&a&0&0&m-&x&f&9&3&5&0&0&m-&x&f&9&2&f&0&0&m-&x&f&8&2&a&0&0&m-&x&f&8&2&5&0&0&m-&x&f&8&2&0&0&0&m-&x&f&8&1&a&0&0&m-&x&f&8&1&5&0&0&m-&x&f&8&1&0&0&0&m-&x&f&7&0&b&0&0&m-&x&f&7&0&5&0&0&m-&x&f&7&0&0&0&0&m-&x&f&7&0&0&0&0&m-&x&f&7&0&5&0&0&m-&x&f&7&0&b&0&0&m-&x&f&8&1&0&0&0&m-&x&f&8&1&5&0&0&m-&x&f&8&1&a&0&0&m-&x&f&8&2&0&0&0&m-&x&f&8&2&5&0&0&m-&x&f&8&2&a&0&0&m-&x&f&9&2&f&0&0&m-&x&f&9&3&5&0&0&m-&x&f&9&3&a&0&0&m-&x&f&9&3&f&0&0&m-&x&f&9&4&4&0&0&m-&x&f&9&4&a&0&0&m-&x&f&a&4&f&0&0&m-&x&f&a&5&4&0&0&m-&x&f&a&5&9&0&0&m-&x&f&a&5&f&0&0&m-&x&f&a&6&4&0&0&m-&x&f&a&6&9&0&0&m-&x&f&b&6&e&0&0&m-&x&f&b&7&4&0&0&m-&x&f&b&7&9&0&0&m-");
	public static final String NO_CONSOLE = PREFIX + ChatColor.RED + "This command cannot be used from the console!";
	public static final String HELP_PLAYTIME = ChatColor.GOLD + "  /dpt " + ChatColor.YELLOW + "check\n";
	public static final String HELP_PLAYTIME_OTHER = ChatColor.GOLD + "  /dpt " + ChatColor.YELLOW + "check [player]\n";
	public static final String HELP_TOP = ChatColor.GOLD + "  /dpt " + ChatColor.YELLOW + "top\n";
	public static final String HELP_RELOAD = ChatColor.GOLD + "  /dpt " + ChatColor.YELLOW + "reload\n";
	public static final String HELP_ADD = ChatColor.GOLD + "  /dpt " + ChatColor.YELLOW + "add [player] [time]\n";
	public static final String HELP_REMOVE = ChatColor.GOLD + "  /dpt " + ChatColor.YELLOW + "remove [player] [time]\n";
	public static final String HELP_SET = ChatColor.GOLD + "  /dpt " + ChatColor.YELLOW + "set [player] [time]\n";
	public static final String INVALID_NUMBER_OF_ARGUMENTS_FOR_CONSOLE = PREFIX + ChatColor.RED + "Invalid number of arguments for this command! Use /dpt check <player> in the console!";
}
