package com.mrbysco.lunar.mixin;

import com.mrbysco.lunar.events.PlayerEvents;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerListMixin {
	@Inject(method = "placeNewPlayer", at = @At("RETURN"))
	private void placeNewPlayer(Connection connection, ServerPlayer serverPlayer, CallbackInfo ci) {
		PlayerEvents.PLAYER_LOGIN.invoker().join(serverPlayer);
	}
}