package com.mrbysco.lunar;

import com.mrbysco.lunar.client.MoonHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

public class LunarClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(Constants.SYNC_EVENT_ID, (client, handler, buf, responseSender) -> {
			int moonColor = buf.readInt();
			String eventID = buf.readUtf();
			float moonScale = buf.readFloat();
			client.execute(() -> {
				if (moonColor == -1 || eventID.isBlank()) {
					MoonHandler.disableMoon();
				} else {
					MoonHandler.setMoon(eventID, moonColor, moonScale);
				}
			});
		});
		ClientPlayNetworking.registerGlobalReceiver(Constants.SYNC_MOVEMENT_EVENT_ID, (client, handler, buf, responseSender) -> {
			Vec3 deltaMovement = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
			client.execute(() -> {
				Minecraft.getInstance().player.setDeltaMovement(deltaMovement);
			});
		});
	}
}
