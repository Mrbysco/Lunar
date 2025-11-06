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
	public final General general = new General();

	@CollapsibleObject
	public final Events events = new Events();

	@CollapsibleObject
	public final Crimson crimson = new Crimson();

	@CollapsibleObject
	public BadOmen badomen = new BadOmen();

	@CollapsibleObject
	public Sleeping sleeping = new Sleeping();

	@SuppressWarnings("CanBeFinal")
	public static class General {
		//General
		@Comment("Chance of a Lunar Event happening when night falls [Default: 0.4]")
		@BoundedDiscrete(min = 0, max = 1)
		public float lunarEventChance = 0.4F;
	}

	@SuppressWarnings("CanBeFinal")
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

	@SuppressWarnings("CanBeFinal")
	public static class Crimson {
		@Comment("List of entities to replace during the Crimson Moon Event [Example: \"minecraft:zombie,minecraft:zombified_piglin\"]")
		public List<String> crimsonReplacements = CommonClass.DEFAULT_CRIMSON_REPLACEMENT;
	}

	@SuppressWarnings("CanBeFinal")
	public static class BadOmen {
		@Comment("The max level of Bad Omen effect applied during the Bad Omen Moon Event (It will apply a level between 1 and this max) [Default: 3]")
		public int maxBadOmen = 3;
	}

	@SuppressWarnings("CanBeFinal")
	public static class Sleeping {
		//Sleeping
		@Comment("Allow sleeping during the Blood Moon Event [Default: true]")
		public boolean bloodMoonSleeping = true;

		@Comment("Allow sleeping during the Crimson Moon Event [Default: true]")
		public boolean crimsonMoonSleeping = true;

		@Comment("Allow sleeping during the Miner Moon Event [Default: true]")
		public boolean minerMoonSleeping = true;

		@Comment("Allow sleeping during the White Moon Event [Default: true]")
		public boolean whiteMoonSleeping = true;

		@Comment("Allow sleeping during the Tiny Moon Event [Default: true]")
		public boolean tinyMoonSleeping = true;

		@Comment("Allow sleeping during the Big Moon Event [Default: true]")
		public boolean bigMoonSleeping = true;

		@Comment("Allow sleeping during the Bad Omen Moon Event [Default: true]")
		public boolean badOmenMoonSleeping = true;

		@Comment("Allow sleeping during the Hero Moon Event [Default: true]")
		public boolean heroMoonSleeping = true;

		@Comment("Allow sleeping during the Eclipse Moon Event [Default: true]")
		public boolean eclipseMoonSleeping = true;
	}
}
