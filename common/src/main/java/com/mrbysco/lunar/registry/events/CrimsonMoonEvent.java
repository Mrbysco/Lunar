package com.mrbysco.lunar.registry.events;

import com.mrbysco.lunar.Constants;
import com.mrbysco.lunar.api.LunarEvent;
import com.mrbysco.lunar.config.ConfigHelper;
import com.mrbysco.lunar.handler.result.EventResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;

import java.util.Map;

public class CrimsonMoonEvent extends LunarEvent {
	private static final Identifier MOON_LOCATION = Constants.modLoc("crimson");

	public CrimsonMoonEvent() {
		super(Constants.modLoc("crimson_moon"), 0xDC143C);
	}

	@Override
	public int spawnWeight() {
		return ConfigHelper.getCrimsonMoonWeight();
	}

	@Override
	public String getTranslationKey() {
		return "lunar.event.crimson_moon";
	}

	@Override
	public EventResult canSleep(Player player, BlockPos sleepingLocation) {
		if (!ConfigHelper.canSleepIn(getID()))
			return EventResult.DENY;
		return EventResult.DEFAULT;
	}

	@Override
	public boolean dictatesMobSpawn() {
		return true;
	}

	@Override
	public Identifier moonTexture() {
		return MOON_LOCATION;
	}

	@SuppressWarnings("ConstantValue")
	@Override
	public EventResult getSpawnResult(LivingEntity livingEntity, EntitySpawnReason spawnType) {
		ServerLevel level = (ServerLevel) livingEntity.level();
		if (spawnType == EntitySpawnReason.NATURAL) {
			Map<Identifier, Identifier> replacementMap = ConfigHelper.getCrimsonReplacementMap();
			Identifier entityLocation = BuiltInRegistries.ENTITY_TYPE.getKey(livingEntity.getType());
			if (replacementMap.containsKey(entityLocation)) {
				Identifier replacementLocation = replacementMap.get(entityLocation);
				if (replacementLocation != null) {
					EntityType<?> replacementType = BuiltInRegistries.ENTITY_TYPE.getValue(replacementLocation);
					if (replacementType != null) {
						Entity replacementEntity = replacementType.create(level, EntitySpawnReason.CONVERSION);
						if (replacementEntity != null) {
							BlockPos position = livingEntity.blockPosition();
							replacementEntity.snapTo(position, livingEntity.getYRot(), livingEntity.getXRot());
							if (replacementEntity instanceof Mob mob) {
								if (!mob.checkSpawnObstruction(level)) {
									return EventResult.DEFAULT;
								}
								mob.finalizeSpawn(level, level.getCurrentDifficultyAt(position), EntitySpawnReason.NATURAL, null);
							}
							if (replacementEntity instanceof Ghast) {
								if (level.getRandom().nextDouble() <= 0.5) {
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