package com.mrbysco.lunar.mixin;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mrbysco.lunar.client.MoonHandler;
import net.minecraft.client.renderer.SkyRenderer;
import org.joml.Vector4fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SkyRenderer.class)
public abstract class SkyRendererMixin {

	@ModifyArg(
			method = "renderMoon(Lnet/minecraft/world/level/MoonPhase;FLcom/mojang/blaze3d/vertex/PoseStack;)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/DynamicUniforms;writeTransform(Lorg/joml/Matrix4fc;Lorg/joml/Vector4fc;Lorg/joml/Vector3fc;Lorg/joml/Matrix4fc;)Lcom/mojang/blaze3d/buffers/GpuBufferSlice;"
			), index = 1
	)
	private Vector4fc lunar_colorMoon(Vector4fc colorVector) {
		return MoonHandler.colorTheMoon(colorVector);
	}

	@ModifyArg(
			method = "renderMoon(Lnet/minecraft/world/level/MoonPhase;FLcom/mojang/blaze3d/vertex/PoseStack;)V",
			at = @At(
					value = "INVOKE",
					target = "Lcom/mojang/blaze3d/systems/RenderPass;setVertexBuffer(ILcom/mojang/blaze3d/buffers/GpuBuffer;)V",
					remap = false),
			index = 1)
	public GpuBuffer lunar_changeMoonTexture(GpuBuffer gpuBuffer) {
		return MoonHandler.getMoonBuffer(gpuBuffer);
	}
}