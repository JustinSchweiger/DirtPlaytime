package net.dirtcraft.plugins.dirtplaytime.database;

import net.dirtcraft.plugins.dirtplaytime.DirtPlaytime;
import net.dirtcraft.plugins.dirtplaytime.data.Player;
import net.dirtcraft.plugins.dirtplaytime.database.callbacks.GetPlaytimeCallback;
import net.dirtcraft.plugins.dirtplaytime.database.callbacks.PlayerExistCallback;
import net.dirtcraft.plugins.dirtplaytime.database.callbacks.UpdatePlayerCallback;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.UUID;

public class DatabaseOperations {
	public static void playerExists(final UUID uuid, final PlayerExistCallback playerExistCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtPlaytime.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement statement = connection.prepareStatement("SELECT * FROM players WHERE uuid = ?")) {
				statement.setString(1, uuid.toString());
				ResultSet resultSet = statement.executeQuery();

				if (resultSet.next()) {
					Bukkit.getScheduler().runTask(DirtPlaytime.getPlugin(), playerExistCallback::onExist);
				} else {
					Bukkit.getScheduler().runTask(DirtPlaytime.getPlugin(), playerExistCallback::onNotExist);
				}
			} catch (SQLException e) { e.printStackTrace(); }
		});
	}

	public static void updatePlayerMeta(final UUID uniqueId, final UpdatePlayerCallback updatePlayerCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtPlaytime.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement statement = connection.prepareStatement("UPDATE players SET times_joined = times_joined + 1 WHERE uuid = ?")) {
				statement.setString(1, uniqueId.toString());
				statement.executeUpdate();

				Bukkit.getScheduler().runTask(DirtPlaytime.getPlugin(), updatePlayerCallback::onSuccess);
			} catch (SQLException e) { e.printStackTrace(); }
		});
	}

	public static void createPlayer(final UUID uniqueId, final UpdatePlayerCallback updatePlayerCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtPlaytime.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement statement = connection.prepareStatement("INSERT INTO players (uuid, first_joined) VALUES (?, ?)")) {
				statement.setString(1, uniqueId.toString());
				statement.setString(2, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
				statement.executeUpdate();

				Bukkit.getScheduler().runTask(DirtPlaytime.getPlugin(), updatePlayerCallback::onSuccess);
			} catch (SQLException e) { e.printStackTrace(); }
		});
	}

	public static void addPlaytime(final Set<UUID> onlinePlayers) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtPlaytime.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement statement = connection.prepareStatement("UPDATE players SET time_played = time_played + 1 WHERE uuid = ?")) {
				for (UUID uuid : onlinePlayers) {
					statement.setString(1, uuid.toString());
					statement.executeUpdate();
				}
			} catch (SQLException e) { e.printStackTrace(); }
		});
	}

	public static void getPlaytime(final UUID uuid, final GetPlaytimeCallback getPlaytimeCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtPlaytime.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement statement = connection.prepareStatement("SELECT * FROM players WHERE uuid = ?")) {
				statement.setString(1, uuid.toString());
				ResultSet resultSet = statement.executeQuery();

				if (resultSet.next()) {
					Player player = new Player(
							UUID.fromString(resultSet.getString("uuid")),
							resultSet.getLong("time_played"),
							resultSet.getInt("times_joined"),
							LocalDateTime.parse(resultSet.getString("first_joined"))
					);
					Bukkit.getScheduler().runTask(DirtPlaytime.getPlugin(), () -> getPlaytimeCallback.onSuccess(player));
				}
			} catch (SQLException e) { e.printStackTrace(); }
		});
	}
}
