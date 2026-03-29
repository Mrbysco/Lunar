package com.mrbysco.lunar.config;

import com.google.common.collect.Maps;
import net.minecraft.resources.Identifier;

import java.util.Map;

public class ConfigHelper {

	public static float getLunarChance() {
		return LunarConfig.COMMON.lunarEventChance.get().floatValue();
	}

	public static int getBloodMoonWeight() {
		return LunarConfig.COMMON.bloodMoonWeight.get();
	}

	public static int getCrimsonMoonWeight() {
		return LunarConfig.COMMON.crimsonMoonWeight.get();
	}

	public static int getMinerMoonWeight() {
		return LunarConfig.COMMON.minerMoonWeight.get();
	}

	public static int getWhiteMoonWeight() {
		return LunarConfig.COMMON.whiteMoonWeight.get();
	}

	public static int getBigMoonWeight() {
		return LunarConfig.COMMON.bigMoonWeight.get();
	}

	public static int getTinyMoonWeight() {
		return LunarConfig.COMMON.tinyMoonWeight.get();
	}

	public static int getBadOmenMoonWeight() {
		return LunarConfig.COMMON.badOmenMoonWeight.get();
	}

	public static int getHeroMoonWeight() {
		return LunarConfig.COMMON.heroMoonWeight.get();
	}

	public static int getEclipseMoonWeight() {
		return LunarConfig.COMMON.eclipseMoonWeight.get();
	}

	public static Map<Identifier, Identifier> getCrimsonReplacementMap() {
		Map<Identifier, Identifier> map = Maps.newHashMap();
		LunarConfig.COMMON.crimsonReplacements.get().forEach(entry -> {
			if (entry.contains(",")) {
				String[] split = entry.split(",");
				map.put(Identifier.tryParse(split[0]), Identifier.tryParse(split[1]));
			}
		});
		return map;
	}

	public static boolean canSleepIn(Identifier moonID) {
		boolean result = true;
		switch (moonID.toString()) {
			case "lunar:blood_moon" -> result = LunarConfig.COMMON.bloodMoonSleeping.get();
			case "lunar:crimson_moon" -> result = LunarConfig.COMMON.crimsonMoonSleeping.get();
			case "lunar:miner_moon" -> result = LunarConfig.COMMON.minerMoonSleeping.get();
			case "lunar:white_moon" -> result = LunarConfig.COMMON.whiteMoonSleeping.get();
			case "lunar:big_moon" -> result = LunarConfig.COMMON.bigMoonSleeping.get();
			case "lunar:tiny_moon" -> result = LunarConfig.COMMON.tinyMoonSleeping.get();
			case "lunar:bad_omen_moon" -> result = LunarConfig.COMMON.badOmenMoonSleeping.get();
			case "lunar:hero_moon" -> result = LunarConfig.COMMON.heroMoonSleeping.get();
			case "lunar:eclipse_moon" -> result = LunarConfig.COMMON.eclipseMoonSleeping.get();
		}
		return result;
	}

	public static int maxBadOmen() {
		return LunarConfig.COMMON.maxBadOmen.get();
	}
}
