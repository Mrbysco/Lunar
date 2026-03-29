package com.mrbysco.lunar.platform;

import com.mrbysco.lunar.api.ILunarEvent;
import com.mrbysco.lunar.network.message.SyncEventMessage;
import com.mrbysco.lunar.platform.services.IPlatformHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

public class NeoForgePlatformHelper implements IPlatformHelper {

	@Override
	public void syncEvent(Level level, ILunarEvent event) {
		PacketDistributor.sendToAllPlayers(new SyncEventMessage(event));
	}

	@Override
	public void syncEvent(ServerPlayer player, ILunarEvent event) {
		player.connection.send(new SyncEventMessage(event));
	}
}
