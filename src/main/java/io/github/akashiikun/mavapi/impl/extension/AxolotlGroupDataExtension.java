package io.github.akashiikun.mavapi.impl.extension;

import io.github.akashiikun.mavapi.api.v2.AxolotlVariant;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;

public interface AxolotlGroupDataExtension {
	void setVariants(Holder<AxolotlVariant>[] variants);
	public Holder<AxolotlVariant> getVariant(RandomSource random);
}
