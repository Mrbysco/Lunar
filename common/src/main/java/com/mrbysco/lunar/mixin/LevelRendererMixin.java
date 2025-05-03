package com.mrbysco.lunar.mixin;

import com.mrbysco.lunar.client.MoonHandler;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

	@Shadow
	@Nullable
	private ClientLevel level;

	@Inject(method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V", at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/multiplayer/ClientLevel;getMoonPhase()I",
			shift = Shift.AFTER,
			ordinal = 0
	))
	private void lunar_colorMoon(Matrix4f frustumMatrix, Matrix4f projectionMatrix, float partialTick, Camera camera,
	                             boolean isFoggy, Runnable skyFogSetup, CallbackInfo ci) {
		if (this.level != null) {
			MoonHandler.colorTheMoon(level, frustumMatrix, projectionMatrix, partialTick, camera);
		}
	}

	@ModifyVariable(
			method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V",
			slice = @Slice(
					from = @At(ordinal = 0, value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V"),
					to = @At(ordinal = 1, value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V")
			),
			at = @At(value = "CONSTANT", args = "floatValue=20.0"),
			ordinal = 1,
			require = 0,
			argsOnly = true)
	private Matrix4f lunar_scaleMoon(Matrix4f matrix) {
		return MoonHandler.scaleMoon(matrix);
	}

	@ModifyArg(method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V",
			slice = @Slice(
					from = @At(ordinal = 1, value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferUploader;drawWithShader(Lcom/mojang/blaze3d/vertex/MeshData;)V"),
					to = @At(ordinal = 0, value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getMoonPhase()I")
			),
			at = @At(
					value = "INVOKE",
					target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V"),
			index = 1)
	public ResourceLocation lunar_changeMoonTexture(ResourceLocation location) {
		return MoonHandler.getMoonTexture(location);
	}
}