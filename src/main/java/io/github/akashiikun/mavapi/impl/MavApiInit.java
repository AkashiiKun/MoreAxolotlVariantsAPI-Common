// Copyright (c) 2025 Jab125. All rights reserved.
// This file is part of More Axolotl Variants API.
// More Axolotl Variants API is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
// More Axolotl Variants API distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
// You should have received a copy of the GNU Lesser General Public License along with More Axolotl Variants API. If not, see <https://www.gnu.org/licenses/>.
package io.github.akashiikun.mavapi.impl;

import io.github.akashiikun.mavapi.api.v2.AxolotlVariant;
import io.github.akashiikun.mavapi.api.v2.MavApiDataComponents;
import io.github.akashiikun.mavapi.api.v2.MavApiRegistries;
import io.github.akashiikun.mavapi.impl.init.ModEntityDataSerializers;
//? fabric {
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
//?} else if neoforge {
/*import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

import static io.github.akashiikun.mavapi.impl.init.ModEntityDataSerializers.AXOLOTL_VARIANT;
*///?}

import java.lang.invoke.MethodHandles;

//? if neoforge {
/*@Mod(MavApiInit.MOD_ID)
*///?}
public class MavApiInit {
	public static final String MOD_ID = "mavapi";

	//? if neoforge {
	/*public MavApiInit(IEventBus modEventBus) {
		onInitialize();
		modEventBus.register(this);
	}
	*///?}

	public void onInitialize() {
		try {
			MethodHandles.publicLookup().ensureInitialized(ModEntityDataSerializers.class);
			MethodHandles.publicLookup().ensureInitialized(MavApiDataComponents.class);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		//? if fabric {
		DynamicRegistries.registerSynced(MavApiRegistries.AXOLOTL_VARIANT, AxolotlVariant.DIRECT_CODEC, AxolotlVariant.NETWORK_CODEC);
		//?}
	}

	//? if neoforge {
	/*@SubscribeEvent
	public void event(DataPackRegistryEvent.NewRegistry event) {
		event.dataPackRegistry(MavApiRegistries.AXOLOTL_VARIANT, AxolotlVariant.DIRECT_CODEC, AxolotlVariant.NETWORK_CODEC);
	}

	@SubscribeEvent
	public void event(RegisterEvent event) {
		event.register(NeoForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, Identifier.parse("mavapi:axolotl_variant"), () -> AXOLOTL_VARIANT);
		event.register(Registries.DATA_COMPONENT_TYPE, Identifier.parse("mavapi:axolotl/variant"), () -> MavApiDataComponents.AXOLOTL_VARIANT = DataComponentType.<Holder<AxolotlVariant>>builder().persistent(AxolotlVariant.CODEC).networkSynchronized(AxolotlVariant.STREAM_CODEC).build());
	}
	*///?}
}
