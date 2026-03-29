package com.mrbysco.lunar.network.handler;

import com.mrbysco.lunar.client.MoonHandler;
import com.mrbysco.lunar.network.message.SyncEventMessage;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {
	private static final ClientPayloadHandler INSTANCE = new ClientPayloadHandler();

	public static ClientPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleSync(final SyncEventMessage payload, final IPayloadContext context) {
		context.enqueueWork(() -> {
					//Sync moon event
					MoonHandler.disableMoon();
					if (payload.color() == -1 || payload.eventID().isBlank()) {
					} else {
						MoonHandler.setMoon(payload.eventID(), payload.color(), payload.moonScale());
						if (payload.customTexture() != null) {
							MoonHandler.setMoonBuffer(payload.customTexture());
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
