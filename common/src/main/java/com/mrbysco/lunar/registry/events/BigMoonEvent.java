package com.mrbysco.lunar.registry.events;

import com.mrbysco.lunar.Constants;
import com.mrbysco.lunar.api.LunarEvent;
import com.mrbysco.lunar.handler.result.EventResult;
import com.mrbysco.lunar.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class BigMoonEvent extends LunarEvent {

	public BigMoonEvent() {
		super(new ResourceLocation(Constants.MOD_ID, "big_moon"), 0xFFFFF1);
	}

	@Override
	public int spawnWeight() {
		return Services.PLATFORM.getBigMoonWeight();
	}

	@Override
	public String getTranslationKey() {
		return "lunar.event.big_moon";
	}

	@Override
	public boolean applyPlayerEffect() {
		return true;
	}

	@Override
	public void applyPlayerEffect(Player player) {
		player.addEffect(new MobEffectInstance(MobEffects.JUMP, 40, 2, false, false));
		player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 40, 0, false, false));

		if (player.getRandom().nextInt(30) == 0) {
			player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 40, 0, false, false));
		}
	}

	@Override
	public float moonScale() {
		return 4.0F;
	}

	@Override
	public EventResult canSleep(Player player, BlockPos sleepingLocation) {
		//TODO: Config?
		return EventResult.DEFAULT;
	}
}
