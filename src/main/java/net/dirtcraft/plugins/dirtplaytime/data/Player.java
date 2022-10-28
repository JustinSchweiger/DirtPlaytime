package net.dirtcraft.plugins.dirtplaytime.data;

import java.time.LocalDateTime;
import java.util.UUID;

public class Player {
	private final UUID uuid;
	private final long timePlayed;
	private final int timesJoined;
	private final LocalDateTime firstJoined;

	public Player(UUID uuid, long timePlayed, int timesJoined, LocalDateTime firstJoined) {
		this.uuid = uuid;
		this.timePlayed = timePlayed;
		this.timesJoined = timesJoined;
		this.firstJoined = firstJoined;
	}

	public UUID getUuid() {
		return uuid;
	}

	public long getTimePlayed() {
		return timePlayed;
	}

	public int getTimesJoined() {
		return timesJoined;
	}

	public LocalDateTime getFirstJoined() {
		return firstJoined;
	}
}
