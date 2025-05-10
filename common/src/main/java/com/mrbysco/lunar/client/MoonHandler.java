package com.mrbysco.lunar.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/**
 * Handles the moon color, texture and scale.
 */
public class MoonHandler {
	private static String moonID = null;
	private static int rawMoonColor = 0xFFFFFF;
	private static float[] moonColor = null;
	private static float rawMoonScale = 1.0F;
	private static Matrix4f moonScale;
	private static ResourceLocation moonTexture;

	/**
	 * Called to set the color of the moon.
	 * @param level The level
	 * @param frustumMatrix the frustum matrix
	 * @param projectionMatrix the projection matrix
	 * @param partialTick the partial tick
	 * @param camera the camera
	 */
	public static void colorTheMoon(ClientLevel level, Matrix4f frustumMatrix, Matrix4f projectionMatrix,
	                                float partialTick, Camera camera) {
		if (isEventActive()) {
			RenderSystem.setShaderColor(moonColor[0], moonColor[1], moonColor[2], 1.0F);
		}
	}

	/**
	 * Called to set the local values of the moon.
	 * @param eventID The event ID
	 * @param color The color of the moon
	 * @param scale The scale of the moon
	 */
	public static void setMoon(String eventID, int color, float scale) {
		rawMoonColor = color;
		int r = (color >> 16) & 0xFF;
		int g = (color >> 8) & 0xFF;
		int b = (color >> 0) & 0xFF;
		moonColor = new float[]{(float) r / 255.0F, (float) g / 255.0F, (float) b / 255.0F};
		moonID = eventID;
		if (scale != 1.0F) {
			rawMoonScale = scale;
			moonScale = (new Matrix4f()).scale(scale, 1F, scale);
		}
	}

	/**
	 * Called to set the texture used for the moon.
	 * @param location The resource location of the texture
	 */
	public static void setMoonTexture(@Nullable ResourceLocation location) {
		moonTexture = location;
	}

	/**
	 * Clear the moon values.
	 */
	public static void disableMoon() {
		moonColor = null;
		moonID = null;
		moonScale = null;
		moonTexture = null;
	}

	/**
	 * Check if the event is active.
	 * @return true if the event is active, false otherwise
	 */
	public static boolean isEventActive() {
		return moonID != null && moonColor != null;
	}

	/**
	 * Check if the moon is scaled.
	 * @return true if the moon is scaled, false otherwise
	 */
	public static boolean isMoonScaled() {
		return moonScale != null;
	}

	/**
	 * Get the ID of the moon.
	 * @param defaultTexture The default texture
	 * @return the resource location of the moon texture
	 */
	public static ResourceLocation getMoonTexture(ResourceLocation defaultTexture) {
		if (moonTexture != null) {
			return moonTexture;
		}
		return defaultTexture;
	}

	/**
	 * Get the scale of the moon.
	 * @return the scale of the moon
	 */
	public static Matrix4f getMoonScale() {
		return moonScale;
	}

	/**
	 * Scale the moon.
	 * @param matrix the matrix to scale
	 * @return the scaled matrix
	 */
	public static Matrix4f scaleMoon(Matrix4f matrix) {
//		if (isMoonScaled() && moonScale != null) { TODO: Figure out how to scale just the moon!
//			matrix.mul(moonScale);
//		}
		return matrix;
	}

	/**
	 * Get the color of the moon.
	 * @return the color int of the moon
	 */
	public static int getMoonColor() {
		return rawMoonColor;
	}

	/**
	 * Get the color of the moon as a float array.
	 * @return the color of the moon as a float array
	 */
	public static float getRawMoonScale() {
		return rawMoonScale;
	}
}
