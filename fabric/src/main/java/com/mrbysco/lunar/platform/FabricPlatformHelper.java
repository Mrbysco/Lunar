package com.mrbysco.lunar.platform;

import com.google.common.collect.Maps;
import com.mrbysco.lunar.Lunar;
import com.mrbysco.lunar.api.ILunarEvent;
import com.mrbysco.lunar.config.LunarConfig;
import com.mrbysco.lunar.network.message.SyncDeltaMovement;
import com.mrbysco.lunar.network.message.SyncEventMessage;
import com.mrbysco.lunar.platform.services.IPlatformHelper;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Map;

public class FabricPlatformHelper implements IPlatformHelper {

	@Override
	public void syncEvent(Level level, ILunarEvent event) {
		if (!level.isClientSide) {
			for (ServerPlayer player : ((ServerLevel) level).players()) {
				ServerPlayNetworking.send(player, new SyncEventMessage(event));
			}
		}
	}

	@Override
	public void syncEvent(ServerPlayer player, ILunarEvent event) {
		ServerPlayNetworking.send(player, new SyncEventMessage(event));
	}

	@Override
	public void syncDeltaMovement(ServerPlayer player, Vec3 deltaMovement) {
		FriendlyByteBuf buf = PacketByteBufs.create();
		buf.writeDouble(deltaMovement.x);
		buf.writeDouble(deltaMovement.y);
		buf.writeDouble(deltaMovement.z);

		ServerPlayNetworking.send(player, new SyncDeltaMovement(deltaMovement));
	}

	@Override
	public float getLunarChance() {
		if (Lunar.config == null) Lunar.config = AutoConfig.getConfigHolder(LunarConfig.class).getConfig();
		return Lunar.config.general.lunarEventChance;
	}

	@Override
	public int getBloodMoonWeight() {
		if (Lunar.config == null) Lunar.config = AutoConfig.getConfigHolder(LunarConfig.class).getConfig();
		return Lunar.config.events.bloodMoonWeight;
	}

	@Override
	public int getCrimsonMoonWeight() {
		if (Lunar.config == null) Lunar.config = AutoConfig.getConfigHolder(LunarConfig.class).getConfig();
		return Lunar.config.events.crimsonMoonWeight;
	}

	@Override
	public int getMinerMoonWeight() {
		if (Lunar.config == null) Lunar.config = AutoConfig.getConfigHolder(LunarConfig.class).getConfig();
		return Lunar.config.events.minerMoonWeight;
	}

	@Override
	public int getWhiteMoonWeight() {
		if (Lunar.config == null) Lunar.config = AutoConfig.getConfigHolder(LunarConfig.class).getConfig();
		return Lunar.config.events.whiteMoonWeight;
	}

	@Override
	public int getBigMoonWeight() {
		if (Lunar.config == null) Lunar.config = AutoConfig.getConfigHolder(LunarConfig.class).getConfig();
		return Lunar.config.events.bigMoonWeight;
	}

	@Override
	public int getTinyMoonWeight() {
		if (Lunar.config == null) Lunar.config = AutoConfig.getConfigHolder(LunarConfig.class).getConfig();
		return Lunar.config.events.tinyMoonWeight;
	}

	@Override
	public int getBadOmenMoonWeight() {
		if (Lunar.config == null) Lunar.config = AutoConfig.getConfigHolder(LunarConfig.class).getConfig();
		return Lunar.config.events.badOmenMoonWeight;
	}

	@Override
	public int getHeroMoonWeight() {
		if (Lunar.config == null) Lunar.config = AutoConfig.getConfigHolder(LunarConfig.class).getConfig();
		return Lunar.config.events.heroMoonWeight;
	}

	@Override
	public int getEclipseMoonWeight() {
		if (Lunar.config == null) Lunar.config = AutoConfig.getConfigHolder(LunarConfig.class).getConfig();
		return Lunar.config.events.eclipseMoonWeight;
	}

	@Override
	public Map<ResourceLocation, ResourceLocation> getCrimsonReplacementMap() {
		if (Lunar.config == null) Lunar.config = AutoConfig.getConfigHolder(LunarConfig.class).getConfig();
		Map<ResourceLocation, ResourceLocation> map = Maps.newHashMap();
		Lunar.config.crimson.crimsonReplacements.forEach(entry -> {
			if (entry.contains(",")) {
				String[] split = entry.split(",");
				map.put(ResourceLocation.tryParse(split[0]), ResourceLocation.tryParse(split[1]));
			}
		});
		return map;
	}
}
