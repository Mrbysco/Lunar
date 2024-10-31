package com.mrbysco.lunar.registry.events;

import com.mrbysco.lunar.Constants;
import com.mrbysco.lunar.api.LunarEvent;
import com.mrbysco.lunar.handler.result.EventResult;
import com.mrbysco.lunar.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class BloodMoonEvent extends LunarEvent {
	private static final UUID DAMAGE_MODIFIER_UUID = UUID.fromString("2f00d1d5-a4aa-4c2f-bb48-1d6570507666");
	private static final UUID HEALTH_MODIFIER_UUID = UUID.fromString("267db29a-2a6f-4c9d-b3c8-512c085bdf21");

	public BloodMoonEvent() {
		super(new ResourceLocation(Constants.MOD_ID, "blood_moon"), 0x882e2e);
	}

	@Override
	public int spawnWeight() {
		return Services.PLATFORM.getBloodMoonWeight();
	}

	@Override
	public int getPhase() {
		return Services.PLATFORM.getBloodMoonPhase();
	}

	@Override
	public int getDay() {
		return Services.PLATFORM.getBloodMoonDay();
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
							new AttributeModifier(DAMAGE_MODIFIER_UUID, "Blood moon damage boost", (double) damageBoost, AttributeModifier.Operation.ADDITION));
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
							new AttributeModifier(HEALTH_MODIFIER_UUID, "Blood moon health boost", (double) healthBoost, AttributeModifier.Operation.ADDITION));
				}
			}
		}
	}

	@Override
	public void stopEffects(Level level) {
		if (!level.isClientSide) {
			ServerLevel serverLevel = (ServerLevel) level;
			for (Entity entity : serverLevel.getAllEntities()) {
				if (entity instanceof LivingEntity livingEntity && livingEntity.isAlive()) {
					AttributeInstance attackAttribute = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
					if (attackAttribute != null) {
						attackAttribute.removePermanentModifier(DAMAGE_MODIFIER_UUID);
					}

					AttributeInstance healthAttribute = livingEntity.getAttribute(Attributes.MAX_HEALTH);
					if (healthAttribute != null) {
						healthAttribute.removePermanentModifier(HEALTH_MODIFIER_UUID);
					}
				}
			}
		}
	}

	@Override
	public EventResult canSleep(Player player, BlockPos sleepingLocation) {
		//TODO: Config?
		return EventResult.DEFAULT;
	}
}
