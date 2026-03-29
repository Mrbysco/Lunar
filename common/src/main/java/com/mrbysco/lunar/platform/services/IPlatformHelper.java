package com.mrbysco.lunar.platform.services;

import com.mrbysco.lunar.api.ILunarEvent;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

import java.util.Map;

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

	/**
	 * Returns the configured chance of a Lunar Event happening.
	 *
	 * @return the Lunar Event Chance
	 */
	float getLunarChance();

	/**
	 * Returns the configured weight of the Blood Moon event.
	 *
	 * @return the Blood Moon Weight
	 */
	int getBloodMoonWeight();

	/**
	 * Returns the configured weight of the Crimson Moon event.
	 *
	 * @return the Crimson Moon Weight
	 */
	int getCrimsonMoonWeight();

	/**
	 * Returns the configured weight of the Miner Moon event.
	 *
	 * @return the Miner Moon Weight
	 */
	int getMinerMoonWeight();

	/**
	 * Returns the configured weight of the White Moon event.
	 *
	 * @return the White Moon Weight
	 */
	int getWhiteMoonWeight();

	/**
	 * Returns the configured weight of the Big Moon event.
	 *
	 * @return the Big Moon Weight
	 */
	int getBigMoonWeight();

	/**
	 * Returns the configured weight of the Tiny Moon event.
	 *
	 * @return the Tiny Moon Weight
	 */
	int getTinyMoonWeight();

	/**
	 * Returns the configured weight of the Bad Omen Moon event.
	 *
	 * @return the Bad Omen Moon Weight
	 */
	int getBadOmenMoonWeight();

	/**
	 * Returns the configured weight of the Hero Moon event.
	 *
	 * @return the Hero Moon Weight
	 */
	int getHeroMoonWeight();

	/**
	 * Returns the configured weight of the Eclipse Moon event.
	 *
	 * @return the Eclipse Moon Weight
	 */
	int getEclipseMoonWeight();

	/**
	 * Returns the configured Crimson Replacement Map to be used during the Crimson Moon event
	 *
	 * @return the configured Crimson Replacement Map.
	 */
	Map<Identifier, Identifier> getCrimsonReplacementMap();

	/**
	 * Checks if sleeping is allowed during the given moon event.
	 * @param moonID the moon event to check
	 * @return true if sleeping is allowed, false otherwise
	 */
	boolean canSleepIn(Identifier moonID);

	/**
	 * Returns the max level of Bad Omen applied during the Bad Omen Moon Event.
	 * @return the max level of Bad Omen
	 */
	int maxBadOmen();
}
