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
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
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
		MinecraftForge.EVENT_BUS.addListener(this::onWorldTick);
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
		if (event.getEntityLiving() instanceof Player player && player.level.dimension().equals(Level.OVERWORLD)) {
			EventResult result = LunarHandler.canSleep(player, event.getSleepingLocation());
			if (result != EventResult.DEFAULT) {
				event.setResult(result == EventResult.DENY ? Result.DENY : Result.ALLOW);
			}
		}
	}

	private void onLivingSpawn(LivingSpawnEvent.SpecialSpawn event) {
		if (event.getEntityLiving().level.dimension().equals(Level.OVERWORLD)) {
			LunarHandler.uponLivingSpawn(event.getSpawnReason(), event.getEntityLiving());
		}
	}

	private void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) {
		if (event.getEntityLiving().level.dimension().equals(Level.OVERWORLD)) {
			EventResult spawnResult = LunarHandler.getSpawnResult(event.getSpawnReason(), event.getEntityLiving());
			if (spawnResult != EventResult.DEFAULT) {
				if (spawnResult == EventResult.ALLOW) {
					event.setResult(Result.ALLOW);
				} else {
					event.setResult(Result.DENY);
				}
			}
		}
	}

	private void onWorldTick(TickEvent.WorldTickEvent event) {
		if (event.phase == TickEvent.Phase.END && event.side.isServer()) {
			if (event.world.dimension().equals(Level.OVERWORLD)) {
				LunarHandler.onWorldTick(event.world);
			}
		}
	}

	public void onLogin(PlayerLoggedInEvent event) {
		Player player = event.getPlayer();
		Level level = player.level;
		if (!level.isClientSide) {
			LunarPhaseData phaseData = LunarPhaseData.get(player.level);
			phaseData.syncEvent((ServerPlayer) player);
		}
	}
}