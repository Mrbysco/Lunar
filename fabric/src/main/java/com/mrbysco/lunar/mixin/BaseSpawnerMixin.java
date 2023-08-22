package com.mrbysco.lunar.mixin;

import com.mrbysco.lunar.events.EntityEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
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
			target = "Lnet/minecraft/world/entity/Mob;checkSpawnObstruction(Lnet/minecraft/world/level/LevelReader;)Z",
			ordinal = 0))
	private boolean skipDoubleObstruction(Mob mob, LevelReader levelReader) {
		return true;
	}
}