package com.mrbysco.lunar.platform.services;

import com.mrbysco.lunar.api.ILunarEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

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
	 * Sync the deltaMovement to the client.
	 *
	 * @param player        The player to sync to
	 * @param deltaMovement The deltaMovement to sync
	 */
	void syncDeltaMovement(ServerPlayer player, Vec3 deltaMovement);

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
	 * Returns the configured phase of the Blood Moon event.
	 *
	 * @return the Blood Moon Phase
	 */
	int getBloodMoonPhase();

	/**
	 * Returns the configured phase of the Crimson Moon event.
	 *
	 * @return the Crimson Moon Phase
	 */
	int getCrimsonMoonPhase();

	/**
	 * Returns the configured phase of the Miner Moon event.
	 *
	 * @return the Miner Moon Phase
	 */
	int getMinerMoonPhase();

	/**
	 * Returns the configured phase of the White Moon event.
	 *
	 * @return the White Moon Phase
	 */
	int getWhiteMoonPhase();

	/**
	 * Returns the configured phase of the Big Moon event.
	 *
	 * @return the Big Moon Phase
	 */
	int getBigMoonPhase();

	/**
	 * Returns the configured phase of the Tiny Moon event.
	 *
	 * @return the Tiny Moon Phase
	 */
	int getTinyMoonPhase();

	/**
	 * Returns the configured phase of the Bad Omen Moon event.
	 *
	 * @return the Bad Omen Moon Phase
	 */
	int getBadOmenMoonPhase();

	/**
	 * Returns the configured phase of the Hero Moon event.
	 *
	 * @return the Hero Moon Phase
	 */
	int getHeroMoonPhase();

	/**
	 * Returns the configured nth day of the Blood Moon event.
	 *
	 * @return the Blood Moon Day
	 */
	int getBloodMoonDay();

	/**
	 * Returns the configured nth day of the Crimson Moon event.
	 *
	 * @return the Crimson Moon Day
	 */
	int getCrimsonMoonDay();

	/**
	 * RReturns the configured nth day of the Miner Moon event.
	 *
	 * @return the Miner Moon Day
	 */
	int getMinerMoonDay();

	/**
	 * Returns the configured nth day of the White Moon event.
	 *
	 * @return the White Moon Day
	 */
	int getWhiteMoonDay();

	/**
	 * Returns the configured nth day of the Big Moon event.
	 *
	 * @return the Big Moon Day
	 */
	int getBigMoonDay();

	/**
	 * Returns the configured nth day of the Tiny Moon event.
	 *
	 * @return the Tiny Moon Day
	 */
	int getTinyMoonDay();

	/**
	 * Returns the configured nth day of the Bad Omen Moon event.
	 *
	 * @return the Bad Omen Moon Day
	 */
	int getBadOmenMoonDay();

	/**
	 * Returns the configured nth day of the Hero Moon event.
	 *
	 * @return the Hero Moon Day
	 */
	int getHeroMoonDay();

	/**
	 * Returns the configured Crimson Replacement Map to be used during the Crimson Moon event
	 *
	 * @return the configured Crimson Replacement Map.
	 */
	Map<ResourceLocation, ResourceLocation> getCrimsonReplacementMap();

	/**
	 * Returns the associated ResourceLocation for the given EntityType.
	 *
	 * @return the ResourceLocation.
	 */
	ResourceLocation getEntityTypeLocation(EntityType<?> entityType);

	/**
	 * Returns the associated EntityType for the given ResourceLocation.
	 *
	 * @return the EntityType.
	 */
	EntityType<?> getEntityType(ResourceLocation location);
}
