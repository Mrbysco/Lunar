package com.mrbysco.lunar.network.message;

import com.mrbysco.lunar.api.ILunarEvent;
import com.mrbysco.lunar.client.MoonHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.NetworkEvent.Context;

public class SyncEventMessage {
	private final int color;
	private final String eventID;
	private final float moonScale;
	private final ResourceLocation customTexture;

	public SyncEventMessage(ILunarEvent event) {
		this.color = event != null ? event.moonColor() : -1;
		this.eventID = event != null ? event.getID().toString() : "";
		this.moonScale = event != null ? event.moonScale() : 1.0F;
		this.customTexture = event != null ? event.moonTexture() : null;
	}

	public SyncEventMessage(int color, String eventName, float scale, String moonTexture) {
		this.color = color;
		this.eventID = eventName;
		this.moonScale = scale;
		this.customTexture = (moonTexture != null && moonTexture.isEmpty()) ? null : ResourceLocation.tryParse(moonTexture);
	}

	public static SyncEventMessage decode(final FriendlyByteBuf buffer) {
		return new SyncEventMessage(buffer.readInt(), buffer.readUtf(), buffer.readFloat(), buffer.readUtf());
	}

	public void encode(FriendlyByteBuf buffer) {
		buffer.writeInt(color);
		buffer.writeUtf(eventID);
		buffer.writeFloat(moonScale);
		buffer.writeUtf(customTexture == null ? "" : customTexture.toString());
	}

	public void handle(Context ctx) {
		ctx.enqueueWork(() -> {
			if (ctx.getDirection().getReceptionSide().isClient()) {
				if (FMLEnvironment.dist.isClient()) {
					if (color == -1 || eventID.isBlank()) {
						MoonHandler.disableMoon();
					} else {
						MoonHandler.setMoon(eventID, color, moonScale);
						if (customTexture != null) {
							MoonHandler.setMoonTexture(customTexture);
						}
					}
				}
			}
		});
		ctx.setPacketHandled(true);
	}
}
