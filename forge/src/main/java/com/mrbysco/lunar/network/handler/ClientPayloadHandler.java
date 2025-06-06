package com.mrbysco.lunar.network.handler;

import com.mrbysco.lunar.client.MoonHandler;
import com.mrbysco.lunar.network.message.SyncDeltaMovement;
import com.mrbysco.lunar.network.message.SyncEventMessage;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {
	private static final ClientPayloadHandler INSTANCE = new ClientPayloadHandler();

	public static ClientPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleDelta(final SyncDeltaMovement payload, final IPayloadContext context) {
		context.enqueueWork(() -> {
					//Sync delta movement
					context.player().setDeltaMovement(payload.deltaMovement());
				})
				.exceptionally(e -> {
					// Handle exception
					context.disconnect(Component.translatable("lunar.networking.sync_movement_event.failed", e.getMessage()));
					return null;
				});
	}

	public void handleSync(final SyncEventMessage payload, final IPayloadContext context) {
		context.enqueueWork(() -> {
					//Sync moon event
					MoonHandler.disableMoon();
					if (payload.color() == -1 || payload.eventID().isBlank()) {
					} else {
						MoonHandler.setMoon(payload.eventID(), payload.color(), payload.moonScale());
						if (payload.customTexture() != null) {
							MoonHandler.setMoonTexture(payload.customTexture());
						}
					}
				})
				.exceptionally(e -> {
					// Handle exception
					context.disconnect(Component.translatable("lunar.networking.sync_event.failed", e.getMessage()));
					return null;
				});
	}
}
