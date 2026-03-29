package com.mrbysco.lunar.api;

import net.minecraft.resources.Identifier;

public abstract class LunarEvent implements ILunarEvent {
	private final Identifier identifier;
	private final int moonColor;

	public LunarEvent(Identifier identifier, int moonColor) {
		this.identifier = identifier;
		this.moonColor = moonColor;
	}

	@Override
	public Identifier getID() {
		return identifier;
	}

	@Override
	public int moonColor() {
		return moonColor;
	}

	@Override
	public String toString() {
		return "LunarEvent{" +
				"identifier=" + identifier +
				", moonColor=" + moonColor +
				", weight=" + spawnWeight() +
				'}';
	}
}
