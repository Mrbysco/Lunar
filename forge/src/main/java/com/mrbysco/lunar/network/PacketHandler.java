package com.mrbysco.lunar.network;

import com.mrbysco.lunar.Constants;
import com.mrbysco.lunar.network.message.SyncDeltaMovement;
import com.mrbysco.lunar.network.message.SyncEventMessage;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.NetworkRegistry;
import net.neoforged.neoforge.network.simple.SimpleChannel;

public class PacketHandler {
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(Constants.MOD_ID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);

	private static int id = 0;

	public static void init() {
		CHANNEL.registerMessage(id++, SyncEventMessage.class, SyncEventMessage::encode, SyncEventMessage::decode, SyncEventMessage::handle);
		CHANNEL.registerMessage(id++, SyncDeltaMovement.class, SyncDeltaMovement::encode, SyncDeltaMovement::decode, SyncDeltaMovement::handle);
	}
}
