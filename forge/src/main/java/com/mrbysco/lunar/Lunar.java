package com.mrbysco.lunar;

import com.mrbysco.lunar.commands.LunarCommands;
import com.mrbysco.lunar.config.LunarConfig;
import com.mrbysco.lunar.handler.LunarHandler;
import com.mrbysco.lunar.handler.result.EventResult;
import com.mrbysco.lunar.network.PacketHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.SleepingLocationCheckEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class Lunar {

	public Lunar() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, LunarConfig.commonSpec);
		eventBus.addListener(this::setup);

		MinecraftForge.EVENT_BUS.addListener(this::onSleepCheck);
		MinecraftForge.EVENT_BUS.addListener(this::onLivingSpawn);
		MinecraftForge.EVENT_BUS.addListener(this::onCheckSpawn);
		MinecraftForge.EVENT_BUS.addListener(this::onLevelTick);
		MinecraftForge.EVENT_BUS.addListener(this::onLogin);

		MinecraftForge.EVENT_BUS.addListener(this::onCommandRegister);
	}

	private void setup(final FMLCommonSetupEvent event) {
		PacketHandler.init();

		CommonClass.initRegistry();
	}

	public void onCommandRegister(RegisterCommandsEvent event) {
		LunarCommands.initializeCommands(event.getDispatcher());
	}

	private void onSleepCheck(SleepingLocationCheckEvent event) {
		if (event.getEntity() instanceof Player player && player.level().dimension().equals(Level.OVERWORLD)) {
			EventResult result = LunarHandler.canSleep(player, event.getSleepingLocation());
			if (result != EventResult.DEFAULT) {
				event.setResult(result == EventResult.DENY ? Result.DENY : Result.ALLOW);
			}
		}
	}

	private void onLivingSpawn(MobSpawnEvent.FinalizeSpawn event) {
		if (event.getEntity().level().dimension().equals(Level.OVERWORLD)) {
			LunarHandler.uponLivingSpawn(event.getSpawnType(), event.getEntity());
		}
	}

	private void onCheckSpawn(MobSpawnEvent.PositionCheck event) {
		if (event.getLevel().getLevel().dimension().equals(Level.OVERWORLD)) {
			EventResult spawnResult = LunarHandler.getSpawnResult(event.getSpawnType(), event.getEntity());
			if (spawnResult != EventResult.DEFAULT) {
				if (spawnResult == EventResult.ALLOW) {
					event.setResult(Result.ALLOW);
				} else {
					event.setResult(Result.DENY);
				}
			}
		}
	}

	private void onLevelTick(TickEvent.LevelTickEvent event) {
		if (event.phase == TickEvent.Phase.END && event.side.isServer() && event.level.dimension().equals(Level.OVERWORLD)) {
			LunarHandler.onWorldTick(event.level);
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