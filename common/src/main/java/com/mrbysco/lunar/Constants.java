package com.mrbysco.lunar;

import net.minecraft.resources.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Constants {
	public static final String MOD_ID = "lunar";
	public static final String MOD_NAME = "Lunar";
	public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

	public static final Identifier SYNC_EVENT_ID = modLoc("sync_event");
	public static final Identifier SYNC_MOVEMENT_EVENT_ID = modLoc("sync_movement_event");

	public static Identifier modLoc(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}