package com.mrbysco.lunar.handler;

import com.mrbysco.lunar.LunarPhaseData;
import com.mrbysco.lunar.api.ILunarEvent;
import com.mrbysco.lunar.handler.result.EventResult;
import com.mrbysco.lunar.registry.LunarRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Pair;

public class LunarHandler {
	public static void onLevelTick(Level level) {
		if (level.getGameTime() % 20 == 0 && !level.isClientSide) {
			ServerLevel serverLevel = (ServerLevel) level;
			LunarPhaseData phaseData = LunarPhaseData.get(serverLevel);
			ILunarEvent event = phaseData.getActiveLunarEvent();
			int currentTime = (int) (serverLevel.getDayTime() % 24000L);

			if (currentTime > 13000 && currentTime < 23000) {
				if (!phaseData.hasEventActive()) {
					phaseData.setRandomLunarEvent(serverLevel);
					phaseData.syncEvent(serverLevel);
				} else {
					if (event != null) {
						if (event.applyEntityEffect()) {
							serverLevel.getAllEntities().forEach(entity -> {
								if (entity.isSpectator()) return;
								event.applyEntityEffect(entity);
							});
						}
						if (event.applyPlayerEffect()) {
							serverLevel.players().forEach(event::applyPlayerEffect);
						}
					}
				}
			} else {
				if (phaseData.hasEventActive()) {
					if (event != null) {
						if (event.applyEntityEffect()) {
							serverLevel.getAllEntities().forEach(entity -> {
								if (entity.isSpectator()) return;
								event.removeEntityEffect(entity);
							});
						}
						if (event.applyPlayerEffect()) {
							serverLevel.players().forEach(event::removePlayerEffect);
						}
					}

					phaseData.eraseEvent();
					phaseData.syncEvent(serverLevel);
				} else {
					// Remove any accidental modifiers that might have been left behind
					for (Pair<Holder<Attribute>, ResourceLocation> modifierPair : LunarRegistry.instance().getEventModifiers()) {
						serverLevel.getAllEntities().forEach(entity -> {
							if (entity instanceof LivingEntity livingEntity && !entity.isSpectator()) {
								AttributeInstance modifier = livingEntity.getAttribute(modifierPair.getLeft());
								if (modifier != null) {
									modifier.removeModifier(modifierPair.getRight());
								}
							}
						});
					}
				}
			}
		}
	}

	public static EventResult canSleep(Player player, BlockPos sleepingPos) {
		Level level = player.level();
		if (!level.isClientSide) {
			LunarPhaseData phaseData = LunarPhaseData.get(level);
			ILunarEvent lunarEvent = phaseData.getActiveLunarEvent();
			if (lunarEvent != null) return lunarEvent.canSleep(player, sleepingPos);
		}
		return EventResult.DEFAULT;
	}

	public static EventResult getSpawnResult(MobSpawnType mobSpawnType, LivingEntity livingEntity) {
		Level level = livingEntity.level();
		if (!level.isClientSide) {
			LunarPhaseData phaseData = LunarPhaseData.get(level);
			ILunarEvent lunarEvent = phaseData.getActiveLunarEvent();
			if (lunarEvent != null && lunarEvent.dictatesMobSpawn()) {
				return lunarEvent.getSpawnResult(livingEntity, mobSpawnType);
			}
		}
		return EventResult.DEFAULT;
	}

	public static void uponLivingSpawn(MobSpawnType mobSpawnType, LivingEntity livingEntity) {
		Level level = livingEntity.level();
		if (!level.isClientSide) {
			LunarPhaseData phaseData = LunarPhaseData.get(level);
			ILunarEvent lunarEvent = phaseData.getActiveLunarEvent();
			if (lunarEvent != null && lunarEvent.applySpawnEffect()) {
				lunarEvent.applySpawnEffect(livingEntity, mobSpawnType);
			}
		}
	}
}
