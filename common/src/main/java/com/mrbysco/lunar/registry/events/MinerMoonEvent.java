package com.mrbysco.lunar.registry.events;

import com.mrbysco.lunar.Constants;
import com.mrbysco.lunar.handler.result.EventResult;
import com.mrbysco.lunar.platform.Services;
import com.mrbysco.lunar.registry.LunarEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class MinerMoonEvent extends LunarEvent {

	public MinerMoonEvent() {
		super(new ResourceLocation(Constants.MOD_ID, "miner_moon"), 0xb3e09e);
	}

	@Override
	public int spawnWeight() {
		return Services.PLATFORM.getMinerMoonWeight();
	}

	@Override
	public String getTranslationKey() {
		return "lunar.event.miner_moon";
	}

	@Override
	public boolean applyPlayerEffect() {
		return true;
	}

	@Override
	public void applyPlayerEffect(Player player) {
		player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 40, 0, false, true));
	}

	@Override
	public EventResult canSleep(Player player, BlockPos sleepingLocation) {
		//TODO: Config?
		return EventResult.DEFAULT;
	}
}
