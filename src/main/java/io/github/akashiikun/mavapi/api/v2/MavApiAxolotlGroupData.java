package io.github.akashiikun.mavapi.api.v2;

import io.github.akashiikun.mavapi.impl.extension.AxolotlGroupDataExtension;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.animal.axolotl.Axolotl;

public class MavApiAxolotlGroupData {
	@SafeVarargs
	public static Axolotl.AxolotlGroupData create(Holder<AxolotlVariant>... variants) {
		Axolotl.AxolotlGroupData axolotlGroupData = new Axolotl.AxolotlGroupData(null);
		((AxolotlGroupDataExtension) axolotlGroupData).setVariants(variants);
		return axolotlGroupData;
	}

	public static Holder<AxolotlVariant> getVariant(Axolotl.AxolotlGroupData axolotlGroupData, RandomSource randomSource) {
		return ((AxolotlGroupDataExtension) axolotlGroupData).getVariant(randomSource);
	}
}
