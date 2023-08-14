package com.mrbysco.lunar.network.message;

import com.mrbysco.lunar.client.MoonHandler;
import com.mrbysco.lunar.registry.ILunarEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;
import net.minecraftforge.network.NetworkEvent.Context;

import java.io.Serial;
import java.util.function.Supplier;

public class SyncEventMessage {
	private final int color;
	private final String eventID;
	private final float moonScale;

	public SyncEventMessage(ILunarEvent event) {
		this.color = event != null ? event.moonColor() : -1;
		this.eventID = event != null ? event.getID().toString() : "";
		this.moonScale = event != null ? event.moonScale() : 1.0F;
	}

	public SyncEventMessage(int color, String eventName, float scale) {
		this.color = color;
		this.eventID = eventName;
		this.moonScale = scale;
	}

	public static SyncEventMessage decode(final FriendlyByteBuf buffer) {
		return new SyncEventMessage(buffer.readInt(), buffer.readUtf(), buffer.readFloat());
	}

	public void encode(FriendlyByteBuf buffer) {
		buffer.writeInt(color);
		buffer.writeUtf(eventID);
		buffer.writeFloat(moonScale);
	}

	public void handle(Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			if (ctx.getDirection().getReceptionSide().isClient()) {
				UpdateEvent.update(this.eventID, this.color, this.moonScale).run();
			}
		});
		ctx.setPacketHandled(true);
	}

	private static class UpdateEvent {
		private static SafeRunnable update(String eventID, int moonColor, float moonScale) {
			return new SafeRunnable() {
				@Serial
				private static final long serialVersionUID = 1L;

				@Override
				public void run() {
					if (moonColor == -1 || eventID.isBlank()) {
						MoonHandler.disableMoon();
					} else {
						MoonHandler.setMoon(eventID, moonColor, moonScale);
					}
				}
			};
		}
	}
}
