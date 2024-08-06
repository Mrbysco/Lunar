package com.mrbysco.lunar.network;

import com.mrbysco.lunar.Constants;
import com.mrbysco.lunar.network.handler.ClientPayloadHandler;
import com.mrbysco.lunar.network.message.SyncDeltaMovement;
import com.mrbysco.lunar.network.message.SyncEventMessage;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

public class PacketHandler {
	public static void setupPackets(final RegisterPayloadHandlerEvent event) {
		final IPayloadRegistrar registrar = event.registrar(Constants.MOD_ID);
		registrar.play(Constants.SYNC_MOVEMENT_EVENT_ID, SyncDeltaMovement::new, handler -> handler
				.client(ClientPayloadHandler.getInstance()::handleDelta));
		registrar.play(Constants.SYNC_EVENT_ID, SyncEventMessage::new, handler -> handler
				.client(ClientPayloadHandler.getInstance()::handleSync));
	}
}
