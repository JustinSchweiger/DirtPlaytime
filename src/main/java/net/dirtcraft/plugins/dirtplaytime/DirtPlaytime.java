package net.dirtcraft.plugins.dirtplaytime;

import net.dirtcraft.plugins.dirtplaytime.data.PlaytimeManager;
import net.dirtcraft.plugins.dirtplaytime.database.Database;
import net.dirtcraft.plugins.dirtplaytime.utils.Utilities;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class DirtPlaytime extends JavaPlugin {

	private static DirtPlaytime plugin;
	private static Economy econ = null;

	public static DirtPlaytime getPlugin() {
		return plugin;
	}

	public static Economy getEcon() {
		return econ;
	}

	@Override
	public void onEnable() {
		plugin = this;
		Utilities.loadConfig();
		Database.initialiseDatabase();
		PlaytimeManager.initialisePlayerTracker();
		Utilities.registerCommands();
		Utilities.registerListeners();
		PlaytimeManager.startSchedulers();

		if (!setupEconomy() ) {
			Utilities.log(Level.SEVERE, "Vault not found, disabling plugin");
			Utilities.disablePlugin();
		}
	}

	@Override
	public void onDisable() {
		Database.closeDatabase();
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return true;
	}
}
