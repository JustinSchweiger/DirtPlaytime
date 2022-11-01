package net.dirtcraft.plugins.dirtplaytime.database;

import net.dirtcraft.plugins.dirtplaytime.DirtPlaytime;
import net.dirtcraft.plugins.dirtplaytime.data.Player;
import net.dirtcraft.plugins.dirtplaytime.database.callbacks.GetPlayersCallback;
import net.dirtcraft.plugins.dirtplaytime.database.callbacks.PlayerExistCallback;
import net.dirtcraft.plugins.dirtplaytime.database.callbacks.UpdatePlayerCallback;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DatabaseOperations {
	public static void playerExists(final UUID uuid, final String username, final PlayerExistCallback playerExistCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtPlaytime.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement statement = connection.prepareStatement("SELECT * FROM players WHERE uuid = ?");
				 PreparedStatement updateName = connection.prepareStatement("UPDATE players SET username = ? WHERE uuid = ?")) {
				statement.setString(1, uuid.toString());
				ResultSet resultSet = statement.executeQuery();

				if (resultSet.next()) {
					Bukkit.getScheduler().runTask(DirtPlaytime.getPlugin(), playerExistCallback::onExist);
					updateName.setString(1, username);
					updateName.setString(2, uuid.toString());
					updateName.executeUpdate();
				} else {
					Bukkit.getScheduler().runTask(DirtPlaytime.getPlugin(), playerExistCallback::onNotExist);
				}
			} catch (SQLException e) { e.printStackTrace(); }
		});
	}

	public static void createPlayer(final UUID uniqueId, final String username, final UpdatePlayerCallback updatePlayerCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtPlaytime.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement statement = connection.prepareStatement("INSERT INTO players (uuid, username, current_path, first_joined) VALUES (?, ?, ?, ?)")) {
				statement.setString(1, uniqueId.toString());
				statement.setString(2, username);
				statement.setString(3, "starter");
				statement.setString(4, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
				statement.executeUpdate();

				Bukkit.getScheduler().runTask(DirtPlaytime.getPlugin(), updatePlayerCallback::onSuccess);
			} catch (SQLException e) { e.printStackTrace(); }
		});
	}

	public static void getPlayers(final GetPlayersCallback getPlayersCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtPlaytime.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement statement = connection.prepareStatement("SELECT * FROM players")) {
				ResultSet resultSet = statement.executeQuery();

				Map<UUID, Player> players = new HashMap<>();
				while (resultSet.next()) {
					Player player = new Player(
							UUID.fromString(resultSet.getString("uuid")),
							resultSet.getString("username"),
							resultSet.getString("current_path"),
							resultSet.getLong("time_played"),
							resultSet.getInt("times_joined"),
							LocalDateTime.parse(resultSet.getString("first_joined"))
					);

					players.put(player.getUuid(), player);
				}

				Bukkit.getScheduler().runTask(DirtPlaytime.getPlugin(), () -> getPlayersCallback.onSuccess(players));
			} catch (SQLException ignored) {}
		});
	}

	public static void updatePlayer(final Player player) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtPlaytime.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement statement = connection.prepareStatement("UPDATE players SET time_played = ?, times_joined = ?, current_path = ? WHERE uuid = ?")) {
				statement.setLong(1, player.getTimePlayed());
				statement.setInt(2, player.getTimesJoined());
				statement.setString(3, player.getCurrentPath());
				statement.setString(4, player.getUuid().toString());
				statement.executeUpdate();
			} catch (SQLException e) { e.printStackTrace(); }
		});
	}
}
