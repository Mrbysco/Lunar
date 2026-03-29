package com.mrbysco.lunar.api;

import net.minecraft.resources.Identifier;

public abstract class LunarEvent implements ILunarEvent {
	private final Identifier resourceLocation;
	private final int moonColor;

	public LunarEvent(Identifier resourceLocation, int moonColor) {
		this.resourceLocation = resourceLocation;
		this.moonColor = moonColor;
	}

	@Override
	public Identifier getID() {
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
