package com.mrbysco.lunar.platform.services;

import com.mrbysco.lunar.api.ILunarEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public interface IPlatformHelper {

	/**
	 * Syncing the lunar event to the clients.
	 *
	 * @param level The level to get the players from
	 * @param event The event that gets synced
	 */
	void syncEvent(Level level, ILunarEvent event);

	/**
	 * Syncing the lunar event to a specific client.
	 *
	 * @param player The player it gets synced to
	 * @param event  The event that gets synced
	 */
	void syncEvent(ServerPlayer player, ILunarEvent event);
}
