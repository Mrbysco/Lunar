package com.mrbysco.lunar.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrbysco.lunar.client.MoonHandler;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SkyRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkyRenderer.class)
public class SkyRendererMixin {

	@Inject(method = "renderMoon(IFLnet/minecraft/client/renderer/MultiBufferSource;Lcom/mojang/blaze3d/vertex/PoseStack;)V", at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;",
			shift = Shift.AFTER,
			ordinal = 0
	))
	private void lunar_colorMoon(int phase, float alpha, MultiBufferSource bufferSource, PoseStack poseStack, CallbackInfo ci) {
		MoonHandler.colorTheMoon();
	}

	@ModifyVariable(
			method = "renderMoon(IFLnet/minecraft/client/renderer/MultiBufferSource;Lcom/mojang/blaze3d/vertex/PoseStack;)V",
			at = @At(value = "CONSTANT", args = "floatValue=20.0"),
			ordinal = 1,
			require = 0,
			argsOnly = true)
	private Matrix4f lunar_scaleMoon(Matrix4f matrix) {
		return MoonHandler.scaleMoon(matrix);
	}

	@ModifyArg(
			method = "renderMoon(IFLnet/minecraft/client/renderer/MultiBufferSource;Lcom/mojang/blaze3d/vertex/PoseStack;)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/RenderType;celestial(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;"),
			index = 0)
	public ResourceLocation lunar_changeMoonTexture(ResourceLocation location) {
		return MoonHandler.getMoonTexture(location);
	}
}