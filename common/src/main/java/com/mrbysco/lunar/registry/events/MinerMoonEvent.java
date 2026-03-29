package com.mrbysco.lunar.registry.events;

import com.mrbysco.lunar.Constants;
import com.mrbysco.lunar.api.LunarEvent;
import com.mrbysco.lunar.handler.result.EventResult;
import com.mrbysco.lunar.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class MinerMoonEvent extends LunarEvent {
	private static final Identifier MOON_LOCATION = Constants.modLoc("miner");

	public MinerMoonEvent() {
		super(Constants.modLoc("miner_moon"), 0xb3e09e);
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
		player.addEffect(new MobEffectInstance(MobEffects.HASTE, 40, 0, false, true));
	}

	@Override
	public Identifier moonTexture() {
		return MOON_LOCATION;
	}

	@Override
	public EventResult canSleep(Player player, BlockPos sleepingLocation) {
		if (!Services.PLATFORM.canSleepIn(getID()))
			return EventResult.DENY;
		return EventResult.DEFAULT;
	}
}
