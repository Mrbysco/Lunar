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

public class BadOmenMoonEvent extends LunarEvent {
	private static final ResourceLocation MOON_TEXTURE = new ResourceLocation(Constants.MOD_ID, "textures/environment/bad_omen.png");

	public BadOmenMoonEvent() {
		super(new ResourceLocation(Constants.MOD_ID, "bad_omen_moon"), 0xae1a19);
	}

	@Override
	public int spawnWeight() {
		return Services.PLATFORM.getBadOmenMoonWeight();
	}

	@Override
	public int getPhase() {
		return Services.PLATFORM.getBadOmenMoonPhase();
	}

	@Override
	public int getDay() {
		return Services.PLATFORM.getBadOmenMoonDay();
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
		player.addEffect(new MobEffectInstance(MobEffects.BAD_OMEN, 40, 0, false, true));
	}

	@Override
	public ResourceLocation moonTexture() {
		return MOON_TEXTURE;
	}

	@Override
	public EventResult canSleep(Player player, BlockPos sleepingLocation) {
		//TODO: Config?
		return EventResult.DEFAULT;
	}
}
