package com.mrbysco.lunar.platform;

import com.mrbysco.lunar.api.ILunarEvent;
import com.mrbysco.lunar.network.message.SyncEventMessage;
import com.mrbysco.lunar.platform.services.IPlatformHelper;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class FabricPlatformHelper implements IPlatformHelper {

	@Override
	public void syncEvent(Level level, ILunarEvent event) {
		if (!level.isClientSide()) {
			for (ServerPlayer player : ((ServerLevel) level).players()) {
				ServerPlayNetworking.send(player, new SyncEventMessage(event));
			}
		}
	}

	@Override
	public void syncEvent(ServerPlayer player, ILunarEvent event) {
		ServerPlayNetworking.send(player, new SyncEventMessage(event));
	}
}
