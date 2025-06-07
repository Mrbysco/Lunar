package com.mrbysco.lunar;

import com.mrbysco.lunar.api.ILunarEvent;
import com.mrbysco.lunar.platform.Services;
import com.mrbysco.lunar.registry.LunarRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.Random;

public class LunarPhaseData extends SavedData {
	private static final Random random = new Random();
	private static final String DATA_NAME = Constants.MOD_ID + "_world_data";

	public ILunarEvent forcedEvent;
	public ILunarEvent activeEvent;

	public LunarPhaseData() {
		setActiveEvent(null);
		setForcedEvent(null);
	}

	public LunarPhaseData(ILunarEvent event, ILunarEvent forcedEvent) {
		setActiveEvent(event);
		setForcedEvent(forcedEvent);
	}

	public CompoundTag save(CompoundTag compound) {
		if (activeEvent != null) {
			compound.putString("event", activeEvent.getID().toString());
		}
		if (forcedEvent != null) {
			compound.putString("forcedEvent", forcedEvent.getID().toString());
		}
		return compound;
	}

	public static LunarPhaseData load(CompoundTag compound) {
		ResourceLocation eventID = compound.getString("event").isEmpty() ? null : ResourceLocation.tryParse(compound.getString("event"));
		ILunarEvent event = eventID != null ? LunarRegistry.instance().getEventByID(eventID) : null;

		ResourceLocation forcedEventID = compound.getString("forcedEvent").isEmpty() ? null : ResourceLocation.tryParse(compound.getString("forcedEvent"));
		ILunarEvent forcedEvent = forcedEventID != null ? LunarRegistry.instance().getEventByID(forcedEventID) : null;

		return new LunarPhaseData(event, forcedEvent);
	}

	public static LunarPhaseData get(Level level) {
		if (!(level instanceof ServerLevel)) {
			throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
		}
		ServerLevel overworld = level.getServer().getLevel(Level.OVERWORLD);

		DimensionDataStorage storage = overworld.getDataStorage();
		return storage.computeIfAbsent(LunarPhaseData::load, LunarPhaseData::new, DATA_NAME);
	}

	public void setRandomLunarEvent(Level level) {
		if (forcedEvent != null) {
			Component startComponent = Component.translatable("lunar.event.start", Component.translatable(forcedEvent.getTranslationKey()));
			level.players().forEach(player -> player.sendSystemMessage(startComponent));
			setActiveEvent(forcedEvent);
			setForcedEvent(null);
		} else {
			float rng = random.nextFloat();
			if (rng <= Services.PLATFORM.getLunarChance()) {
				ILunarEvent event = LunarRegistry.instance().getRandomLunarEvent(level);
				if (event != null) {
					Component startComponent = Component.translatable("lunar.event.start", Component.translatable(event.getTranslationKey()));
					level.players().forEach(player -> player.sendSystemMessage(startComponent));
					setActiveEvent(event);
					return;
				}
			}
			setDefaultMoon();
		}
	}

	public void setForcedEvent(ILunarEvent event) {
		this.forcedEvent = event;
		setDirty();
	}

	public void eraseEvent() {
		this.setActiveEvent(null);
	}

	public void setDefaultMoon() {
		this.setActiveEvent(LunarRegistry.getDefaultMoon());
		this.setForcedEvent(null);
	}

	public void setActiveEvent(ILunarEvent storage) {
		this.activeEvent = storage;
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
