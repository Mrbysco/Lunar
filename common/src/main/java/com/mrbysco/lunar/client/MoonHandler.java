package com.mrbysco.lunar.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

public class MoonHandler {
	private static String moonID = null;
	private static int rawMoonColor = 0xFFFFFF;
	private static float[] moonColor = null;
	private static float rawMoonScale = 1.0F;
	private static Matrix4f moonScale;
	private static ResourceLocation moonTexture;

	public static void colorTheMoon(ClientLevel level, PoseStack poseStack, Matrix4f matrix4f, float f, Camera camera) {
		if (isEventActive()) {
			RenderSystem.setShaderColor(moonColor[0], moonColor[1], moonColor[2], 1.0F);
		}
	}

	public static void setMoon(String eventID, int color, float scale) {
		rawMoonColor = color;
		int r = (color >> 16) & 0xFF;
		int g = (color >> 8) & 0xFF;
		int b = (color >> 0) & 0xFF;
		moonColor = new float[]{(float) r / 255.0F, (float) g / 255.0F, (float) b / 255.0F};
		moonID = eventID;
		if (scale != 1.0F) {
			moonScale = (new Matrix4f()).translation(scale, 1F, scale);
			rawMoonScale = scale;
		}
	}

	public static void setMoonTexture(@Nullable ResourceLocation location) {
		moonTexture = location;
	}

	public static void disableMoon() {
		moonColor = null;
		moonID = null;
		moonScale = null;
		moonTexture = null;
	}

	public static boolean isEventActive() {
		return moonID != null && moonColor != null;
	}

	public static boolean isMoonScaled() {
		return moonScale != null;
	}

	public static ResourceLocation getMoonTexture(ResourceLocation defaultTexture) {
		if (moonTexture != null) {
			return moonTexture;
		}
		return defaultTexture;
	}

	public static Matrix4f getMoonScale() {
		return moonScale;
	}

	public static Matrix4f scaleMoon(Matrix4f matrix) {
//		if (isMoonScaled() && moonScale != null) { TODO: Figure out how to scale just the moon!
//			matrix.mul(moonScale);
//		}
		return matrix;
	}

	public static int getMoonColor() {
		return rawMoonColor;
	}

	public static float getRawMoonScale() {
		return rawMoonScale;
	}
}
