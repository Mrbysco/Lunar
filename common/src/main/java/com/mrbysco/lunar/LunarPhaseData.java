package com.mrbysco.lunar;

import com.mrbysco.lunar.api.ILunarEvent;
import com.mrbysco.lunar.platform.Services;
import com.mrbysco.lunar.registry.LunarRegistry;
import net.minecraft.core.HolderLookup;
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

	public CompoundTag save(CompoundTag compound, HolderLookup.Provider provider) {
		if (activeEvent != null) {
			compound.putString("event", activeEvent.getID().toString());
		}
		if (forcedEvent != null) {
			compound.putString("forcedEvent", forcedEvent.getID().toString());
		}
		return compound;
	}

	public static LunarPhaseData load(CompoundTag compound, HolderLookup.Provider provider) {
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
		return storage.computeIfAbsent(new SavedData.Factory<>(LunarPhaseData::new, LunarPhaseData::load, null), DATA_NAME);
	}

	public void setRandomLunarEvent(Level level) {
			float rng = random.nextFloat();
			if (rng <= Services.PLATFORM.getLunarChance()) {
				ILunarEvent event = LunarRegistry.instance().getRandomLunarEvent(level);
				attemptStartEvent(event, level);
				return;
			}
			setDefaultMoon();
	}

	/**
	 * Check if there is a forced event queued, and if so, set the current event to it
	 *
	 * @param level The server level being ticked
	 * @return True if there was a forced event and it was set, false otherwise
	 */
	public boolean executeForcedEvent(Level level) {
		if(forcedEvent != null) {
			attemptStartEvent(forcedEvent, level);
			setForcedEvent(null);
			return true;
		}
		return false;
	}

	/**
	 * Check if there is a lunar event configured for the current phase, and set it if so
	 *
	 * @param level The server level being ticked
	 * @return True if a phase event was set, false if there was no configured event for this phase
	 */
	public boolean setPhaseEvent(Level level) {
		ILunarEvent event = LunarRegistry.instance().getPhaseEvent(level);
		return attemptStartEvent(event, level);
	}

	/**
	 * Check if there is a lunar event configured for this day, and set it if so
	 *
	 * @param level The server level being ticked
	 * @return True if a day event was set, false if there was no configured event for this day
	 */
	public boolean setDayEvent(Level level) {
		ILunarEvent event = LunarRegistry.instance().getDayEvent(level);
		return attemptStartEvent(event, level);
	}

	/**
	 * Attempt to set a lunar event
	 *
	 * @param event The event to try setting
	 * @param level The server level being ticked
	 * @return True if the event was set, otherwise false
	 */
	public boolean attemptStartEvent(ILunarEvent event, Level level) {
		if (event == null)
			return false;

		Component startComponent = Component.translatable("lunar.event.start", Component.translatable(event.getTranslationKey()));
		level.players().forEach(player -> player.sendSystemMessage(startComponent));
		setActiveEvent(event);
		return true;
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
