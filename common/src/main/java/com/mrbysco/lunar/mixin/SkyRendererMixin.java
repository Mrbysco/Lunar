package com.mrbysco.lunar.mixin;

import com.mojang.blaze3d.textures.GpuTextureView;
import com.mrbysco.lunar.client.MoonHandler;
import net.minecraft.client.renderer.SkyRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import org.joml.Vector4fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SkyRenderer.class)
public abstract class SkyRendererMixin {

	@Shadow
	private AbstractTexture moonTexture;

	@ModifyArg(
			method = "renderMoon(IFLcom/mojang/blaze3d/vertex/PoseStack;)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/DynamicUniforms;writeTransform(Lorg/joml/Matrix4fc;Lorg/joml/Vector4fc;Lorg/joml/Vector3fc;Lorg/joml/Matrix4fc;F)Lcom/mojang/blaze3d/buffers/GpuBufferSlice;"
			), index = 1
	)
	private Vector4fc lunar_colorMoon(Vector4fc colorVector) {
		return MoonHandler.colorTheMoon(colorVector);
	}

	@ModifyArg(
			method = "renderMoon(IFLcom/mojang/blaze3d/vertex/PoseStack;)V",
			at = @At(
					value = "INVOKE",
					target = "Lcom/mojang/blaze3d/systems/RenderPass;bindSampler(Ljava/lang/String;Lcom/mojang/blaze3d/textures/GpuTextureView;)V",
					remap = false),
			index = 1)
	public GpuTextureView lunar_changeMoonTexture(GpuTextureView gpuTextureView) {
		AbstractTexture moonTexture = MoonHandler.getMoonTexture(this.moonTexture);
		return moonTexture.getTextureView();
	}
}