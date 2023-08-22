package com.mrbysco.lunar.mixin;

import com.mrbysco.lunar.events.EntityEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.NaturalSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {
	@Shadow
	private static boolean isValidPositionForMob(ServerLevel serverLevel, Mob mob, double d) {
		return false;
	}

	@Redirect(
			method = "spawnCategoryForPosition(Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/NaturalSpawner$SpawnPredicate;Lnet/minecraft/world/level/NaturalSpawner$AfterSpawnCallback;)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/NaturalSpawner;isValidPositionForMob(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Mob;D)Z",
					ordinal = 0
			)
	)
	private static boolean overrideNaturalSpawnCondition(ServerLevel level, Mob entity, double f) {
		var result = EntityEvents.LIVING_CHECK_SPAWN.invoker().canSpawn(entity, level, entity.xOld, entity.yOld, entity.zOld, MobSpawnType.NATURAL, null);
		if (result != InteractionResult.PASS) {
			return result.consumesAction();
		}
		return isValidPositionForMob(level, entity, f);
	}

	@Redirect(
			method = "spawnMobsForChunkGeneration",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/entity/Mob;checkSpawnRules(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/MobSpawnType;)Z",
					ordinal = 0
			)
	)
	private static boolean overrideChunkGenSpawnCondition(Mob mob, LevelAccessor level, MobSpawnType type) {
		var result = EntityEvents.LIVING_CHECK_SPAWN.invoker().canSpawn(mob, level, mob.xOld, mob.yOld, mob.zOld, MobSpawnType.CHUNK_GENERATION, null);
		if (result != InteractionResult.PASS) {
			return result.consumesAction();
		}
		return mob.checkSpawnRules(level, type);
	}
}