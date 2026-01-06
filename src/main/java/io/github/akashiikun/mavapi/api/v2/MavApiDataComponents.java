// Copyright (c) 2025 Jab125. All rights reserved.
// This file is part of More Axolotl Variants API.
// More Axolotl Variants API is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
// More Axolotl Variants API distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
// You should have received a copy of the GNU Lesser General Public License along with More Axolotl Variants API. If not, see <https://www.gnu.org/licenses/>.
package io.github.akashiikun.mavapi.api.v2;

import net.minecraft.core.Holder;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
//? if fabric {
import net.minecraft.core.Registry;
//?}

import java.util.function.Supplier;

// deferred only exists because NeoForge doesn't like using the registry directly
@SuppressWarnings("NullableProblems")
public class MavApiDataComponents {
	//? if fabric {
	public static final DataComponentType<Holder<AxolotlVariant>> AXOLOTL_VARIANT = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Identifier.parse("mavapi:axolotl/variant"), DataComponentType.<Holder<AxolotlVariant>>builder().persistent(AxolotlVariant.CODEC).networkSynchronized(AxolotlVariant.STREAM_CODEC).build());
	// The only reason you would use this is in a multloader environment
	public static final Supplier<DataComponentType<Holder<AxolotlVariant>>> DEFERRED_AXOLOTL_VARIANT = () -> MavApiDataComponents.AXOLOTL_VARIANT;
	//?} else if neoforge {
	/*// this is because neoforge sucks
	public static DataComponentType<Holder<AxolotlVariant>> AXOLOTL_VARIANT = null;
	public static final Supplier<DataComponentType<Holder<AxolotlVariant>>> DEFERRED_AXOLOTL_VARIANT = () -> (DataComponentType<Holder<AxolotlVariant>>) BuiltInRegistries.DATA_COMPONENT_TYPE.getOptional(Identifier.parse("mavapi:axolotl/variant")).orElseThrow();
	*///?}
}
