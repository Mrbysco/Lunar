package com.mrbysco.lunar.mixin;

import com.mrbysco.lunar.events.EntityEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.NaturalSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerSpecialMixin {
	@ModifyArg(method = "spawnCategoryForPosition(Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/NaturalSpawner$SpawnPredicate;Lnet/minecraft/world/level/NaturalSpawner$AfterSpawnCallback;)V", at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntityWithPassengers(Lnet/minecraft/world/entity/Entity;)V",
			ordinal = 0))
	private static Entity lunar_onAddToWorld(Entity entity) {
		if (entity instanceof Mob mob) {
			EntityEvents.LIVING_CHECK_SPAWN.invoker().canSpawn(mob, mob.level(), mob.xOld, mob.yOld, mob.zOld, MobSpawnType.NATURAL, null);
		}
		return entity;
	}
}