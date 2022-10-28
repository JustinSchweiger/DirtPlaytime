package net.dirtcraft.plugins.dirtplaytime;

import net.dirtcraft.plugins.dirtplaytime.data.PlaytimeManager;
import net.dirtcraft.plugins.dirtplaytime.database.Database;
import net.dirtcraft.plugins.dirtplaytime.utils.Utilities;
import org.bukkit.plugin.java.JavaPlugin;

public final class DirtPlaytime extends JavaPlugin {

	private static DirtPlaytime plugin;

	public static DirtPlaytime getPlugin() {
		return plugin;
	}

	@Override
	public void onEnable() {
		plugin = this;
		Utilities.loadConfig();
		Database.initialiseDatabase();
		Utilities.registerCommands();
		Utilities.registerListeners();
		PlaytimeManager.startPlaytimeScheduler();
	}

	@Override
	public void onDisable() {
		Database.closeDatabase();
	}
}
