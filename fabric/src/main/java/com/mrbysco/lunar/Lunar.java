package com.mrbysco.lunar;

import com.mrbysco.lunar.commands.LunarCommands;
import com.mrbysco.lunar.config.LunarConfig;
import com.mrbysco.lunar.events.EntityEvents;
import com.mrbysco.lunar.events.PlayerEvents;
import com.mrbysco.lunar.handler.LunarHandler;
import com.mrbysco.lunar.handler.result.EventResult;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;


public class Lunar implements ModInitializer {
	public static LunarConfig config;

	@Override
	public void onInitialize() {
		ConfigHolder<LunarConfig> holder = AutoConfig.register(LunarConfig.class, Toml4jConfigSerializer::new);
		config = holder.getConfig();

		ServerLifecycleEvents.SERVER_STARTING.register((server) -> {
			CommonClass.initRegistry();
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, context, selection) -> {
			LunarCommands.initializeCommands(dispatcher);
		});

		EntitySleepEvents.ALLOW_SLEEP_TIME.register(this::onSleepCheck);
		EntityEvents.LIVING_SPECIAL_SPAWN.register(this::onLivingSpawn);
		if (FabricLoader.getInstance().isModLoaded("architectury")) {
			dev.architectury.event.events.common.EntityEvent.LIVING_CHECK_SPAWN.register((entity, level, x, y, z, type, spawner) -> {
				InteractionResult result = onCheckSpawn(entity, level, x, y, z, type, spawner);
				if (result == InteractionResult.FAIL)
					return dev.architectury.event.EventResult.interruptDefault();
				if (result == InteractionResult.SUCCESS)
					return dev.architectury.event.EventResult.interruptTrue();
				return dev.architectury.event.EventResult.pass();
			});
		} else {
			EntityEvents.LIVING_CHECK_SPAWN.register(this::onCheckSpawn);
		}
		ServerTickEvents.END_WORLD_TICK.register(this::onWorldTick);
		PlayerEvents.PLAYER_LOGIN.register(this::onLogin);
	}

	private InteractionResult onSleepCheck(Player player, BlockPos sleepingPos, boolean vanillaResult) {
		if (player.level().dimension().equals(Level.OVERWORLD)) {
			EventResult result = LunarHandler.canSleep(player, sleepingPos);
			if (result != EventResult.DEFAULT) {
				return result == EventResult.DENY ? InteractionResult.FAIL : InteractionResult.SUCCESS;
			}
		}

		return InteractionResult.PASS;
	}

	private void onLivingSpawn(Mob entity, LevelAccessor level, float x, float y, float z, @Nullable BaseSpawner spawner, MobSpawnType spawnReason) {
		if (entity.level().dimension().equals(Level.OVERWORLD)) {
			LunarHandler.uponLivingSpawn(spawnReason, entity);
		}
	}

	private InteractionResult onCheckSpawn(LivingEntity entity, LevelAccessor level, double x, double y, double z, MobSpawnType type, @Nullable BaseSpawner spawner) {
		if (entity.level().dimension().equals(Level.OVERWORLD)) {
			EventResult spawnResult = LunarHandler.getSpawnResult(type, entity);
			if (spawnResult != EventResult.DEFAULT) {
				if (spawnResult == EventResult.ALLOW) {
					return InteractionResult.SUCCESS;
				} else {
					return InteractionResult.FAIL;
				}
			}
		}
		return InteractionResult.PASS;
	}

	private void onWorldTick(ServerLevel level) {
		if (level.dimension().equals(Level.OVERWORLD)) {
			LunarHandler.onWorldTick(level);
		}
	}

	public void onLogin(ServerPlayer player) {
		Level level = player.level();
		if (!level.isClientSide) {
			LunarPhaseData phaseData = LunarPhaseData.get(player.level());
			phaseData.syncEvent(player);
		}
	}
}
