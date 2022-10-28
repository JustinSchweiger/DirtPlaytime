package net.dirtcraft.plugins.dirtplaytime.utils;

import com.moandjiezana.toml.Toml;
import net.dirtcraft.plugins.dirtplaytime.DirtPlaytime;
import net.dirtcraft.plugins.dirtplaytime.commands.BaseCommand;
import net.dirtcraft.plugins.dirtplaytime.config.Config;
import net.dirtcraft.plugins.dirtplaytime.listeners.PlayerListener;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;

public class Utilities {
	public static Config config;

	public static String format(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public static void loadConfig() {
		if (!DirtPlaytime.getPlugin().getDataFolder().exists()) {
			DirtPlaytime.getPlugin().getDataFolder().mkdirs();
		}
		File file = new File(DirtPlaytime.getPlugin().getDataFolder(), "config.toml");
		if (!file.exists()) {
			try {
				Files.copy(DirtPlaytime.getPlugin().getResource("config.toml"), file.toPath());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		config = new Toml(new Toml().read(DirtPlaytime.getPlugin().getResource("config.toml"))).read(file).to(Config.class);
	}

	public static void registerCommands() {
		DirtPlaytime.getPlugin().getCommand("dirtplaytime").setExecutor(new BaseCommand());
		DirtPlaytime.getPlugin().getCommand("dirtplaytime").setTabCompleter(new BaseCommand());
	}

	public static void registerListeners() {
		DirtPlaytime.getPlugin().getServer().getPluginManager().registerEvents(new PlayerListener(), DirtPlaytime.getPlugin());
	}

	public static void log(Level level, String msg) {
		String consoleMessage;
		if (Level.INFO.equals(level)) {
			consoleMessage = Strings.INTERNAL_PREFIX + ChatColor.WHITE + msg;
		} else if (Level.WARNING.equals(level)) {
			consoleMessage = Strings.INTERNAL_PREFIX + ChatColor.YELLOW + msg;
		} else if (Level.SEVERE.equals(level)) {
			consoleMessage = Strings.INTERNAL_PREFIX + ChatColor.RED + msg;
		} else {
			consoleMessage = Strings.INTERNAL_PREFIX + ChatColor.GRAY + msg;
		}

		if (!config.general.coloredDebug) {
			consoleMessage = ChatColor.stripColor(msg);
		}

		DirtPlaytime.getPlugin().getServer().getConsoleSender().sendMessage(consoleMessage);
	}

	public static void disablePlugin() {
		DirtPlaytime.getPlugin().getServer().getPluginManager().disablePlugin(DirtPlaytime.getPlugin());
	}

	public static void playRankUpSound(CommandSender sender) {
		if (!(sender instanceof Player)) return;

		Player player = (Player) sender;

		if (Utilities.config.sounds.playRankUpSound) {
			String sound = Utilities.config.sounds.rankUpSound;
			if (sound == null) {
				sound = "minecraft:ui.toast.challenge_complete";
			}
			player.playSound(player.getLocation(), sound, 1, 1);
		}
	}
}
