package net.dirtcraft.plugins.dirtplaytime.database.callbacks;

import net.dirtcraft.plugins.dirtplaytime.data.Player;

import java.util.Map;
import java.util.UUID;

public interface GetPlayersCallback {
	void onSuccess(Map<UUID, Player> players);
}
