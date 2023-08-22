package com.mrbysco.lunar.mixin;

import com.mrbysco.lunar.events.EntityEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.BaseSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BaseSpawner.class)
public class BaseSpawnerSpecialMixin {

	@ModifyArg(method = "serverTick", at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerLevel;tryAddFreshEntityWithPassengers(Lnet/minecraft/world/entity/Entity;)Z",
			ordinal = 0))
	private Entity lunar_onAddToWorld(Entity entity) {
		if (entity instanceof Mob mob) {
			EntityEvents.LIVING_SPECIAL_SPAWN.invoker().specialSpawn(mob, mob.level, (float) mob.getX(), (float) mob.getY(), (float) mob.getZ(), (BaseSpawner) (Object) this, MobSpawnType.SPAWNER);
		}
		return entity;
	}
}