package com.mrbysco.lunar.mixin;

import com.mojang.renderpearl.api.buffers.GpuBuffer;
import com.mrbysco.lunar.client.MoonHandler;
import net.minecraft.client.renderer.SkyRenderer;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SkyRenderer.class)
public abstract class SkyRendererMixin {

	@ModifyArg(
			method = "renderMoon(Lcom/mojang/renderpearl/api/commands/RenderPass;Lnet/minecraft/world/level/MoonPhase;FLcom/mojang/blaze3d/vertex/PoseStack;)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/DynamicGpuData;writeTransform(Lorg/joml/Matrix4f;Lorg/joml/Vector4f;)Lcom/mojang/renderpearl/api/buffers/GpuBufferSlice;"
			), index = 1
	)
	private Vector4f lunar_colorMoon(Vector4f colorModulator) {
		return MoonHandler.colorTheMoon(colorModulator);
	}

	@ModifyArg(
			method = "renderMoon(Lcom/mojang/renderpearl/api/commands/RenderPass;Lnet/minecraft/world/level/MoonPhase;FLcom/mojang/blaze3d/vertex/PoseStack;)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/SkyRenderer;drawCelestialBody(Ljava/util/function/Supplier;Lcom/mojang/renderpearl/api/commands/RenderPass;Lcom/mojang/renderpearl/api/buffers/GpuBufferSlice;Lcom/mojang/renderpearl/api/buffers/GpuBuffer;Lcom/mojang/renderpearl/api/buffers/GpuBuffer;I)V",
					remap = false),
			index = 4)
	public GpuBuffer lunar_changeMoonTexture(GpuBuffer gpuBuffer) {
		return MoonHandler.getMoonBuffer(gpuBuffer);
	}
}