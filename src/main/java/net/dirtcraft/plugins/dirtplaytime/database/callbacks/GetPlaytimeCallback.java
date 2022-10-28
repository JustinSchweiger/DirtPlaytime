package net.dirtcraft.plugins.dirtplaytime.database.callbacks;

import net.dirtcraft.plugins.dirtplaytime.data.Player;

public interface GetPlaytimeCallback {
	void onSuccess(Player player);
}
