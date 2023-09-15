package com.mrbysco.lunar.registry.events;

import com.mrbysco.lunar.Constants;
import com.mrbysco.lunar.api.LunarEvent;
import com.mrbysco.lunar.handler.result.EventResult;
import com.mrbysco.lunar.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;

import java.util.Map;

public class CrimsonMoonEvent extends LunarEvent {

	public CrimsonMoonEvent() {
		super(new ResourceLocation(Constants.MOD_ID, "crimson_moon"), 0xDC143C);
	}

	@Override
	public int spawnWeight() {
		return Services.PLATFORM.getCrimsonMoonWeight();
	}

	@Override
	public String getTranslationKey() {
		return "lunar.event.crimson_moon";
	}

	@Override
	public EventResult canSleep(Player player, BlockPos sleepingLocation) {
		//TODO: Config?
		return EventResult.DEFAULT;
	}

	@Override
	public boolean dictatesMobSpawn() {
		return true;
	}

	@Override
	public EventResult getSpawnResult(LivingEntity livingEntity, MobSpawnType spawnType) {
		ServerLevel level = (ServerLevel) livingEntity.level;
		if (spawnType == MobSpawnType.NATURAL) {
			Map<ResourceLocation, ResourceLocation> replacementMap = Services.PLATFORM.getCrimsonReplacementMap();
			ResourceLocation entityLocation = Services.PLATFORM.getEntityTypeLocation(livingEntity.getType());
			if (replacementMap.containsKey(entityLocation)) {
				ResourceLocation replacementLocation = replacementMap.get(entityLocation);
				if (replacementLocation != null) {
					EntityType<?> replacementType = Services.PLATFORM.getEntityType(replacementLocation);
					if (replacementType != null) {
						Entity replacementEntity = replacementType.create(level);
						if (replacementEntity != null) {
							BlockPos position = livingEntity.blockPosition();
							replacementEntity.moveTo(position, livingEntity.getYRot(), livingEntity.getXRot());
							if (replacementEntity instanceof Mob mob) {
								if (!mob.checkSpawnObstruction(level)) {
									return EventResult.DEFAULT;
								}
								mob.finalizeSpawn(level, level.getCurrentDifficultyAt(position), MobSpawnType.NATURAL, null, null);
							}
							if (replacementEntity instanceof Ghast) {
								if (level.random.nextDouble() <= 0.5) {
									level.addFreshEntity(replacementEntity);
								}
								livingEntity.discard();
							} else {
								level.addFreshEntity(replacementEntity);
								livingEntity.discard();
							}
							return EventResult.DENY;
						}
					}
				}
			}
		}
		return EventResult.DEFAULT;
	}
}