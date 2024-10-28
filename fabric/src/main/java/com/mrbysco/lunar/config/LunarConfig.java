package com.mrbysco.lunar.config;

import com.mrbysco.lunar.CommonClass;
import com.mrbysco.lunar.Constants;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.BoundedDiscrete;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.util.List;

@Config(name = Constants.MOD_ID)
public class LunarConfig implements ConfigData {
	@CollapsibleObject
	public General general = new General();

	@CollapsibleObject
	public Events events = new Events();

	@CollapsibleObject
	public Crimson crimson = new Crimson();

	@CollapsibleObject
	public Phases phases = new Phases();

	@CollapsibleObject
	public Days days = new Days();

	public static class General {
		//General
		@Comment("Chance of a Lunar Event happening when night falls [Default: 0.4]")
		@BoundedDiscrete(min = 0, max = 1)
		public float lunarEventChance = 0.4F;
	}

	public static class Events {
		//Events
		@Comment("Weight of the Blood Moon Event [Default: 60]")
		@BoundedDiscrete(min = 0, max = 100)
		public int bloodMoonWeight = 60;

		@Comment("Weight of the Blood Moon Event [Default: 20]")
		@BoundedDiscrete(min = 0, max = 100)
		public int crimsonMoonWeight = 20;

		@Comment("Weight of the Blood Moon Event [Default: 40]")
		@BoundedDiscrete(min = 0, max = 100)
		public int minerMoonWeight = 40;

		@Comment("Weight of the Blood Moon Event [Default: 10]")
		@BoundedDiscrete(min = 0, max = 100)
		public int whiteMoonWeight = 10;

		@Comment("Weight of the Tiny Moon Event [Default: 10]")
		@BoundedDiscrete(min = 0, max = 100)
		public int tinyMoonWeight = 10;

		@Comment("Weight of the Big Moon Event [Default: 10]")
		@BoundedDiscrete(min = 0, max = 100)
		public int bigMoonWeight = 10;

		@Comment("Weight of the Bad Omen Moon Event [Default: 5]")
		@BoundedDiscrete(min = 0, max = 100)
		public int badOmenMoonWeight = 5;

		@Comment("Weight of the Hero Moon Event [Default: 5]")
		@BoundedDiscrete(min = 0, max = 100)
		public int heroMoonWeight = 5;

		@Comment("Weight of the Eclipse Moon Event [Default: 2]")
		@BoundedDiscrete(min = 0, max = 100)
		public int eclipseMoonWeight = 2;
	}

	public static class Phases {
		@Comment("Moon phase that the Blood Moon should trigger on. [Default: -1]")
		@BoundedDiscrete(min = -1, max = 7)
		public int bloodMoonPhase = -1;

		@Comment("Moon phase that the Crimson Moon should trigger on. [Default: -1]")
		@BoundedDiscrete(min = -1, max = 7)
		public int crimsonMoonPhase = -1;

		@Comment("Moon phase that the Miner Moon should trigger on. [Default: -1]")
		@BoundedDiscrete(min = -1, max = 7)
		public int minerMoonPhase = -1;

		@Comment("Moon phase that the White Moon should trigger on. [Default: -1]")
		@BoundedDiscrete(min = -1, max = 7)
		public int whiteMoonPhase = -1;

		@Comment("Moon phase that the Tiny Moon should trigger on. [Default: -1]")
		@BoundedDiscrete(min = -1, max = 7)
		public int tinyMoonPhase = -1;

		@Comment("Moon phase that the Big Moon should trigger on. [Default: -1]")
		@BoundedDiscrete(min = -1, max = 7)
		public int bigMoonPhase = -1;

		@Comment("Moon phase that the Bad Omen Moon should trigger on. [Default: -1]")
		@BoundedDiscrete(min = -1, max = 7)
		public int badOmenMoonPhase = -1;

		@Comment("Moon phase that the Hero Moon should trigger on. [Default: -1]")
		@BoundedDiscrete(min = -1, max = 7)
		public int heroMoonPhase = -1;

		@Comment("Moon phase that the Eclipse Moon should trigger on. [Default: -1]")
		@BoundedDiscrete(min = -1, max = 7)
		public int eclipseMoonPhase = -1;
	}

	public static class Days {
		@Comment("Triggers the Blood Moon every number of days, as defined. [Default: 0; Disabled]")
		@BoundedDiscrete(min = 0, max = 255)
		public int bloodMoonDay = 0;

		@Comment("Triggers the Crimson Moon every number of days, as defined. [Default: 0; Disabled]")
		@BoundedDiscrete(min = 0, max = 255)
		public int crimsonMoonDay = 0;

		@Comment("Triggers the Miner Moon every number of days, as defined. [Default: 0; Disabled]")
		@BoundedDiscrete(min = 0, max = 255)
		public int minerMoonDay = 0;

		@Comment("Triggers the White Moon every number of days, as defined. [Default: 0; Disabled]")
		@BoundedDiscrete(min = 0, max = 255)
		public int whiteMoonDay = 0;

		@Comment("Triggers the Big Moon every number of days, as defined. [Default: 0; Disabled]")
		@BoundedDiscrete(min = 0, max = 255)
		public int bigMoonDay = 0;

		@Comment("Triggers the Tiny Moon every number of days, as defined. [Default: 0; Disabled]")
		@BoundedDiscrete(min = 0, max = 255)
		public int tinyMoonDay = 0;

		@Comment("Triggers the Bad Omen Moon every number of days, as defined. [Default: 0; Disabled]")
		@BoundedDiscrete(min = 0, max = 255)
		public int badOmenMoonDay = 0;

		@Comment("Triggers the Hero Moon every number of days, as defined. [Default: 0; Disabled]")
		@BoundedDiscrete(min = 0, max = 255)
		public int heroMoonDay = 0;

		@Comment("Triggers the Eclipse Moon every number of days, as defined. [Default: 0; Disabled]")
		@BoundedDiscrete(min = 0, max = 255)
		public int eclipseMoonDay = 0;
	}

	public static class Crimson {
		@Comment("List of entities to replace during the Crimson Moon Event [Example: \"minecraft:zombie,minecraft:zombified_piglin\"]")
		public List<String> crimsonReplacements = CommonClass.DEFAULT_CRIMSON_REPLACEMENT;
	}
}
