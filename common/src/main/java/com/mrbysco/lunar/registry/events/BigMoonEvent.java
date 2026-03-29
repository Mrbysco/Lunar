package com.mrbysco.lunar.registry.events;

import com.mrbysco.lunar.Constants;
import com.mrbysco.lunar.api.LunarEvent;
import com.mrbysco.lunar.handler.result.EventResult;
import com.mrbysco.lunar.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class BigMoonEvent extends LunarEvent {
	private static final Identifier MOON_LOCATION = Constants.modLoc("big");
	private static final Pair<Holder<Attribute>, Identifier> BIG_MOON_MODIFIER_PAIR = Pair.of(Attributes.GRAVITY, Constants.modLoc("big_moon_modifier"));

	public BigMoonEvent() {
		super(Constants.modLoc("big_moon"), 0xFFFFF1);
	}

	@Override
	public int spawnWeight() {
		return Services.PLATFORM.getBigMoonWeight();
	}

	@Override
	public String getTranslationKey() {
		return "lunar.event.big_moon";
	}

	@Override
	public boolean applyEntityEffect() {
		return true;
	}

	@Override
	public void applyEntityEffect(Entity entity) {
		if (entity instanceof LivingEntity livingEntity) {
			AttributeInstance attributeInstance = livingEntity.getAttribute(BIG_MOON_MODIFIER_PAIR.getLeft());
			if (attributeInstance != null && !attributeInstance.hasModifier(BIG_MOON_MODIFIER_PAIR.getRight())) {
				attributeInstance.addTransientModifier(
						new AttributeModifier(BIG_MOON_MODIFIER_PAIR.getRight(), 0.04F, Operation.ADD_VALUE)
				);
			}
		}
	}

	@Override
	public void removeEntityEffect(Entity entity) {
		if (entity instanceof LivingEntity livingEntity) {
			AttributeInstance attributeInstance = livingEntity.getAttribute(BIG_MOON_MODIFIER_PAIR.getLeft());
			if (attributeInstance != null && attributeInstance.hasModifier(BIG_MOON_MODIFIER_PAIR.getRight())) {
				attributeInstance.removeModifier(BIG_MOON_MODIFIER_PAIR.getRight());
			}
		}
	}

	@Override
	public List<Pair<Holder<Attribute>, Identifier>> getAttributePairs() {
		return List.of(BIG_MOON_MODIFIER_PAIR);
	}

	@Override
	public Identifier moonTexture() {
		return MOON_LOCATION;
	}

	@Override
	public float moonScale() {
		return 4.0F;
	}

	@Override
	public EventResult canSleep(Player player, BlockPos sleepingLocation) {
		if (!Services.PLATFORM.canSleepIn(getID()))
			return EventResult.DENY;
		return EventResult.DEFAULT;
	}
}
