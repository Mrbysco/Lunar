package com.mrbysco.lunar.network;

import com.mrbysco.lunar.Constants;
import com.mrbysco.lunar.network.handler.ClientPayloadHandler;
import com.mrbysco.lunar.network.message.SyncDeltaMovement;
import com.mrbysco.lunar.network.message.SyncEventMessage;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class PacketHandler {
	public static void setupPackets(final RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registrar = event.registrar(Constants.MOD_ID);
		registrar.playToClient(SyncDeltaMovement.ID, SyncDeltaMovement.CODEC, ClientPayloadHandler.getInstance()::handleDelta);
		registrar.playToClient(SyncEventMessage.ID, SyncEventMessage.CODEC, ClientPayloadHandler.getInstance()::handleSync);
	}
}
