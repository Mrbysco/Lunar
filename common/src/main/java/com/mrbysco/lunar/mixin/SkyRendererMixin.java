package com.mrbysco.lunar.mixin;

import com.mrbysco.lunar.client.MoonHandler;
import net.minecraft.client.renderer.SkyRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SkyRenderer.class)
public class SkyRendererMixin {

	@ModifyArg(
			method = "renderMoon(IFLnet/minecraft/client/renderer/MultiBufferSource;Lcom/mojang/blaze3d/vertex/PoseStack;)V",
			at = @At(
					value = "INVOKE",
					target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;setColor(I)Lcom/mojang/blaze3d/vertex/VertexConsumer;"
			)
	)
	private int lunar_colorMoon(int originalColor) {
		return MoonHandler.colorTheMoon(originalColor);
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