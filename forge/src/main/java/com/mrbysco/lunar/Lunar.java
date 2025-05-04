package com.mrbysco.lunar;

import com.mrbysco.lunar.commands.LunarCommands;
import com.mrbysco.lunar.config.LunarConfig;
import com.mrbysco.lunar.handler.LunarHandler;
import com.mrbysco.lunar.handler.result.EventResult;
import com.mrbysco.lunar.network.PacketHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;
import net.neoforged.neoforge.event.entity.player.CanContinueSleepingEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@Mod(Constants.MOD_ID)
public class Lunar {

	public Lunar(IEventBus eventBus, ModContainer container) {
		container.registerConfig(ModConfig.Type.COMMON, LunarConfig.commonSpec);
		eventBus.addListener(this::setup);

		eventBus.addListener(PacketHandler::setupPackets);

		NeoForge.EVENT_BUS.addListener(this::onSleepCheck);
		NeoForge.EVENT_BUS.addListener(this::onLivingSpawn);
		NeoForge.EVENT_BUS.addListener(this::onCheckSpawn);
		NeoForge.EVENT_BUS.addListener(this::onLevelTick);
		NeoForge.EVENT_BUS.addListener(this::onLogin);

		NeoForge.EVENT_BUS.addListener(this::onCommandRegister);
	}

	private void setup(final FMLCommonSetupEvent event) {
		CommonClass.initRegistry();
	}

	public void onCommandRegister(RegisterCommandsEvent event) {
		LunarCommands.initializeCommands(event.getDispatcher());
	}

	private void onSleepCheck(CanContinueSleepingEvent event) {
		if (event.getEntity() instanceof Player player && player.level().dimension().equals(Level.OVERWORLD) && player.getSleepingPos().isPresent()) {
			EventResult result = LunarHandler.canSleep(player, player.getSleepingPos().get());
			if (result != EventResult.DEFAULT) {
				event.setContinueSleeping(result == EventResult.DENY ? false : event.mayContinueSleeping());
			}
		}
	}

	private void onLivingSpawn(FinalizeSpawnEvent event) {
		if (event.getEntity().level().dimension().equals(Level.OVERWORLD)) {
			LunarHandler.uponLivingSpawn(event.getSpawnType(), event.getEntity());
		}
	}

	private void onCheckSpawn(MobSpawnEvent.PositionCheck event) {
		if (event.getLevel().getLevel().dimension().equals(Level.OVERWORLD)) {
			EventResult spawnResult = LunarHandler.getSpawnResult(event.getSpawnType(), event.getEntity());
			if (spawnResult != EventResult.DEFAULT) {
				if (spawnResult == EventResult.ALLOW) {
					event.setResult(MobSpawnEvent.PositionCheck.Result.SUCCEED);
				} else {
					event.setResult(MobSpawnEvent.PositionCheck.Result.FAIL);
				}
			}
		}
	}

	private void onLevelTick(LevelTickEvent.Post event) {
		if (event.getLevel() instanceof ServerLevel serverLevel) {
			if (serverLevel.dimension().equals(Level.OVERWORLD)) {
				LunarHandler.onOverworldTick(serverLevel);
			} else {
				LunarHandler.onLevelTick(serverLevel);
			}
		}
	}

	public void onLogin(PlayerLoggedInEvent event) {
		Player player = event.getEntity();
		Level level = player.level();
		if (!level.isClientSide) {
			LunarPhaseData phaseData = LunarPhaseData.get(player.level());
			phaseData.syncEvent((ServerPlayer) player);
		}
	}
}