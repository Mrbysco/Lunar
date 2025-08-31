package com.mrbysco.lunar.handler;

import com.mrbysco.lunar.LunarPhaseData;
import com.mrbysco.lunar.api.ILunarEvent;
import com.mrbysco.lunar.handler.result.EventResult;
import com.mrbysco.lunar.platform.Services;
import com.mrbysco.lunar.registry.events.BigMoonEvent;
import com.mrbysco.lunar.registry.events.TinyMoonEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class LunarHandler {
	public static void onWorldTick(Level level) {
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
						event.stopEffects(serverLevel);
					}

					phaseData.eraseEvent();
					phaseData.syncEvent(serverLevel);
				} else {
					serverLevel.getAllEntities().forEach(entity -> {
						Attribute gravityAttribute = Services.PLATFORM.getGravityAttribute();
						if (entity instanceof LivingEntity livingEntity) {
							AttributeInstance attributeInstance = livingEntity.getAttribute(gravityAttribute);
							if (attributeInstance != null) {
								attributeInstance.removeModifier(BigMoonEvent.GRAVITY_MODIFIER);
								attributeInstance.removeModifier(TinyMoonEvent.GRAVITY_MODIFIER);
							}
						}
					});
				}
			}
		}
	}

	public static EventResult canSleep(Player player, BlockPos sleepingPos) {
		Level level = player.level();
		if (!level.isClientSide) {
			LunarPhaseData phaseData = LunarPhaseData.get(level);
			ILunarEvent lunarEvent = phaseData.getActiveLunarEvent();
			if (lunarEvent != null) {
				EventResult result = lunarEvent.canSleep(player, sleepingPos);
				if (result == EventResult.DENY) {
					MutableComponent denyComponent = Component.translatable("lunar.event.sleep_canceled",
							Component.translatable(lunarEvent.getTranslationKey())).withStyle(ChatFormatting.RED);
					player.displayClientMessage(denyComponent, true);
				}
				return result;
			}
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
