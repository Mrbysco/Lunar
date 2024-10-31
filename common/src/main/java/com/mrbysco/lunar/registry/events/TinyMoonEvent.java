package com.mrbysco.lunar.registry.events;

import com.mrbysco.lunar.Constants;
import com.mrbysco.lunar.api.LunarEvent;
import com.mrbysco.lunar.handler.result.EventResult;
import com.mrbysco.lunar.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class TinyMoonEvent extends LunarEvent {

	public TinyMoonEvent() {
		super(new ResourceLocation(Constants.MOD_ID, "tiny_moon"), 0xFFFFF1);
	}

	@Override
	public int spawnWeight() {
		return Services.PLATFORM.getTinyMoonWeight();
	}

	@Override
	public int getPhase() {
		return Services.PLATFORM.getTinyMoonPhase();
	}

	@Override
	public int getDay() {
		return Services.PLATFORM.getTinyMoonDay();
	}

	@Override
	public String getTranslationKey() {
		return "lunar.event.tiny_moon";
	}

	@Override
	public boolean applyPlayerEffect() {
		return true;
	}

	@Override
	public void applyEntityEffect(Entity entity) {
		Vec3 deltaMovement = entity.getDeltaMovement();
		entity.setDeltaMovement(deltaMovement.add(0, -1F, 0));
	}

	@Override
	public boolean applyEntityEffect() {
		return true;
	}

	@Override
	public void applyPlayerEffect(Player player) {
		if (!player.level().isClientSide)
			Services.PLATFORM.syncDeltaMovement((ServerPlayer) player, player.getDeltaMovement());
	}

	@Override
	public float moonScale() {
		return 0.25F;
	}

	@Override
	public EventResult canSleep(Player player, BlockPos sleepingLocation) {
		//TODO: Config?
		return EventResult.DEFAULT;
	}
}
