package com.mrbysco.lunar.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mrbysco.lunar.events.EntityEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.npc.CatSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CatSpawner.class)
public class CatSpawnerMixin {
	@Inject(
			method = "spawnCat",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/entity/EntityType;create(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/EntitySpawnReason;)Lnet/minecraft/world/entity/Entity;",
					ordinal = 0,
					shift = At.Shift.BY,
					by = 3
			),
			cancellable = true
	)
	private void checkCatSpawn(BlockPos pos, ServerLevel level, CallbackInfoReturnable<Integer> cir, @Local Cat entity) {
		if (!EntityEvents.LIVING_CHECK_SPAWN.invoker().canSpawn(entity, level, pos.getX(), pos.getY(), pos.getZ(), EntitySpawnReason.NATURAL, null).consumesAction()) {
			cir.setReturnValue(0);
			cir.cancel();
		}
	}
}