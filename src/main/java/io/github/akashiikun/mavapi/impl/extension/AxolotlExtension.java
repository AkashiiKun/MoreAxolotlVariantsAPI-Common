package io.github.akashiikun.mavapi.impl.extension;

import io.github.akashiikun.mavapi.api.v2.AxolotlVariant;
import net.minecraft.core.Holder;

@SuppressWarnings("NullableProblems")
public interface AxolotlExtension {
	void setVariant(Holder<AxolotlVariant> holder);

	public Holder<AxolotlVariant> getVariant();
}
