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
		PacketDistributor.sendToAllPlayers(new SyncEventMessage(event));
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

	@Override
	public boolean canSleepIn(ResourceLocation moonID) {
		boolean result = true;
		switch(moonID.toString()) {
			case "lunar:blood_moon" -> result = LunarConfig.COMMON.bloodMoonSleeping.get();
			case "lunar:crimson_moon" -> result = LunarConfig.COMMON.crimsonMoonSleeping.get();
			case "lunar:miner_moon" -> result = LunarConfig.COMMON.minerMoonSleeping.get();
			case "lunar:white_moon" -> result = LunarConfig.COMMON.whiteMoonSleeping.get();
			case "lunar:big_moon" -> result = LunarConfig.COMMON.bigMoonSleeping.get();
			case "lunar:tiny_moon" -> result = LunarConfig.COMMON.tinyMoonSleeping.get();
			case "lunar:bad_omen_moon" -> result = LunarConfig.COMMON.badOmenMoonSleeping.get();
			case "lunar:hero_moon" -> result = LunarConfig.COMMON.heroMoonSleeping.get();
			case "lunar:eclipse_moon" -> result = LunarConfig.COMMON.eclipseMoonSleeping.get();
		}
		return result;
	}

	@Override
	public int maxBadOmen() {
		return LunarConfig.COMMON.maxBadOmen.get();
	}

	@Override
	public int getBloodMoonPhase() { return LunarConfig.COMMON.bloodMoonPhase.get(); }

	@Override
	public int getCrimsonMoonPhase() { return LunarConfig.COMMON.crimsonMoonPhase.get(); }

	@Override
	public int getMinerMoonPhase() { return LunarConfig.COMMON.minerMoonPhase.get(); }

	@Override
	public int getWhiteMoonPhase() { return LunarConfig.COMMON.whiteMoonPhase.get(); }

	@Override
	public int getBigMoonPhase() { return LunarConfig.COMMON.bigMoonPhase.get(); }

	@Override
	public int getTinyMoonPhase() { return LunarConfig.COMMON.tinyMoonPhase.get(); }

	@Override
	public int getBadOmenMoonPhase() { return LunarConfig.COMMON.badOmenMoonPhase.get(); }

	@Override
	public int getHeroMoonPhase() { return LunarConfig.COMMON.heroMoonPhase.get(); }

	@Override
	public int getEclipseMoonPhase() { return LunarConfig.COMMON.eclipseMoonPhase.get(); }

	@Override
	public int getBloodMoonDay() { return LunarConfig.COMMON.bloodMoonDay.get(); }

	@Override
	public int getCrimsonMoonDay() { return LunarConfig.COMMON.crimsonMoonDay.get(); }

	@Override
	public int getMinerMoonDay() { return LunarConfig.COMMON.minerMoonDay.get(); }

	@Override
	public int getWhiteMoonDay() { return LunarConfig.COMMON.whiteMoonDay.get(); }

	@Override
	public int getBigMoonDay() { return LunarConfig.COMMON.bigMoonDay.get(); }

	@Override
	public int getTinyMoonDay() { return LunarConfig.COMMON.tinyMoonDay.get(); }

	@Override
	public int getBadOmenMoonDay() { return LunarConfig.COMMON.badOmenMoonDay.get(); }

	@Override
	public int getHeroMoonDay() { return LunarConfig.COMMON.heroMoonDay.get(); }

	@Override
	public int getEclipseMoonDay() { return LunarConfig.COMMON.eclipseMoonDay.get(); }
}
