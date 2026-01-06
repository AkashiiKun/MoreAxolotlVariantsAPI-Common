// Copyright (c) 2025 Jab125. All rights reserved.
// This file is part of More Axolotl Variants API.
// More Axolotl Variants API is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
// More Axolotl Variants API distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
// You should have received a copy of the GNU Lesser General Public License along with More Axolotl Variants API. If not, see <https://www.gnu.org/licenses/>.
package io.github.akashiikun.mavapi.impl.mixin.client;

import io.github.akashiikun.mavapi.api.v2.AxolotlVariants;
import io.github.akashiikun.mavapi.impl.extension.client.AxolotlRenderStateExtension;
import net.minecraft.client.renderer.entity.AxolotlRenderer;
import net.minecraft.client.renderer.entity.state.AxolotlRenderState;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AxolotlRenderer.class)
public class AxolotlRendererMixin {
	/**
	 * @author Jab125
	 * @reason There's no keeping compat here, this keeps our intentions clear.
	 */
	@Overwrite
	public Identifier getTextureLocation(AxolotlRenderState axolotlRenderState) {
		return ((AxolotlRenderStateExtension) axolotlRenderState).getVariant() == null ? MissingTextureAtlasSprite.getLocation() : ((AxolotlRenderStateExtension) axolotlRenderState).getVariant().assetInfo().texturePath();
	}

	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/animal/axolotl/Axolotl;Lnet/minecraft/client/renderer/entity/state/AxolotlRenderState;F)V", at = @At("RETURN"))
	void mavapi$extractRenderState(Axolotl axolotl, AxolotlRenderState axolotlRenderState, float f, CallbackInfo ci) {
		((AxolotlRenderStateExtension) axolotlRenderState).setVariant(AxolotlVariants.getVariant(axolotl).value());
	}
}
