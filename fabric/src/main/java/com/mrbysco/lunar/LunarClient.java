package com.mrbysco.lunar;

import com.mrbysco.lunar.client.MoonHandler;
import com.mrbysco.lunar.network.message.SyncDeltaMovement;
import com.mrbysco.lunar.network.message.SyncEventMessage;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class LunarClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(SyncEventMessage.ID, (payload, context) -> {
			int moonColor = payload.color();
			String eventID = payload.eventID();
			float moonScale = payload.moonScale();
			ResourceLocation customTexture = payload.customTexture();
			context.client().execute(() -> {
				if (moonColor == -1 || eventID.isBlank()) {
					MoonHandler.disableMoon();
				} else {
					MoonHandler.setMoon(eventID, moonColor, moonScale);
					if (customTexture != null) {
						MoonHandler.setMoonTexture(customTexture);
					}
				}
			});
		});
		ClientPlayNetworking.registerGlobalReceiver(SyncDeltaMovement.ID, (payload, context) -> {
			Vec3 deltaMovement = payload.deltaMovement();
			context.client().execute(() -> {
				Minecraft.getInstance().player.setDeltaMovement(deltaMovement);
			});
		});
	}
}
