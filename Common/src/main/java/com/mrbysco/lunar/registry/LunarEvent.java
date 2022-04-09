package com.mrbysco.lunar.registry;

import net.minecraft.resources.ResourceLocation;

public abstract class LunarEvent implements ILunarEvent {
	private final ResourceLocation resourceLocation;
	private int moonColor;

	public LunarEvent(ResourceLocation resourceLocation, int moonColor) {
		this.resourceLocation = resourceLocation;
		this.moonColor = moonColor;
	}

	@Override
	public ResourceLocation getID() {
		return resourceLocation;
	}

	@Override
	public int moonColor() {
		return moonColor;
	}

	@Override
	public String toString() {
		return "LunarEvent{" +
				"resourceLocation=" + resourceLocation +
				", moonColor=" + moonColor +
				", weight=" + spawnWeight() +
				'}';
	}
}
