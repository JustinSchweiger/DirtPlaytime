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
		DatabaseOperations.playerExists(event.getPlayer().getUniqueId(), new PlayerExistCallback() {
			@Override
			public void onExist() {
				Utilities.log(Level.INFO, ChatColor.YELLOW + "Now tracking playtime for " + ChatColor.GOLD + event.getPlayer().getName());
				DatabaseOperations.updatePlayerMeta(event.getPlayer().getUniqueId(), () -> PlaytimeManager.addOnlinePlayer(event.getPlayer()));
			}

			@Override
			public void onNotExist() {
				Utilities.log(Level.INFO, ChatColor.YELLOW + "Now tracking playtime for new player " + ChatColor.GOLD + event.getPlayer().getName());
				DatabaseOperations.createPlayer(event.getPlayer().getUniqueId(), () -> PlaytimeManager.addOnlinePlayer(event.getPlayer()));
			}
		});
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		PlaytimeManager.removeOnlinePlayer(event.getPlayer());
		Utilities.log(Level.INFO, ChatColor.YELLOW + "No longer tracking playtime for " + ChatColor.GOLD + event.getPlayer().getName());
	}
}
