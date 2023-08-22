package com.mrbysco.lunar.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;

public class EntityEvents {

	public static final Event<CheckSpawn> LIVING_CHECK_SPAWN = EventFactory.createArrayBacked(CheckSpawn.class, callbacks -> (entity, world, x, y, z, type, spawner) -> {
		for (CheckSpawn callback : callbacks) {
			InteractionResult result = callback.canSpawn(entity, world, x, y, z, type, spawner);

			if (result != InteractionResult.PASS) {
				return result;
			}
		}

		return InteractionResult.PASS;
	});

	public static final Event<SpecialSpawn> LIVING_SPECIAL_SPAWN = EventFactory.createArrayBacked(SpecialSpawn.class, callbacks -> (entity, world, x, y, z, type, spawner) -> {
		for (SpecialSpawn callback : callbacks) {
			callback.specialSpawn(entity, world, x, y, z, type, spawner);
		}
	});

	@FunctionalInterface
	public interface CheckSpawn {
		InteractionResult canSpawn(LivingEntity entity, LevelAccessor level, double x, double y, double z, MobSpawnType type, @Nullable BaseSpawner spawner);
	}

	@FunctionalInterface
	public interface SpecialSpawn {
		void specialSpawn(Mob entity, LevelAccessor level, float x, float y, float z, @Nullable BaseSpawner spawner, MobSpawnType spawnReason);
	}
}
