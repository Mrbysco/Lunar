package com.mrbysco.lunar.registry.events;

import com.mrbysco.lunar.Constants;
import com.mrbysco.lunar.api.LunarEvent;
import com.mrbysco.lunar.config.ConfigHelper;
import com.mrbysco.lunar.handler.result.EventResult;
import com.mrbysco.lunar.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class BadOmenMoonEvent extends LunarEvent {
	private static final Identifier MOON_LOCATION = Constants.modLoc("bad_omen");

	public BadOmenMoonEvent() {
		super(Constants.modLoc("bad_omen_moon"), 0xae1a19);
	}

	@Override
	public int spawnWeight() {
		return ConfigHelper.getBadOmenMoonWeight();
	}

	@Override
	public String getTranslationKey() {
		return "lunar.event.bad_omen_moon";
	}

	@Override
	public boolean applyPlayerEffect() {
		return true;
	}

	@Override
	public void applyPlayerEffect(Player player) {
		if (player instanceof ServerPlayer serverPlayer) {
			ServerLevel serverLevel = serverPlayer.level();
			if (!serverLevel.isRaided(player.blockPosition())) {
				final int maxLevel = ConfigHelper.maxBadOmen();
				int randomLevel = serverPlayer.getRandom().nextInt(maxLevel);
				player.addEffect(new MobEffectInstance(MobEffects.BAD_OMEN, 40, randomLevel, false, true));
			}
		}
	}

	@Override
	public Identifier moonTexture() {
		return MOON_LOCATION;
	}

	@Override
	public EventResult canSleep(Player player, BlockPos sleepingLocation) {
		if (!ConfigHelper.canSleepIn(getID()))
			return EventResult.DENY;
		return EventResult.DEFAULT;
	}
}
