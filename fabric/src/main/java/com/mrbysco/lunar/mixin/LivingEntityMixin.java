package com.mrbysco.lunar.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mrbysco.lunar.Lunar;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	public LivingEntityMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	@Shadow
	@Nullable
	public abstract AttributeInstance getAttribute(Attribute attribute);

	@Shadow
	public abstract boolean hasEffect(MobEffect p_21024_);

	@ModifyReturnValue(method = "createLivingAttributes", at = @At("RETURN"))
	private static AttributeSupplier.Builder port_lib$addModdedAttributes(AttributeSupplier.Builder builder) {
		return builder.add(Lunar.ENTITY_GRAVITY);
	}

	@ModifyVariable(method = "travel", at = @At(value = "STORE", ordinal = 0))
	private double lunar_changeGravity(double original) {
		if (original == 0.08) { // only apply gravity if other mods haven't changed it
			AttributeInstance attribute = this.getAttribute(Lunar.ENTITY_GRAVITY);
			if (attribute != null)
				return attribute.getValue();
		}
		return original;
	}

	private static final AttributeModifier SLOW_FALLING = new AttributeModifier(
			UUID.fromString("A5B6CF2A-2F7C-31EF-9022-7C3E7D5E6ABA"),
			"Slow falling acceleration reduction", -0.07, AttributeModifier.Operation.ADDITION); // Add -0.07 to 0.08 so we get the vanilla default of 0.01

	@Inject(
			method = "travel",
			at = @At(
					value = "CONSTANT", args = {
					"doubleValue=0.08D"
			}
			)
	)
	public void lunar_checkGravity(Vec3 travelVector, CallbackInfo ci) {
		AttributeInstance gravity = this.getAttribute(Lunar.ENTITY_GRAVITY);
		if (gravity != null) {
			boolean falling = this.getDeltaMovement().y <= 0.0D;
			if (falling && this.hasEffect(MobEffects.SLOW_FALLING)) {
				if (!gravity.hasModifier(SLOW_FALLING)) gravity.addTransientModifier(SLOW_FALLING);
				this.resetFallDistance();
			} else if (gravity.hasModifier(SLOW_FALLING)) {
				gravity.removeModifier(SLOW_FALLING);
			}
		}
	}
}
