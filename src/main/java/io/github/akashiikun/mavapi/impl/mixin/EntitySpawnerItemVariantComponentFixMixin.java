package io.github.akashiikun.mavapi.impl.mixin;

import com.mojang.serialization.Dynamic;
import io.github.akashiikun.mavapi.impl.AxolotlItemVariantComponentFixHelper;
import net.minecraft.util.datafix.fixes.EntitySpawnerItemVariantComponentFix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntitySpawnerItemVariantComponentFix.class)
public class EntitySpawnerItemVariantComponentFixMixin {
	/**
	 * @author Jab125
	 * @reason Makes our intentions clearer
	 */
	@Overwrite
	private static <T> Dynamic<T> fixAxolotlBucket(Dynamic<T> data, Dynamic<T> entityData) {
		return AxolotlItemVariantComponentFixHelper.fixAxolotlBucket(data, entityData);
	}
}












