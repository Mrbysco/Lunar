package com.mrbysco.lunar.platform;

import com.google.common.collect.Maps;
import com.mrbysco.lunar.api.ILunarEvent;
import com.mrbysco.lunar.config.LunarConfig;
import com.mrbysco.lunar.network.message.SyncDeltaMovement;
import com.mrbysco.lunar.network.message.SyncEventMessage;
import com.mrbysco.lunar.platform.services.IPlatformHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Map;

public class NeoForgePlatformHelper implements IPlatformHelper {

	@Override
	public void syncEvent(Level level, ILunarEvent event) {
		PacketDistributor.ALL.noArg().send(new SyncEventMessage(event));
	}

	@Override
	public void syncEvent(ServerPlayer player, ILunarEvent event) {
		player.connection.send(new SyncEventMessage(event));
	}

	@Override
	public void syncDeltaMovement(ServerPlayer player, Vec3 deltaMovement) {
		player.connection.send(new SyncDeltaMovement(deltaMovement));
	}

	@Override
	public float getLunarChance() {
		return LunarConfig.COMMON.lunarEventChance.get().floatValue();
	}

	@Override
	public int getBloodMoonWeight() {
		return LunarConfig.COMMON.bloodMoonWeight.get();
	}

	@Override
	public int getCrimsonMoonWeight() {
		return LunarConfig.COMMON.crimsonMoonWeight.get();
	}

	@Override
	public int getMinerMoonWeight() {
		return LunarConfig.COMMON.minerMoonWeight.get();
	}

	@Override
	public int getWhiteMoonWeight() {
		return LunarConfig.COMMON.whiteMoonWeight.get();
	}

	@Override
	public int getBigMoonWeight() {
		return LunarConfig.COMMON.bigMoonWeight.get();
	}

	@Override
	public int getTinyMoonWeight() {
		return LunarConfig.COMMON.tinyMoonWeight.get();
	}

	@Override
	public int getBadOmenMoonWeight() {
		return LunarConfig.COMMON.badOmenMoonWeight.get();
	}

	@Override
	public int getHeroMoonWeight() {
		return LunarConfig.COMMON.heroMoonWeight.get();
	}

	@Override
	public int getEclipseMoonWeight() {
		return LunarConfig.COMMON.eclipseMoonWeight.get();
	}

	@Override
	public Map<ResourceLocation, ResourceLocation> getCrimsonReplacementMap() {
		Map<ResourceLocation, ResourceLocation> map = Maps.newHashMap();
		LunarConfig.COMMON.crimsonReplacements.get().forEach(entry -> {
			if (entry.contains(",")) {
				String[] split = entry.split(",");
				map.put(ResourceLocation.tryParse(split[0]), ResourceLocation.tryParse(split[1]));
			}
		});
		return map;
	}
}
