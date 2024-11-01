package com.mrbysco.lunar.config;

import com.mrbysco.lunar.CommonClass;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class LunarConfig {
	public static class Common {
		public final DoubleValue lunarEventChance;

		public final IntValue bloodMoonWeight;
		public final IntValue crimsonMoonWeight;
		public final IntValue minerMoonWeight;
		public final IntValue whiteMoonWeight;
		public final IntValue tinyMoonWeight;
		public final IntValue bigMoonWeight;
		public final IntValue badOmenMoonWeight;
		public final IntValue heroMoonWeight;
		public final IntValue eclipseMoonWeight;
		public final IntValue bloodMoonPhase;
		public final IntValue crimsonMoonPhase;
		public final IntValue minerMoonPhase;
		public final IntValue whiteMoonPhase;
		public final IntValue bigMoonPhase;
		public final IntValue tinyMoonPhase;
		public final IntValue badOmenMoonPhase;
		public final IntValue heroMoonPhase;
		public final IntValue eclipseMoonPhase;
		public final IntValue bloodMoonDay;
		public final IntValue crimsonMoonDay;
		public final IntValue minerMoonDay;
		public final IntValue whiteMoonDay;
		public final IntValue bigMoonDay;
		public final IntValue tinyMoonDay;
		public final IntValue badOmenMoonDay;
		public final IntValue heroMoonDay;
		public final IntValue eclipseMoonDay;


		public final ConfigValue<List<? extends String>> crimsonReplacements;

		Common(ForgeConfigSpec.Builder builder) {
			builder.comment("General settings")
					.push("General");

			lunarEventChance = builder
					.comment("Chance of a Lunar Event happening when night falls [Default: 0.4]")
					.defineInRange("lunarEventChance", 0.4, 0, 1);

			builder.pop();
			builder.comment("Event settings")
					.push("Events");

			bloodMoonWeight = builder
					.comment("Weight of the Blood Moon Event [Default: 60]")
					.defineInRange("bloodMoonWeight", 60, 0, 100);

			crimsonMoonWeight = builder
					.comment("Weight of the Crimson Moon Event [Default: 20]")
					.defineInRange("crimsonMoonWeight", 20, 0, 100);

			minerMoonWeight = builder
					.comment("Weight of the Miner Moon Event [Default: 40]")
					.defineInRange("minerMoonWeight", 40, 0, 100);

			whiteMoonWeight = builder
					.comment("Weight of the White Moon Event [Default: 10]")
					.defineInRange("whiteMoonWeight", 10, 0, 100);

			tinyMoonWeight = builder
					.comment("Weight of the Tiny Moon Event [Default: 10]")
					.defineInRange("tinyMoonWeight", 10, 0, 100);

			bigMoonWeight = builder
					.comment("Weight of the Big Moon Event [Default: 10]")
					.defineInRange("bigMoonWeight", 10, 0, 100);

			badOmenMoonWeight = builder
					.comment("Weight of the Big Moon Event [Default: 5]")
					.defineInRange("badOmenMoonWeight", 5, 0, 100);

			heroMoonWeight = builder
					.comment("Weight of the Hero Moon Event [Default: 5]")
					.defineInRange("heroMoonWeight", 5, 0, 100);

			eclipseMoonWeight = builder
					.comment("Weight of the Eclipse Moon Event [Default: 2]")
					.defineInRange("eclipseMoonWeight", 2, 0, 100);

			builder.pop();
			builder.comment("Moon phase settings. -1 is disabled, 0-7 are moon phases, starting with a full moon at 0.")
					.push("Phases");

			bloodMoonPhase = builder
					.comment("Moon phase that the Blood Moon should trigger on. [Default: -1]")
					.defineInRange("bloodMoonPhase", -1, -1, 7);

			crimsonMoonPhase = builder
					.comment("Moon phase that the Crimson Moon should trigger on. [Default: -1]")
					.defineInRange("crimsonMoonPhase", -1, -1, 7);

			minerMoonPhase = builder
					.comment("Moon phase that the Miner Moon should trigger on. [Default: -1]")
					.defineInRange("minerMoonPhase", -1, -1, 7);

			whiteMoonPhase = builder
					.comment("Moon phase that the White Moon should trigger on. [Default: -1]")
					.defineInRange("whiteMoonPhase", -1, -1, 7);

			bigMoonPhase = builder
					.comment("Moon phase that the Big Moon should trigger on. [Default: -1]")
					.defineInRange("bigMoonPhase", -1, -1, 7);

			tinyMoonPhase = builder
					.comment("Moon phase that the Tiny Moon should trigger on. [Default: -1]")
					.defineInRange("tinyMoonPhase", -1, -1, 7);

			badOmenMoonPhase = builder
					.comment("Moon phase that the Bad Omen Moon should trigger on. [Default: -1]")
					.defineInRange("badOmenMoonPhase", -1, -1, 7);

			heroMoonPhase = builder
					.comment("Moon phase that the Hero Moon should trigger on. [Default: -1]")
					.defineInRange("heroMoonPhase", -1, -1, 7);

			eclipseMoonPhase = builder
					.comment("Moon phase that the Eclipse Moon should trigger on. [Default: -1]")
					.defineInRange("eclipseMoonPhase", -1, -1, 7);

			builder.pop();
			builder.comment("Lunar event day settings. 0 is disabled, anything above is enabled.")
					.push("Days");

			bloodMoonDay = builder
					.comment("Triggers the Blood Moon every number of days, as defined. [Default: 0]")
					.defineInRange("bloodMoonDay", 0, 0, 255);

			crimsonMoonDay = builder
					.comment("Triggers the Crimson Moon every number of days, as defined. [Default: 0]")
					.defineInRange("crimsonMoonDay", 0, 0, 255);

			minerMoonDay = builder
					.comment("Triggers the Miner Moon every number of days, as defined. [Default: 0]")
					.defineInRange("minerMoonDay", 0, 0, 255);

			whiteMoonDay = builder
					.comment("Triggers the White Moon every number of days, as defined. [Default: 0]")
					.defineInRange("whiteMoonDay", 0, 0, 255);

			bigMoonDay = builder
					.comment("Triggers the Big Moon every number of days, as defined. [Default: 0]")
					.defineInRange("bigMoonDay", 0, 0, 255);

			tinyMoonDay = builder
					.comment("Triggers the Tiny Moon every number of days, as defined. [Default: 0]")
					.defineInRange("tinyMoonDay", 0, 0, 255);

			badOmenMoonDay = builder
					.comment("Triggers the Bad Omen Moon every number of days, as defined. [Default: 0]")
					.defineInRange("badOmenMoonDay", 0, 0, 255);

			heroMoonDay = builder
					.comment("Triggers the Hero Moon every number of days, as defined. [Default: 0]")
					.defineInRange("heroMoonDay", 0, 0, 255);

			eclipseMoonDay = builder
					.comment("Triggers the Eclipse Moon every number of days, as defined. [Default: 0]")
					.defineInRange("eclipseMoonDay", 0, 0, 255);

			builder.pop();
			builder.comment("Crimson Replacement settings")
					.push("Crimson");

			crimsonReplacements = builder
					.comment("List of entities to replace during the Crimson Moon Event [Example: \"minecraft:zombie,minecraft:zombified_piglin\"]")
					.defineListAllowEmpty(List.of("crimsonReplacements"), () -> CommonClass.DEFAULT_CRIMSON_REPLACEMENT, o -> (o instanceof String entry && entry.contains(",")));

			builder.pop();
		}
	}

	public static final ForgeConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}
}
