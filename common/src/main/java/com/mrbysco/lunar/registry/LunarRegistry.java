package com.mrbysco.lunar.registry;

import com.google.common.collect.Maps;
import com.mrbysco.lunar.Constants;
import com.mrbysco.lunar.registry.events.BadOmenMoonEvent;
import com.mrbysco.lunar.registry.events.BigMoonEvent;
import com.mrbysco.lunar.registry.events.BloodMoonEvent;
import com.mrbysco.lunar.registry.events.CrimsonMoonEvent;
import com.mrbysco.lunar.registry.events.HeroMoonEvent;
import com.mrbysco.lunar.registry.events.MinerMoonEvent;
import com.mrbysco.lunar.registry.events.TinyMoonEvent;
import com.mrbysco.lunar.registry.events.WhiteMoonEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.apache.commons.compress.utils.Lists;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class LunarRegistry {
	private static LunarRegistry INSTANCE;
	private final Map<ResourceLocation, ILunarEvent> eventMap = Maps.newHashMap();
	private final List<ILunarEvent> eventList = Lists.newArrayList();

	public static LunarRegistry instance() {
		if (INSTANCE == null)
			INSTANCE = new LunarRegistry();
		return INSTANCE;
	}

	public void initializeLunarEvents() {
		eventMap.clear();
		eventList.clear();

		addEvent(new BloodMoonEvent());
		addEvent(new CrimsonMoonEvent());
		addEvent(new MinerMoonEvent());
		addEvent(new WhiteMoonEvent());
		addEvent(new BigMoonEvent());
		addEvent(new TinyMoonEvent());
		addEvent(new BadOmenMoonEvent());
		addEvent(new HeroMoonEvent());

		sortByWeight();
	}

	public void sortByWeight() {
		eventList.sort(Comparator.comparingInt(ILunarEvent::spawnWeight));
	}

	public void addEvent(ILunarEvent event) {
		ResourceLocation id = event.getID();
		if (!eventMap.containsKey(id)) {
			Constants.LOGGER.debug("Adding Lunar Event: " + id.toString());
			eventMap.put(id, event);
			eventList.add(event);
		} else {
			Constants.LOGGER.error("Failed to add lunar event. There was an attempt to add duplicate lunar event {} of class {}", id, event.getClass().getName());
		}
	}

	public ILunarEvent getEventByID(ResourceLocation ID) {
		if (eventMap.containsKey(ID)) {
			return eventMap.get(ID);
		}
		return null;
	}

	public ILunarEvent getRandomLunarEvent(Level level) {
		List<ILunarEvent> eventCopy = new ArrayList<>(eventList);
		eventCopy.removeIf(event -> !event.canAppear(level));

		if (eventCopy.isEmpty()) {
			return null;
		}

		// Compute the total weight of all memes together
		double totalWeight = 0.0d;
		for (ILunarEvent i : eventCopy) {
			totalWeight += i.spawnWeight();
		}
		// Now choose a random meme
		int randomIndex = -1;
		double random = Math.random() * totalWeight;
		for (int i = 0; i < eventCopy.size(); ++i) {
			random -= eventCopy.get(i).spawnWeight();
			if (random <= 0.0d) {
				randomIndex = i;
				break;
			}
		}
		return eventCopy.get(randomIndex);
	}

	public List<String> getIDList() {
		List<String> list = Lists.newArrayList();
		for (ILunarEvent i : eventList) {
			list.add(i.getID().toString());
		}
		return list;
	}
}
