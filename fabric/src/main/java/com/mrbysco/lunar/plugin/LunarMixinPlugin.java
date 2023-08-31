package com.mrbysco.lunar.plugin;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class LunarMixinPlugin implements IMixinConfigPlugin {
	@Override
	public void onLoad(String mixinPackage) {
		if (FabricLoader.getInstance().isModLoaded("architectury")) {
			System.out.println("[Lunar] Applying Architectury compatibility");
		}
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		List<String> classList = List.of(
				"com.mrbysco.lunar.mixin.BaseSpawnerMixin",
				"com.mrbysco.lunar.mixin.CatSpawnerMixin",
				"com.mrbysco.lunar.mixin.NaturalSpawnerMixin"
		);
		if (classList.contains(mixinClassName)) {
			return !FabricLoader.getInstance().isModLoaded("architectury");
		}
		return true;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
		// noop
	}

	@Override
	public List<String> getMixins() {
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
		// noop
	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
		// noop
	}
}
