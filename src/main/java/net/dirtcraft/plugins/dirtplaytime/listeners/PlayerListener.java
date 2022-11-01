package net.dirtcraft.plugins.dirtplaytime.listeners;

import net.dirtcraft.plugins.dirtplaytime.data.PlaytimeManager;
import net.dirtcraft.plugins.dirtplaytime.database.DatabaseOperations;
import net.dirtcraft.plugins.dirtplaytime.database.callbacks.PlayerExistCallback;
import net.dirtcraft.plugins.dirtplaytime.utils.Utilities;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.logging.Level;

public class PlayerListener implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		DatabaseOperations.playerExists(event.getPlayer().getUniqueId(), event.getPlayer().getName(), new PlayerExistCallback() {
			@Override
			public void onExist() {
				if (Utilities.config.general.debug) {
					Utilities.log(Level.INFO, ChatColor.YELLOW + "Now tracking playtime for " + ChatColor.GOLD + event.getPlayer().getName());
				}
				PlaytimeManager.addOnlinePlayer(event.getPlayer().getUniqueId());
				String username = PlaytimeManager.getPlayerTracker().get(event.getPlayer().getUniqueId()).getUsername();
				PlaytimeManager.getPlayerTracker().get(event.getPlayer().getUniqueId()).addTimesJoined();

				if (username.equals(event.getPlayer().getName())) return;
				PlaytimeManager.getPlayerTracker().get(event.getPlayer().getUniqueId()).setUsername(event.getPlayer().getName());
			}

			@Override
			public void onNotExist() {
				if (Utilities.config.general.debug) {
					Utilities.log(Level.INFO, ChatColor.YELLOW + "Now tracking playtime for new player " + ChatColor.GOLD + event.getPlayer().getName());
				}
				PlaytimeManager.createPlayer(event.getPlayer().getUniqueId(), event.getPlayer().getName());
			}
		});
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		if (Utilities.config.general.debug) {
			Utilities.log(Level.INFO, ChatColor.YELLOW + "No longer tracking playtime for " + ChatColor.GOLD + event.getPlayer().getName());
		}
		PlaytimeManager.removeOnlinePlayer(event.getPlayer().getUniqueId());
	}
}
