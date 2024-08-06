package com.mrbysco.lunar.network.message;

import com.mrbysco.lunar.Constants;
import com.mrbysco.lunar.api.ILunarEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SyncEventMessage(int color, String eventID, float moonScale,
                               ResourceLocation customTexture) implements CustomPacketPayload {

	public SyncEventMessage(ILunarEvent event) {
		this(
				event != null ? event.moonColor() : -1,
				event != null ? event.getID().toString() : "",
				event != null ? event.moonScale() : 1.0F,
				event != null ? event.moonTexture() : null
		);
	}

	public SyncEventMessage(int color, String eventName, float scale, String moonTexture) {
		this(
				color,
				eventName,
				scale,
				(moonTexture != null && moonTexture.isEmpty()) ? null : ResourceLocation.tryParse(moonTexture)
		);
	}

	public SyncEventMessage(final FriendlyByteBuf buffer) {
		this(buffer.readInt(), buffer.readUtf(), buffer.readFloat(), buffer.readUtf());
	}

	public void write(FriendlyByteBuf buffer) {
		buffer.writeInt(color);
		buffer.writeUtf(eventID);
		buffer.writeFloat(moonScale);
		buffer.writeUtf(customTexture == null ? "" : customTexture.toString());
	}

	@Override
	public ResourceLocation id() {
		return Constants.SYNC_EVENT_ID;
	}
}
