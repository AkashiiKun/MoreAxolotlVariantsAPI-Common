package io.github.akashiikun.mavapi.impl.extension.client;

import io.github.akashiikun.mavapi.api.v2.AxolotlVariant;

public interface AxolotlRenderStateExtension {
	AxolotlVariant getVariant();
	void setVariant(AxolotlVariant variant);
}
