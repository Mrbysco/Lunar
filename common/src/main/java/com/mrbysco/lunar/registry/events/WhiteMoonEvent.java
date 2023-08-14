package com.mrbysco.lunar.registry.events;

import com.mrbysco.lunar.Constants;
import com.mrbysco.lunar.handler.result.EventResult;
import com.mrbysco.lunar.platform.Services;
import com.mrbysco.lunar.registry.LunarEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;

public class WhiteMoonEvent extends LunarEvent {

	public WhiteMoonEvent() {
		super(new ResourceLocation(Constants.MOD_ID, "white_moon"), 0x1f1f1f);
	}

	@Override
	public int spawnWeight() {
		return Services.PLATFORM.getWhiteMoonWeight();
	}

	@Override
	public String getTranslationKey() {
		return "lunar.event.white_moon";
	}

	@Override
	public boolean applyPlayerEffect() {
		return true;
	}

	@Override
	public boolean dictatesMobSpawn() {
		return true;
	}

	@Override
	public EventResult getSpawnResult(LivingEntity livingEntity, MobSpawnType spawnType) {
		if (spawnType == MobSpawnType.NATURAL && livingEntity.getType().getCategory() == MobCategory.MONSTER) {
			return EventResult.DENY;
		}
		return EventResult.ALLOW;
	}

	@Override
	public EventResult canSleep(Player player, BlockPos sleepingLocation) {
		//TODO: Config?
		return EventResult.DEFAULT;
	}
}
