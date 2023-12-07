package com.mrbysco.lunar.network.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.NetworkEvent;

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

	public void handle(NetworkEvent.Context ctx) {
		ctx.enqueueWork(() -> {
			if (ctx.getDirection().getReceptionSide().isClient()) {
				if (FMLEnvironment.dist.isClient()) {
					net.minecraft.client.Minecraft.getInstance().player.setDeltaMovement(deltaMovement);
				}
			}
		});
		ctx.setPacketHandled(true);
	}
}
