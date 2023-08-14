package com.mrbysco.lunar.platform.services;

import com.mrbysco.lunar.registry.ILunarEvent;
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
