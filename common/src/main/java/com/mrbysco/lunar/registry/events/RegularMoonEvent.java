package com.mrbysco.lunar.registry.events;

import com.mrbysco.lunar.Constants;
import com.mrbysco.lunar.api.LunarEvent;
import com.mrbysco.lunar.handler.result.EventResult;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class RegularMoonEvent extends LunarEvent {

	public RegularMoonEvent() {
		super(new ResourceLocation(Constants.MOD_ID, "regular"), 0xFFFFFF);
	}

	@Override
	public int spawnWeight() {
		return 0;
	}

	@Override
	public String getTranslationKey() {
		return "lunar.event.regular";
	}

	@Override
	public EventResult canSleep(Player player, BlockPos sleepingLocation) {
		//TODO: Config?
		return EventResult.DEFAULT;
	}
}
