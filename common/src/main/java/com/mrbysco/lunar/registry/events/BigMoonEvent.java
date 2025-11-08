package com.mrbysco.lunar.registry.events;

import com.mrbysco.lunar.Constants;
import com.mrbysco.lunar.api.LunarEvent;
import com.mrbysco.lunar.handler.result.EventResult;
import com.mrbysco.lunar.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;

public class BigMoonEvent extends LunarEvent {
	private static final ResourceLocation MOON_TEXTURE = Constants.modLoc("textures/environment/big.png");
	public static final AttributeModifier GRAVITY_MODIFIER = new AttributeModifier(
			Constants.modLoc("big_moon_gravity").toString(),
			0.04F,
			Operation.ADDITION
	);

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
			Attribute gravityAttribute = Services.PLATFORM.getGravityAttribute();
			AttributeInstance attributeInstance = livingEntity.getAttribute(gravityAttribute);
			if (attributeInstance != null && !attributeInstance.hasModifier(GRAVITY_MODIFIER)) {
				attributeInstance.addTransientModifier(
						GRAVITY_MODIFIER
				);
			}
		}
	}

	@Override
	public void removeEntityEffect(Entity entity) {
		if (entity instanceof LivingEntity livingEntity) {
			Attribute gravityAttribute = Services.PLATFORM.getGravityAttribute();
			AttributeInstance attributeInstance = livingEntity.getAttribute(gravityAttribute);
			if (attributeInstance != null && attributeInstance.hasModifier(GRAVITY_MODIFIER)) {
				attributeInstance.removeModifier(GRAVITY_MODIFIER);
			}
		}
	}

	@Override
	public ResourceLocation moonTexture() {
		return MOON_TEXTURE;
	}

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
