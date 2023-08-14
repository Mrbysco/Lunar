package com.mrbysco.lunar.mixin;

import com.mrbysco.lunar.events.EntityEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BaseSpawner.class)
public class BaseSpawnerMixin {
	@Redirect(method = "serverTick", at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/Mob;checkSpawnRules(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/MobSpawnType;)Z",
			ordinal = 0))
	private boolean checkSpawnerSpawn(Mob mob, LevelAccessor level, MobSpawnType type) {
		var result = EntityEvents.LIVING_CHECK_SPAWN.invoker().canSpawn(mob, level, mob.getX(), mob.getY(), mob.getZ(), type, (BaseSpawner) (Object) this);
		if (result != InteractionResult.PASS) {
			return result.consumesAction();
		}
		return mob.checkSpawnRules(level, type) && mob.checkSpawnObstruction(level);
	}

	@Redirect(method = "serverTick", at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerLevel;tryAddFreshEntityWithPassengers(Lnet/minecraft/world/entity/Entity;)Z",
			ordinal = 0))
	private boolean specialSpawn(ServerLevel level, Entity entity) {
		Mob mob = (Mob) entity;
		var result = EntityEvents.LIVING_SPECIAL_SPAWN.invoker().specialSpawn(mob, level, (float) mob.getX(), (float) mob.getY(), (float) mob.getZ(), (BaseSpawner) (Object) this, MobSpawnType.SPAWNER);
		if (result != InteractionResult.PASS && !result.consumesAction()) {
			return false;
		}
		return level.tryAddFreshEntityWithPassengers(entity);
	}

	@Redirect(method = "serverTick", at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/Mob;checkSpawnObstruction(Lnet/minecraft/world/level/LevelReader;)Z",
			ordinal = 0))
	private boolean skipDoubleObstruction(Mob mob, LevelReader levelReader) {
		return true;
	}
}