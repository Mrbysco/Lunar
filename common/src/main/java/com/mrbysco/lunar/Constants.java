package com.mrbysco.lunar;

import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Constants {
	public static final String MOD_ID = "lunar";
	public static final String MOD_NAME = "Lunar";
	public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

	public static final ResourceLocation SYNC_EVENT_ID = modLoc("sync_event");
	public static final ResourceLocation SYNC_MOVEMENT_EVENT_ID = modLoc("sync_movement_event");

	public static ResourceLocation modLoc(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}