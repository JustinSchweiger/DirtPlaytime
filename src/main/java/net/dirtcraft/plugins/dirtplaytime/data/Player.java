package net.dirtcraft.plugins.dirtplaytime.data;

import java.time.LocalDateTime;
import java.util.UUID;

public class Player {
	private final UUID uuid;
	private String username;
	private String currentPath;
	private long timePlayed;
	private int timesJoined;
	private final LocalDateTime firstJoined;

	public Player(UUID uuid, String username, String currentPath, long timePlayed, int timesJoined, LocalDateTime firstJoined) {
		this.uuid = uuid;
		this.username = username;
		this.currentPath = currentPath;
		this.timePlayed = timePlayed;
		this.timesJoined = timesJoined;
		this.firstJoined = firstJoined;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void addTimePlayed(long timePlayed) {
		this.timePlayed += timePlayed;
	}

	public void setTimePlayed(long timePlayed) {
		this.timePlayed = timePlayed;
	}
	public void removeTimePlayed(long timePlayed) {
		this.timePlayed -= timePlayed;
	}

	public long getTimePlayed() {
		return timePlayed;
	}

	public int getTimesJoined() {
		return timesJoined;
	}

	public void addTimesJoined() {
		this.timesJoined++;
	}

	public LocalDateTime getFirstJoined() {
		return firstJoined;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCurrentPath() {
		return currentPath;
	}

	public void setCurrentPath(String currentPath) {
		this.currentPath = currentPath;
	}
}
