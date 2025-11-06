package com.mrbysco.lunar.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.joml.Vector4fc;

/**
 * Handles the moon color, texture and scale.
 */
public class MoonHandler {
	private static String moonID = null;
	private static int rawMoonColor = 0xFFFFFF;
	private static float[] moonColor = null;
	private static float rawMoonScale = 1.0F;
	private static Matrix4f moonScale;
	private static AbstractTexture moonTexture;

	/**
	 * Called to set the color of the moon.
	 */
	public static Vector4fc colorTheMoon(Vector4fc originalColor) {
		if (isEventActive()) {
			int color = getMoonColor();
			return new Vector4f(ARGB.redFloat(color), ARGB.greenFloat(color), ARGB.blueFloat(color), originalColor.w());
		}
		return originalColor;
	}

	/**
	 * Called to set the local values of the moon.
	 * @param eventID The event ID
	 * @param color The color of the moon
	 * @param scale The scale of the moon
	 */
	public static void setMoon(String eventID, int color, float scale) {
		rawMoonColor = color;
		float r = ARGB.redFloat(color);
		float g = ARGB.greenFloat(color);
		float b = ARGB.blueFloat(color);
		moonColor = new float[]{r, g, b};
		moonID = eventID;
		if (scale != 1.0F) {
			rawMoonScale = scale;
			moonScale = (new Matrix4f()).scale(scale, 1F, scale);
		}
	}

	/**
	 * Called to set the texture used for the moon.
	 * @param textureLocation The AbstractTexture of the texture
	 */
	public static void setMoonTexture(@Nullable ResourceLocation textureLocation) {
		moonTexture = getTexture(textureLocation);
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
	public static AbstractTexture getMoonTexture(AbstractTexture defaultTexture) {
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

	/**
	 * Method copied from SkyRenderer to get an abstract texture from a resource location.
	 * @param location The resource location of the texture
	 * @return the abstract texture of the texture
	 */
	private static AbstractTexture getTexture(ResourceLocation location) {
		TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
		AbstractTexture abstracttexture = texturemanager.getTexture(location);
		abstracttexture.setUseMipmaps(false);
		return abstracttexture;
	}
}
