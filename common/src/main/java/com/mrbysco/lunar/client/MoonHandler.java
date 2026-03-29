package com.mrbysco.lunar.client;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.data.AtlasIds;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.MoonPhase;
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
	private static GpuBuffer moonBuffer;

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
	 *
	 * @param eventID The event ID
	 * @param color   The color of the moon
	 * @param scale   The scale of the moon
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
	 *
	 * @param textureLocation The AbstractTexture of the texture
	 */
	public static void setMoonBuffer(@Nullable Identifier textureLocation) {
		moonBuffer = buildMoonPhases(textureLocation);
	}

	/**
	 * Clear the moon values.
	 */
	public static void disableMoon() {
		moonColor = null;
		moonID = null;
		moonScale = null;
		moonBuffer = null;
	}

	/**
	 * Check if the event is active.
	 *
	 * @return true if the event is active, false otherwise
	 */
	public static boolean isEventActive() {
		return moonID != null && moonColor != null;
	}

	/**
	 * Check if the moon is scaled.
	 *
	 * @return true if the moon is scaled, false otherwise
	 */
	public static boolean isMoonScaled() {
		return moonScale != null;
	}

	/**
	 * Get the ID of the moon.
	 *
	 * @param defaultBuffer The default gpu buffer
	 * @return the resource location of the moon texture
	 */
	public static GpuBuffer getMoonBuffer(GpuBuffer defaultBuffer) {
		if (moonBuffer != null) {
			return moonBuffer;
		}
		return defaultBuffer;
	}

	/**
	 * Get the scale of the moon.
	 *
	 * @return the scale of the moon
	 */
	public static Matrix4f getMoonScale() {
		return moonScale;
	}

	/**
	 * Scale the moon.
	 *
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
	 *
	 * @return the color int of the moon
	 */
	public static int getMoonColor() {
		return rawMoonColor;
	}

	/**
	 * Get the color of the moon as a float array.
	 *
	 * @return the color of the moon as a float array
	 */
	public static float getRawMoonScale() {
		return rawMoonScale;
	}

	private static GpuBuffer buildMoonPhases(@Nullable Identifier location) {
		if (location == null) {
			return null;
		}
		TextureAtlas atlas = Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(AtlasIds.CELESTIALS);
		MoonPhase[] amoonphase = MoonPhase.values();
		VertexFormat vertexformat = DefaultVertexFormat.POSITION_TEX;

		GpuBuffer gpubuffer;
		try (ByteBufferBuilder bytebufferbuilder = ByteBufferBuilder.exactlySized(amoonphase.length * 4 * vertexformat.getVertexSize())) {
			BufferBuilder bufferbuilder = new BufferBuilder(bytebufferbuilder, VertexFormat.Mode.QUADS, vertexformat);

			for (MoonPhase moonphase : amoonphase) {
				Identifier moonLocation = location.withSuffix("/" + moonphase.getSerializedName());
				TextureAtlasSprite textureatlassprite = atlas.getSprite(moonLocation);
				bufferbuilder.addVertex(-1.0F, 0.0F, -1.0F).setUv(textureatlassprite.getU1(), textureatlassprite.getV1());
				bufferbuilder.addVertex(1.0F, 0.0F, -1.0F).setUv(textureatlassprite.getU0(), textureatlassprite.getV1());
				bufferbuilder.addVertex(1.0F, 0.0F, 1.0F).setUv(textureatlassprite.getU0(), textureatlassprite.getV0());
				bufferbuilder.addVertex(-1.0F, 0.0F, 1.0F).setUv(textureatlassprite.getU1(), textureatlassprite.getV0());
			}

			try (MeshData meshdata = bufferbuilder.buildOrThrow()) {
				gpubuffer = RenderSystem.getDevice().createBuffer(() -> "Moon phases", 32, meshdata.vertexBuffer());
			}
		}

		return gpubuffer;
	}
}
