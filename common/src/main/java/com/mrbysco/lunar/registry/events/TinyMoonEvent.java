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

public class TinyMoonEvent extends LunarEvent {
	private static final Identifier MOON_LOCATION = Constants.modLoc("tiny");
	private static final Pair<Holder<Attribute>, Identifier> TINY_MOON_MODIFIER_PAIR = Pair.of(Attributes.GRAVITY, Constants.modLoc("tiny_moon_modifier"));

	public TinyMoonEvent() {
		super(Constants.modLoc("tiny_moon"), 0xFFFFF1);
	}

	@Override
	public int spawnWeight() {
		return Services.PLATFORM.getTinyMoonWeight();
	}

	@Override
	public String getTranslationKey() {
		return "lunar.event.tiny_moon";
	}

	@Override
	public boolean applyEntityEffect() {
		return true;
	}

	@Override
	public void applyEntityEffect(Entity entity) {
		if (entity instanceof LivingEntity livingEntity) {
			AttributeInstance attributeInstance = livingEntity.getAttribute(TINY_MOON_MODIFIER_PAIR.getLeft());
			if (attributeInstance != null && !attributeInstance.hasModifier(TINY_MOON_MODIFIER_PAIR.getRight())) {
				attributeInstance.addTransientModifier(
						new AttributeModifier(TINY_MOON_MODIFIER_PAIR.getRight(), -0.06F, Operation.ADD_VALUE)
				);
			}
		}
	}

	@Override
	public void removeEntityEffect(Entity entity) {
		if (entity instanceof LivingEntity livingEntity) {
			AttributeInstance attributeInstance = livingEntity.getAttribute(TINY_MOON_MODIFIER_PAIR.getLeft());
			if (attributeInstance != null && attributeInstance.hasModifier(TINY_MOON_MODIFIER_PAIR.getRight())) {
				attributeInstance.removeModifier(TINY_MOON_MODIFIER_PAIR.getRight());
			}
		}
	}

	public List<Pair<Holder<Attribute>, Identifier>> getAttributePairs() {
		return List.of(TINY_MOON_MODIFIER_PAIR);
	}

	@Override
	public Identifier moonTexture() {
		return MOON_LOCATION;
	}

	@Override
	public float moonScale() {
		return 0.25F;
	}

	@Override
	public EventResult canSleep(Player player, BlockPos sleepingLocation) {
		if (!Services.PLATFORM.canSleepIn(getID()))
			return EventResult.DENY;
		return EventResult.DEFAULT;
	}
}
