// Copyright (c) 2025 Jab125. All rights reserved.
// This file is part of More Axolotl Variants API.
// More Axolotl Variants API is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
// More Axolotl Variants API distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
// You should have received a copy of the GNU Lesser General Public License along with More Axolotl Variants API. If not, see <https://www.gnu.org/licenses/>.
package io.github.akashiikun.mavapi.impl.init;

import io.github.akashiikun.mavapi.api.v2.AxolotlVariant;
//? if fabric {
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricTrackedDataRegistry;
//?} else if neoforge {
/*import net.minecraft.core.Registry;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
*///?}
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.Identifier;

@SuppressWarnings("NullableProblems")
public class ModEntityDataSerializers {
	public static final EntityDataSerializer<Holder<AxolotlVariant>> AXOLOTL_VARIANT = EntityDataSerializer.forValueType(AxolotlVariant.STREAM_CODEC);

	static {
		//? if fabric {
		FabricTrackedDataRegistry.register(Identifier.parse("mavapi:axolotl_variant"), AXOLOTL_VARIANT);
		//?}
	}
}
