package com.mrbysco.lunar.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerPlayer;

public class PlayerEvents {
	public static final Event<CheckSpawn> PLAYER_LOGIN = EventFactory.createArrayBacked(CheckSpawn.class, callbacks -> (serverPlayer) -> {
		for (CheckSpawn callback : callbacks) {
			callback.join(serverPlayer);
		}
	});

	@FunctionalInterface
	public interface CheckSpawn {
		void join(ServerPlayer serverPlayer);
	}
}
