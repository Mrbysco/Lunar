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
