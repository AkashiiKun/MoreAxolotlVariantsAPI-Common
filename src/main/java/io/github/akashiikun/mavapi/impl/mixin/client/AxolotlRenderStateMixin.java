package io.github.akashiikun.mavapi.impl.mixin.client;

import io.github.akashiikun.mavapi.api.v2.AxolotlVariant;
import io.github.akashiikun.mavapi.impl.extension.client.AxolotlRenderStateExtension;
import net.minecraft.client.renderer.entity.state.AxolotlRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AxolotlRenderState.class)
public class AxolotlRenderStateMixin implements AxolotlRenderStateExtension {
	@Unique
	private AxolotlVariant variant;
	@Override
	public AxolotlVariant getVariant() {
		return this.variant;
	}

	@Override
	public void setVariant(AxolotlVariant variant) {
		this.variant = variant;
	}
}
