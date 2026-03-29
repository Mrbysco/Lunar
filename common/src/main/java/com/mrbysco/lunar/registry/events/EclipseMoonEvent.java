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

public class EclipseMoonEvent extends LunarEvent {
	private static final Identifier MOON_LOCATION = Constants.modLoc("eclipse");

	public EclipseMoonEvent() {
		super(Constants.modLoc("eclipse_moon"), 0x000008);
	}

	@Override
	public int spawnWeight() {
		return Services.PLATFORM.getEclipseMoonWeight();
	}

	@Override
	public String getTranslationKey() {
		return "lunar.event.eclipse_moon";
	}

	@Override
	public boolean applyPlayerEffect() {
		return true;
	}

	@Override
	public void applyPlayerEffect(Player player) {
		if (player.level().canSeeSky(BlockPos.containing(player.getEyePosition())))
			player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 80, 0, false, true));
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
