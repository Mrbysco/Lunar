package com.mrbysco.lunar.network.message;

import com.mrbysco.lunar.Constants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.phys.Vec3;

public record SyncDeltaMovement(Vec3 deltaMovement) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, SyncDeltaMovement> CODEC = CustomPacketPayload.codec(
			SyncDeltaMovement::write,
			SyncDeltaMovement::new);
	public static final Type<SyncDeltaMovement> ID = new Type<>(Constants.SYNC_MOVEMENT_EVENT_ID);

	public SyncDeltaMovement(final FriendlyByteBuf buffer) {
		this(new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()));
	}

	public void write(FriendlyByteBuf buffer) {
		buffer.writeDouble(deltaMovement.x);
		buffer.writeDouble(deltaMovement.y);
		buffer.writeDouble(deltaMovement.z);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
