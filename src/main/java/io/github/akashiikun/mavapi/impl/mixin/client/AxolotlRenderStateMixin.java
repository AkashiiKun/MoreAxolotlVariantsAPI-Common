// Copyright (c) 2025 Jab125. All rights reserved.
// This file is part of More Axolotl Variants API.
// More Axolotl Variants API is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
// More Axolotl Variants API distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
// You should have received a copy of the GNU Lesser General Public License along with More Axolotl Variants API. If not, see <https://www.gnu.org/licenses/>.
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
