package com.mrbysco.lunar;

import com.mrbysco.lunar.registry.LunarRegistry;

import java.util.List;

public class CommonClass {
	public static final List<String> DEFAULT_CRIMSON_REPLACEMENT = List.of(
			"minecraft:zombie,minecraft:zombified_piglin",
			"minecraft:husk,minecraft:zombified_piglin",
			"minecraft:drowned,minecraft:zombified_piglin",
			"minecraft:skeleton,minecraft:wither_skeleton",
			"minecraft:creeper,minecraft:ghast",
			"minecraft:slime,minecraft:magma_cube",
			"minecraft:spider,minecraft:zoglin");

	public static void initRegistry() {
		LunarRegistry.instance().initializeLunarEvents();
	}
}