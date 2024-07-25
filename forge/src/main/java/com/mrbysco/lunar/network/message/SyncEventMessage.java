package com.mrbysco.lunar.network.message;

import com.mrbysco.lunar.api.ILunarEvent;
import com.mrbysco.lunar.client.MoonHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;
import net.minecraftforge.network.NetworkEvent.Context;

import java.io.Serial;
import java.util.function.Supplier;

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

	public void handle(Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			if (ctx.getDirection().getReceptionSide().isClient()) {
				UpdateEvent.update(this.eventID, this.color, this.moonScale, this.customTexture).run();
			}
		});
		ctx.setPacketHandled(true);
	}

	private static class UpdateEvent {
		private static SafeRunnable update(String eventID, int moonColor, float moonScale, ResourceLocation customTexture) {
			return new SafeRunnable() {
				@Serial
				private static final long serialVersionUID = 1L;

				@Override
				public void run() {
					if (moonColor == -1 || eventID.isBlank()) {
						MoonHandler.disableMoon();
					} else {
						MoonHandler.setMoon(eventID, moonColor, moonScale);
						if (customTexture != null) {
							MoonHandler.setMoonTexture(customTexture);
						}
					}
				}
			};
		}
	}
}
