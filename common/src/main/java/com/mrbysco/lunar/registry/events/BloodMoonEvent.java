package com.mrbysco.lunar.registry.events;

import com.mrbysco.lunar.Constants;
import com.mrbysco.lunar.api.LunarEvent;
import com.mrbysco.lunar.handler.result.EventResult;
import com.mrbysco.lunar.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class BloodMoonEvent extends LunarEvent {
	private static final ResourceLocation DAMAGE_MODIFIER_UUID = Constants.modLoc("blood_moon_damage_modifier");
	private static final ResourceLocation HEALTH_MODIFIER_UUID = Constants.modLoc("blood_moon_health_modifier");

	public BloodMoonEvent() {
		super(Constants.modLoc("blood_moon"), 0x882e2e);
	}

	@Override
	public int spawnWeight() {
		return Services.PLATFORM.getBloodMoonWeight();
	}

	@Override
	public String getTranslationKey() {
		return "lunar.event.blood_moon";
	}

	@Override
	public boolean applySpawnEffect() {
		return true;
	}

	@Override
	public void applySpawnEffect(LivingEntity livingEntity, MobSpawnType spawnType) {
		if (spawnType == MobSpawnType.NATURAL) {
			final float difficultyMultiplier = livingEntity.level().getCurrentDifficultyAt(livingEntity.blockPosition()).getSpecialMultiplier();
			final RandomSource random = livingEntity.getRandom();

			AttributeInstance attackAttribute = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
			if (attackAttribute != null) {
				int damageBoost = 0;
				if (difficultyMultiplier > 0) {
					int i = (int) Math.floor(2 * difficultyMultiplier);
					damageBoost = i > 0 ? random.nextInt(i) : 0;
				}
				if (damageBoost > 0) {
					attackAttribute.addPermanentModifier(
							new AttributeModifier(DAMAGE_MODIFIER_UUID,
									damageBoost, AttributeModifier.Operation.ADD_VALUE));
				}
			}

			AttributeInstance healthAttribute = livingEntity.getAttribute(Attributes.MAX_HEALTH);
			if (healthAttribute != null) {
				int healthBoost = 0;
				if (difficultyMultiplier > 0) {
					int i = (int) Math.floor(2 * difficultyMultiplier);
					healthBoost = i > 0 ? random.nextInt(i) : 0;
				}
				if (healthBoost > 0) {
					healthAttribute.addPermanentModifier(
							new AttributeModifier(HEALTH_MODIFIER_UUID,
									healthBoost, AttributeModifier.Operation.ADD_VALUE));
				}
			}
		}
	}

	@Override
	public void removeEntityEffect(Entity entity) {
		if (entity instanceof LivingEntity livingEntity && livingEntity.isAlive()) {
			AttributeInstance attackAttribute = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
			if (attackAttribute != null) {
				attackAttribute.removeModifier(DAMAGE_MODIFIER_UUID);
			}

			AttributeInstance healthAttribute = livingEntity.getAttribute(Attributes.MAX_HEALTH);
			if (healthAttribute != null) {
				healthAttribute.removeModifier(HEALTH_MODIFIER_UUID);
			}
		}
	}

	@Override
	public EventResult canSleep(Player player, BlockPos sleepingLocation) {
		//TODO: Config?
		return EventResult.DEFAULT;
	}
}
