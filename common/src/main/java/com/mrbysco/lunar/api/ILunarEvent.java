package com.mrbysco.lunar.api;

import com.mrbysco.lunar.handler.result.EventResult;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface ILunarEvent {

	/**
	 * Get the resource location of the lunar event.
	 *
	 * @return the resource location of the lunar event
	 */
	ResourceLocation getID();

	/**
	 * Get the translation key for the lunar event.
	 *
	 * @return the translation key
	 */
	String getTranslationKey();

	/**
	 * Called to check if the lunar event can happen.
	 *
	 * @param level The level
	 * @return if the lunar event can happen
	 */
	default boolean canAppear(Level level) {
		return spawnWeight() > 0;
	}

	/**
	 * Called to check if the moon should get scaled.
	 *
	 * @return the scale of the moon (1.0 = normal, 0.5 = half size, 2.0 = double size)
	 */
	default float moonScale() {
		return 1.0F;
	}

	/**
	 * Called to change the texture of the moon.
	 *
	 * @return the resource location of the custom moon texture
	 */
	default ResourceLocation moonTexture() {
		return null;
	}

	/**
	 * The weight in which the lunar event will be picked.
	 *
	 * @return The spawn weight, 0 being disabled and 100 being really common (min 0, max 100)
	 */
	int spawnWeight();

	/**
	 * The phase of the moon in which the lunar event will always be picked.
	 *
	 * @return The moon phase, -1 being disabled and 0-7 being corresponding phases (min -1, max 7)
	 */
	int getPhase();

	/**
	 * The nth day in which the lunar event will always be picked.
	 *
	 * @return The nth day, 0 being disabled and 255 the maximum (min 0, max 255)
	 */
	int getDay();

	/**
	 * The color of the moon during the lunar event.
	 *
	 * @return the color of the moon
	 */
	int moonColor();

	/**
	 * Called when the lunar event tries check if a mob is allowed to spawn.
	 *
	 * @param livingEntity the entity being checked
	 * @param spawnType    the spawn reason
	 * @return if the lunar event changes mob spawning rules
	 */
	default EventResult getSpawnResult(LivingEntity livingEntity, MobSpawnType spawnType) {
		return EventResult.DEFAULT;
	}

	/**
	 * Called when the lunar event tries check if a mob is allowed to spawn.
	 *
	 * @return if the lunar event changes mob spawning rules
	 */
	default boolean dictatesMobSpawn() {
		return false;
	}

	/**
	 * Called when the lunar event tries to apply an effect to a living entity upon spawn.
	 *
	 * @param livingEntity the entity the effect is applied to
	 * @param spawnType    the spawn reason
	 */
	default void applySpawnEffect(LivingEntity livingEntity, MobSpawnType spawnType) {
	}

	/**
	 * Called when the lunar event checks if it can apply an effect to living entities upon spawn.
	 *
	 * @return if the entity should have an effect applied to it
	 */
	default boolean applySpawnEffect() {
		return false;
	}


	/**
	 * Called when the lunar event tries to apply an effect to an entity.
	 *
	 * @param entity the entity the effect is applied to
	 */
	default void applyEntityEffect(Entity entity) {
	}

	/**
	 * Called when the lunar event checks if it can apply an effect to entities.
	 *
	 * @return if the entity should have an effect applied to it
	 */
	default boolean applyEntityEffect() {
		return false;
	}

	/**
	 * Called when the lunar event tries to apply an effect to a living entity.
	 *
	 * @param player the player the effect is applied to
	 */
	default void applyPlayerEffect(Player player) {
	}

	/**
	 * Called when the lunar event checks if it can apply an effect to players.
	 *
	 * @return if the player should have an effect applied to it
	 */
	default boolean applyPlayerEffect() {
		return false;
	}

	/**
	 * Called when the lunar event is over and the effects are removed.
	 *
	 * @param level the level where the event took place
	 */
	default void stopEffects(Level level) {
	}

	/**
	 * Called when a player is about to try and sleep through the lunar event.
	 *
	 * @param player           The sleeping player
	 * @param sleepingLocation The position of the sleeping player
	 * @return if the player is allowed to sleep
	 */
	default EventResult canSleep(Player player, BlockPos sleepingLocation) {
		return EventResult.DEFAULT;
	}
}
