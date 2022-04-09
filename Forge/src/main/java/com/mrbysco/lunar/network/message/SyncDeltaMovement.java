package com.mrbysco.lunar.network.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;
import net.minecraftforge.network.NetworkEvent.Context;

import java.io.Serial;
import java.util.function.Supplier;

public class SyncDeltaMovement {
	private final Vec3 deltaMovement;

	public SyncDeltaMovement(Vec3 deltaMovement) {
		this.deltaMovement = deltaMovement;
	}

	public static SyncDeltaMovement decode(final FriendlyByteBuf buffer) {
		return new SyncDeltaMovement(new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()));
	}

	public void encode(FriendlyByteBuf buffer) {
		buffer.writeDouble(deltaMovement.x);
		buffer.writeDouble(deltaMovement.y);
		buffer.writeDouble(deltaMovement.z);
	}

	public void handle(Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			if (ctx.getDirection().getReceptionSide().isClient()) {
				UpdateEvent.update(this.deltaMovement).run();
			}
		});
		ctx.setPacketHandled(true);
	}

	private static class UpdateEvent {
		private static SafeRunnable update(Vec3 deltaMovement) {
			return new SafeRunnable() {
				@Serial
				private static final long serialVersionUID = 1L;

				@Override
				public void run() {
					net.minecraft.client.Minecraft.getInstance().player.setDeltaMovement(deltaMovement);
				}
			};
		}
	}
}
