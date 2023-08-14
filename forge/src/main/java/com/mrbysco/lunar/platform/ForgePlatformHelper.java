package com.mrbysco.lunar.platform;

import com.google.common.collect.Maps;
import com.mrbysco.lunar.config.LunarConfig;
import com.mrbysco.lunar.network.PacketHandler;
import com.mrbysco.lunar.network.message.SyncDeltaMovement;
import com.mrbysco.lunar.network.message.SyncEventMessage;
import com.mrbysco.lunar.platform.services.IPlatformHelper;
import com.mrbysco.lunar.registry.ILunarEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

public class ForgePlatformHelper implements IPlatformHelper {

	@Override
	public void syncEvent(Level level, ILunarEvent event) {
		PacketHandler.CHANNEL.send(PacketDistributor.ALL.noArg(), new SyncEventMessage(event));
	}

	@Override
	public void syncEvent(ServerPlayer player, ILunarEvent event) {
		PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SyncEventMessage(event));
	}

	@Override
	public void syncDeltaMovement(ServerPlayer player, Vec3 deltaMovement) {
		PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SyncDeltaMovement(deltaMovement));
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
	public ResourceLocation getEntityTypeLocation(EntityType<?> entityType) {
		return ForgeRegistries.ENTITY_TYPES.getKey(entityType);
	}

	@Override
	public EntityType<?> getEntityType(ResourceLocation location) {
		return ForgeRegistries.ENTITY_TYPES.getValue(location);
	}
}
