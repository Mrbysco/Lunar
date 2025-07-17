package com.mrbysco.lunar;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrbysco.lunar.api.ILunarEvent;
import com.mrbysco.lunar.platform.Services;
import com.mrbysco.lunar.registry.LunarRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class LunarPhaseData extends SavedData {
	private static final Random random = new Random();
	private static final String DATA_NAME = Constants.MOD_ID + "_world_data";
	public static final Codec<LunarPhaseData> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
							ResourceLocation.CODEC.optionalFieldOf("forcedEvent").forGetter(data -> data.forcedEventID),
							ResourceLocation.CODEC.optionalFieldOf("activeEvent").forGetter(data -> data.activeEventID)
					)
					.apply(instance, LunarPhaseData::new)
	);

	public Optional<ResourceLocation> forcedEventID;
	public Optional<ResourceLocation> activeEventID;

	public ILunarEvent forcedEvent;
	public ILunarEvent activeEvent;

	private LunarPhaseData() {
		this(Optional.empty(), Optional.empty());
	}

	public LunarPhaseData(Optional<ResourceLocation> forcedEventID, Optional<ResourceLocation> activeEventID) {
		this.forcedEventID = activeEventID;
		ILunarEvent event = activeEventID.map(location -> LunarRegistry.instance().getEventByID(location)).orElse(null);
		setActiveEvent(event);

		this.activeEventID = forcedEventID;
		ILunarEvent forcedEvent = forcedEventID.map(location -> LunarRegistry.instance().getEventByID(location)).orElse(null);
		setForcedEvent(forcedEvent);
	}

	@SuppressWarnings("DataFlowIssue")
	public static SavedDataType<LunarPhaseData> type() {
		return new SavedDataType<>(DATA_NAME, LunarPhaseData::new, CODEC, null);
	}

	public static LunarPhaseData get(Level level) {
		if (!(level instanceof ServerLevel)) {
			throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
		}
		ServerLevel overworld = level.getServer().getLevel(Level.OVERWORLD);

		assert overworld != null;
		DimensionDataStorage storage = overworld.getDataStorage();
		return storage.computeIfAbsent(type());
	}

	public void setRandomLunarEvent(@NotNull ServerLevel serverLevel) {
		if (forcedEvent != null) {
			Component startComponent = Component.translatable("lunar.event.start", Component.translatable(forcedEvent.getTranslationKey()));
			serverLevel.players().forEach(player -> player.sendSystemMessage(startComponent));
			setActiveEvent(forcedEvent);
			setForcedEvent(null);
		} else {
			float rng = random.nextFloat();
			if (rng <= Services.PLATFORM.getLunarChance()) {
				ILunarEvent event = LunarRegistry.instance().getRandomLunarEvent(serverLevel);
				if (event != null) {
					Component startComponent = Component.translatable("lunar.event.start", Component.translatable(event.getTranslationKey()));
					serverLevel.players().forEach(player -> player.sendSystemMessage(startComponent));
					setActiveEvent(event);
					return;
				}
			}
			setDefaultMoon();
		}
	}

	public void setForcedEvent(@Nullable ILunarEvent event) {
		this.forcedEvent = event;
		this.forcedEventID = Optional.ofNullable(event != null ? event.getID() : null);
		setDirty();
	}

	public void eraseEvent() {
		this.setActiveEvent(null);
	}

	public void setDefaultMoon() {
		this.setActiveEvent(LunarRegistry.getDefaultMoon());
		this.setForcedEvent(null);
	}

	public void setActiveEvent(@Nullable ILunarEvent event) {
		this.activeEvent = event;
		this.activeEventID = Optional.ofNullable(event != null ? event.getID() : null);
		setDirty();
	}

	public void syncEvent(Level level) {
		Services.PLATFORM.syncEvent(level, activeEvent != null ? activeEvent : null);
	}

	public void syncEvent(ServerPlayer player) {
		Services.PLATFORM.syncEvent(player, activeEvent != null ? activeEvent : null);
	}

	public ILunarEvent getActiveLunarEvent() {
		return activeEvent != null ? activeEvent : null;
	}

	public boolean hasEventActive() {
		return activeEvent != null;
	}
}
